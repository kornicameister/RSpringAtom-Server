package org.agatom.springatom.data.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.agatom.springatom.data.model.NAbstractAuditable;
import org.agatom.springatom.data.model.revision.AuditedRevisionEntity;
import org.agatom.springatom.data.model.user.NUser;
import org.agatom.springatom.data.repo.repositories.user.NUserRepository;
import org.agatom.springatom.data.service.services.NAuditableService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.envers.*;
import org.hibernate.envers.query.AuditEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.envers.repository.support.DefaultRevisionMetadata;
import org.springframework.data.history.AnnotationRevisionMetadata;
import org.springframework.data.history.Revision;
import org.springframework.data.history.RevisionMetadata;
import org.springframework.data.history.Revisions;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.security.Principal;
import java.util.*;

@Service
@SuppressWarnings("unchecked")
class AuditableDomainService
  implements NAuditableService {
  private static final Logger LOGGER = LogManager.getLogger(AuditableDomainService.class);
  @Autowired
  @SuppressWarnings("SpringJavaAutowiringInspection")
  private EntityManager   entityManager;
  @Autowired
  private NUserRepository userRepository;

  @Override
  public <T extends NAbstractAuditable> Revisions<Integer, T> findRevisions(final Class<T> clazz, final Long id) {
    final AuditReader reader = AuditReaderFactory.get(this.entityManager);
    final List<Number> revisions = reader.getRevisions(clazz, id);
    final List<Integer> revisionNumbers = Lists.newArrayListWithExpectedSize(revisions.size());

    revisions.forEach(rev -> revisionNumbers.add((Integer) rev));

    return revisionNumbers.isEmpty() ?
      new Revisions<>(Collections.EMPTY_LIST) :
      this.getEntitiesForRevisions(clazz, revisionNumbers, id, reader);
  }

  @Override
  public <T extends NAbstractAuditable> Revision<Integer, T> findInRevision(final Class<T> clazz, final Long id, final Integer revision) {
    return this.findInRevisions(clazz, id, revision).getLatestRevision();
  }

  @Override
  public <T extends NAbstractAuditable> Revisions<Integer, T> findInRevisions(final Class<T> clazz, final Long id, final Integer... revisionNumbers) {
    final AuditReader reader = AuditReaderFactory.get(this.entityManager);
    final Map<Integer, T> revisions = Maps.newHashMapWithExpectedSize(revisionNumbers.length);
    final Map<Number, AuditedRevisionEntity> revisionEntities = reader.findRevisions(AuditedRevisionEntity.class, Sets.newHashSet(revisionNumbers));

    for (Number number : revisionNumbers) {
      revisions.put((Integer) number, reader.find(clazz, id, number));
    }

    return new Revisions<>(this.toRevisions(revisions, revisionEntities));
  }

  @Override
  public <T extends NAbstractAuditable> long countRevisions(final Class<T> clazz, final Long id) {
    final AuditReader reader = AuditReaderFactory.get(this.entityManager);
    return (long) reader.createQuery()
      .forRevisionsOfEntity(clazz, false, true)
      .addProjection(AuditEntity.revisionNumber().count())
      .getSingleResult();
  }

  private <N extends Number & Comparable<N>, T, ID> Revisions<N, T> getEntitiesForRevisions(
    final Class<T> type,
    final List<N> revisionNumbers,
    final ID id,
    final AuditReader reader) {

    final Map<N, T> revisions = Maps.newHashMapWithExpectedSize(revisionNumbers.size());
    final Map<Number, AuditedRevisionEntity> revisionEntities = reader.findRevisions(AuditedRevisionEntity.class, Sets.newHashSet(revisionNumbers));

    for (Number number : revisionNumbers) {
      revisions.put((N) number, reader.find(type, id, number));
    }

    return new Revisions<>(toRevisions(revisions, revisionEntities));
  }

  private <T, N extends Number & Comparable<N>> List<Revision<N, T>> toRevisions(Map<N, T> source, Map<Number, AuditedRevisionEntity> revisionEntities) {
    final List<Revision<N, T>> result = new ArrayList<>();
    for (Map.Entry<N, T> revision : source.entrySet()) {
      final N revisionNumber = revision.getKey();
      final T entity = revision.getValue();
      final RevisionMetadata<N> metadata = (RevisionMetadata<N>) getRevisionMetadata(revisionEntities
        .get(revisionNumber));
      result.add(new Revision<>(metadata, entity));
    }
    Collections.sort(result);
    return Collections.unmodifiableList(result);
  }

  private RevisionMetadata<?> getRevisionMetadata(Object object) {
    if (object instanceof DefaultRevisionEntity) {
      return new DefaultRevisionMetadata((DefaultRevisionEntity) object);
    } else {
      return new AnnotationRevisionMetadata<Long>(object, RevisionNumber.class, RevisionTimestamp.class);
    }
  }

  /**
   * Method retrieves current auditor. Works
   * in following fashion.
   * <ol>
   * <li>Try to locate currently authenticated user</li>
   * <li>If failed, try to locate system administrator</li>
   * <li>Otherwise return boot, as it may be boot operation</li>
   * </ol>
   *
   * @return current auditor
   */
  @Override
  public NUser getCurrentAuditor() {
    Optional<NUser> user = null;
    try {
      user = this.getAuthenticatedUser();
    } catch (Exception exp) {
      LOGGER.debug("No user is currently authenticated");
      LOGGER.trace(exp.getMessage());
    }
    if (user == null) {
      try {
        user = this.getAdministrator();
      } catch (Exception exp) {
        LOGGER.debug("Administrator has not been loaded so far, most like boot.load.data operation");
        LOGGER.trace(exp.getMessage());
      }
    }
    return (user != null && user.isPresent()) ? user.get() : null;
  }

  protected Optional<NUser> getAuthenticatedUser() {
    final Optional<Authentication> authentication = Optional.ofNullable((Authentication) this.getAuthenticatedPrincipal());
    return authentication.isPresent() ? this.userRepository.findByCredentialsUsername(authentication.get().getName()) : null;
  }

  protected Optional<NUser> getAdministrator() {
    return this.userRepository.findByCredentialsUsername("SYSTEM");
  }

  protected Principal getAuthenticatedPrincipal() {
    return SecurityContextHolder.getContext().getAuthentication();
  }

}

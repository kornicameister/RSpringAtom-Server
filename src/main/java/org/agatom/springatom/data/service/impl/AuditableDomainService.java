package org.agatom.springatom.data.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.agatom.springatom.data.model.NAbstractAuditable;
import org.agatom.springatom.data.model.revision.AuditedRevisionEntity;
import org.agatom.springatom.data.service.services.NAuditableService;
import org.hibernate.envers.*;
import org.hibernate.envers.query.AuditEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.envers.repository.support.DefaultRevisionMetadata;
import org.springframework.data.history.AnnotationRevisionMetadata;
import org.springframework.data.history.Revision;
import org.springframework.data.history.RevisionMetadata;
import org.springframework.data.history.Revisions;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
@SuppressWarnings("unchecked")
class AuditableDomainService
  implements NAuditableService {
  @Autowired
  @SuppressWarnings("SpringJavaAutowiringInspection")
  private EntityManager entityManager;

  @Override
  public <T extends NAbstractAuditable> Revisions<Long, T> findRevisions(final Class<T> clazz, final Long id) {
    final AuditReader reader = AuditReaderFactory.get(this.entityManager);
    final List<Number> revisions = reader.getRevisions(clazz, id);
    final List<Long> revisionNumbers = Lists.newArrayListWithExpectedSize(revisions.size());

    revisions.forEach(rev -> revisionNumbers.add((Long) rev));

    return revisionNumbers.isEmpty() ?
      new Revisions<>(Collections.EMPTY_LIST) :
      this.getEntitiesForRevisions(clazz, revisionNumbers, id, reader);
  }

  @Override
  public <T extends NAbstractAuditable> Revision<Long, T> findInRevision(final Class<T> clazz, final Long id, final Long revision) {
    return this.findInRevisions(clazz, id, revision).getLatestRevision();
  }

  @Override
  public <T extends NAbstractAuditable> Revisions<Long, T> findInRevisions(final Class<T> clazz, final Long id, final Long... revisionNumbers) {
    final AuditReader reader = AuditReaderFactory.get(this.entityManager);
    final Map<Long, T> revisions = Maps.newHashMapWithExpectedSize(revisionNumbers.length);
    final Map<Number, AuditedRevisionEntity> revisionEntities = reader.findRevisions(AuditedRevisionEntity.class, Sets.newHashSet(revisionNumbers));

    for (Number number : revisionNumbers) {
      revisions.put((Long) number, reader.find(clazz, id, number));
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

  @Override
  public String getCurrentAuditor() {
    return "SYSTEM";
  }

}

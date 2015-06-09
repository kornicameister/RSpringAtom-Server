/**************************************************************************************************
 * This file is part of [SpringAtom] Copyright [kornicameister@gmail.com][2013]                   *
 * *
 * [SpringAtom] is free software: you can redistribute it and/or modify                           *
 * it under the terms of the GNU General Public License as published by                           *
 * the Free Software Foundation, either version 3 of the License, or                              *
 * (at your option) any later version.                                                            *
 * *
 * [SpringAtom] is distributed in the hope that it will be useful,                                *
 * but WITHOUT ANY WARRANTY; without even the implied warranty of                                 *
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the                                  *
 * GNU General Public License for more details.                                                   *
 * *
 * You should have received a copy of the GNU General Public License                              *
 * along with [SpringAtom].  If not, see <http://www.gnu.org/licenses/gpl.html>.                  *
 **************************************************************************************************/

/*
 * SpringAtom is using part of the original Spring-data-envers code but takes no credit for it.
 * This code was built by Spring team and the honor goes to them.
 */


package org.agatom.springatom.data.repo.core.factory;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.agatom.springatom.data.repo.repositories.NAuditableRepository;
import org.hibernate.envers.*;
import org.hibernate.envers.query.AuditEntity;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.envers.repository.support.DefaultRevisionMetadata;
import org.springframework.data.envers.repository.support.EnversRevisionRepositoryImpl;
import org.springframework.data.history.AnnotationRevisionMetadata;
import org.springframework.data.history.Revision;
import org.springframework.data.history.RevisionMetadata;
import org.springframework.data.history.Revisions;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.data.repository.history.support.RevisionEntityInformation;

import javax.persistence.EntityManager;
import java.util.*;

/**
 * <p>SRepositoryImpl class.</p>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class NAuditableRepositoryImpl<T>
  extends NRepositoryImpl<T>
  implements NAuditableRepository<T> {
  /** Constant <code>ERROR_MESSAGE_INSUFFICIENT_REV_NUMBERS="Insufficient revision numbers"</code> */
  private static final String                            ERROR_MESSAGE_INSUFFICIENT_REV_NUMBERS = "Insufficient revision numbers";
  private static final org.slf4j.Logger                  LOGGER                                 = LoggerFactory.getLogger(NAuditableRepositoryImpl.class);
  private static final String                            ERROR_MESSAGE_ID                       = "ID must not be null";
  private static final String                            ERROR_MESSAGE_PAGEABLE                 = "Pageable must not be null";
  private static final String                            ERROR_MESSAGE_REVISION                 = "Revision must not be null";
  private static final String                            ERROR_MESSAGE_REI                      = "RevisionEntityInformation must not be null";
  private              RevisionEntityInformation         revisionEntityInformation              = null;
  private              RevisionRepository<T, Long, Long> repository                             = null;

  /**
   * <p>Constructor for SRepositoryImpl.</p>
   *
   * @param entityInformation         a {@link org.springframework.data.jpa.repository.support.JpaEntityInformation} object.
   * @param revisionEntityInformation a {@link org.springframework.data.repository.history.support.RevisionEntityInformation} object.
   * @param entityManager             a {@link javax.persistence.EntityManager} object.
   */
  public NAuditableRepositoryImpl(final JpaEntityInformation<T, Long> entityInformation,
                                  final RevisionEntityInformation revisionEntityInformation,
                                  final EntityManager entityManager) {
    super(entityInformation, entityManager);
    Preconditions.checkArgument(revisionEntityInformation != null, ERROR_MESSAGE_REI);
    this.revisionEntityInformation = revisionEntityInformation;
    this.repository = new EnversRevisionRepositoryImpl<>(entityInformation, revisionEntityInformation, entityManager);
    if (LOGGER.isTraceEnabled()) {
      LOGGER.trace(String
        .format("Created %s for arguments=[em=%s,ei=%s,rei=%s]", NAuditableRepositoryImpl.class
          .getSimpleName(), entityManager, entityInformation, revisionEntityInformation));
    }
  }

  /** {@inheritDoc} */
  @Override
  @SuppressWarnings("unchecked")
  public Revision<Long, T> findLastChangeRevision(final Long id) {
    Preconditions.checkArgument(id != null, ERROR_MESSAGE_ID);
    if (LOGGER.isTraceEnabled()) {
      LOGGER.trace(String.format("%s(%s)", "findLastChangeRevision", id));
    }
    return this.repository.findLastChangeRevision(id);
  }

  /** {@inheritDoc} */
  @Override
  @SuppressWarnings("unchecked")
  public Revisions<Long, T> findRevisions(final Long id) {
    Preconditions.checkArgument(id != null, ERROR_MESSAGE_ID);
    if (LOGGER.isTraceEnabled()) {
      LOGGER.trace(String.format("%s(%s)", "findRevisions", id));
    }
    return this.repository.findRevisions(id);
  }

  /** {@inheritDoc} */
  @Override
  @SuppressWarnings("unchecked")
  public Page<Revision<Long, T>> findRevisions(final Long id, final Pageable pageable) {
    Preconditions.checkArgument(id != null, ERROR_MESSAGE_ID);
    Preconditions.checkArgument(pageable != null, ERROR_MESSAGE_PAGEABLE);
    if (LOGGER.isTraceEnabled()) {
      LOGGER.trace(String.format("%s(%s,%s)", "findRevisions", id, pageable));
    }
    return this.repository.findRevisions(id, pageable);
  }

  /** {@inheritDoc} */
  @Override
  public Revision<Long, T> findInRevision(final Long id, final Long revision) {
    Preconditions.checkArgument(id != null, ERROR_MESSAGE_ID);
    Preconditions.checkArgument(revision != null, ERROR_MESSAGE_REVISION);
    if (LOGGER.isTraceEnabled()) {
      LOGGER.trace(String.format("%s(%s,%s)", "findInRevision", id, revision));
    }
    return this.findInRevisions(id, revision).getLatestRevision();
  }

  /** {@inheritDoc} */
  @Override
  @SafeVarargs
  @SuppressWarnings("unchecked")
  public final Revisions<Long, T> findInRevisions(final Long id, final Long... revisionNumbers) {
    Preconditions.checkArgument(id != null, ERROR_MESSAGE_ID);
    Preconditions.checkArgument(revisionNumbers.length >= 1, ERROR_MESSAGE_INSUFFICIENT_REV_NUMBERS);

    if (LOGGER.isTraceEnabled()) {
      LOGGER.trace(String.format("%s(%s,%s)", "findInRevision", id, Arrays.toString(revisionNumbers)));
    }

    final Class<T> type = this.entityInformation.getJavaType();
    final AuditReader reader = AuditReaderFactory.get(this.entityManager);
    final Map<Long, T> revisions = new HashMap<>(revisionNumbers.length);
    final Class<?> revisionEntityClass = this.revisionEntityInformation.getRevisionEntityClass();
    final Map<Number, Object> revisionEntities = (Map<Number, Object>) reader.findRevisions(revisionEntityClass, new HashSet<>(Lists.newArrayList(revisionNumbers)));

    for (Number number : revisionNumbers) {
      revisions.put((Long) number, reader.find(type, id, number));
    }

    return new Revisions<>(this.toRevisions(revisions, revisionEntities));
  }

  /** {@inheritDoc} */
  @Override
  public long countRevisions(final Long id) {
    final Class<T> type = this.entityInformation.getJavaType();
    final AuditReader reader = AuditReaderFactory.get(this.entityManager);

    if (LOGGER.isTraceEnabled()) {
      LOGGER.trace(String.format("%s(%s)", "countRevisions", id));
    }

    return (long) reader.createQuery()
      .forRevisionsOfEntity(type, false, true)
      .addProjection(AuditEntity.revisionNumber().count())
      .getSingleResult();
  }

  @SuppressWarnings("unchecked")
  private List<Revision<Long, T>> toRevisions(Map<Long, T> source, Map<Number, Object> revisionEntities) {
    final List<Revision<Long, T>> result = new ArrayList<>();
    for (Map.Entry<Long, T> revision : source.entrySet()) {
      final Long revisionNumber = revision.getKey();
      final T entity = revision.getValue();
      final RevisionMetadata<Long> metadata = (RevisionMetadata<Long>) getRevisionMetadata(revisionEntities
        .get(revisionNumber));
      result.add(new Revision<>(metadata, entity));
    }
    Collections.sort(result);
    return Collections.unmodifiableList(result);
  }

  @SuppressWarnings("Convert2Diamond")
  private RevisionMetadata<?> getRevisionMetadata(Object object) {
    if (object instanceof DefaultRevisionEntity) {
      return new DefaultRevisionMetadata((DefaultRevisionEntity) object);
    } else {
      return new AnnotationRevisionMetadata<Long>(object, RevisionNumber.class, RevisionTimestamp.class);
    }
  }
}

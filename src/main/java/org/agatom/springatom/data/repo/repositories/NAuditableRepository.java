package org.agatom.springatom.data.repo.repositories;

import org.springframework.data.envers.repository.support.EnversRevisionRepository;
import org.springframework.data.history.Revision;
import org.springframework.data.history.Revisions;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * <p>
 * <small>Class is a part of <b>SpringAtom2</b> and was created at 2014-10-09</small>
 * </p>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
@NoRepositoryBean
public interface NAuditableRepository<T>
  extends NRepository<T>, EnversRevisionRepository<T, Long, Long> {
  /**
   * {@code findInRevision} returns {@link org.springframework.data.history.Revision} of the target underlying target entity in the given revision
   * described in {@code revision} param
   *
   * @param id       id of the entity {@link org.springframework.data.domain.Persistable#getId()}
   * @param revision the revision number
   *
   * @return {@link org.springframework.data.history.Revision} for passed arguments
   *
   * @see NAuditableRepository#findInRevisions(Long, Long...)
   */
  Revision<Long, T> findInRevision(final Long id, final Long revision);

  /**
   * {@code findInRevisions} does exactly the same job but for multiple possible {@code revisions}.
   *
   * @param id        id of the entity {@link org.springframework.data.domain.Persistable#getId()}
   * @param revisions varargs with revision numbers
   *
   * @return {@link org.springframework.data.history.Revisions}
   */
  Revisions<Long, T> findInRevisions(final Long id, final Long... revisions);

  /**
   * Returns how many revisions exists for given {@link org.springframework.data.domain.Persistable#getId()} instance
   *
   * @param id the id
   *
   * @return revisions amount
   */
  long countRevisions(Long id);
}

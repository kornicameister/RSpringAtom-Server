package org.agatom.springatom.data.service.services;


import org.agatom.springatom.data.model.NAbstractAuditable;
import org.agatom.springatom.data.model.user.NUser;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.history.Revision;
import org.springframework.data.history.Revisions;

public interface NAuditableService
  extends AuditorAware<NUser> {

  <T extends NAbstractAuditable> Revisions<Integer, T> findRevisions(final Class<T> clazz, final Long id);

  /**
   * {@code findInRevision} returns {@link org.springframework.data.history.Revision} of the target underlying target entity in the given revision
   * described in {@code revision} param
   *
   * @param id       id of the entity {@link org.springframework.data.domain.Persistable#getId()}
   * @param revision the revision number
   * @param clazz    domain clazz
   *
   * @return {@link org.springframework.data.history.Revision} for passed arguments
   *
   * @see NAuditableService#findInRevisions(Class, Long, Integer...)
   */
  <T extends NAbstractAuditable> Revision<Integer, T> findInRevision(final Class<T> clazz, final Long id, final Integer revision);

  /**
   * {@code findInRevisions} does exactly the same job but for multiple possible {@code revisions}.
   *
   * @param id        id of the entity {@link org.springframework.data.domain.Persistable#getId()}
   * @param revisions varargs with revision numbers
   * @param clazz     domain clazz
   *
   * @return {@link org.springframework.data.history.Revisions}
   */
  <T extends NAbstractAuditable> Revisions<Integer, T> findInRevisions(final Class<T> clazz, final Long id, final Integer... revisions);

  /**
   * Returns how many revisions exists for given {@link org.springframework.data.domain.Persistable#getId()} instance
   *
   * @param id    the id
   * @param clazz domain clazz
   *
   * @return revisions amount
   */
  <T extends NAbstractAuditable> long countRevisions(final Class<T> clazz, final Long id);
}

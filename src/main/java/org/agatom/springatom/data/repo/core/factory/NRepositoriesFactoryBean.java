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

package org.agatom.springatom.data.repo.core.factory;

import org.agatom.springatom.data.model.revision.AuditedRevisionEntity;
import org.agatom.springatom.data.repo.repositories.NAuditableRepository;
import org.hibernate.envers.DefaultRevisionEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.envers.repository.support.EnversRevisionRepositoryFactoryBean;
import org.springframework.data.envers.repository.support.ReflectionRevisionEntityInformation;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;
import org.springframework.data.repository.history.support.RevisionEntityInformation;

import javax.persistence.EntityManager;
import java.io.Serializable;


public class NRepositoriesFactoryBean
  extends EnversRevisionRepositoryFactoryBean {
  private Class<?> revisionEntityClass = null;

  /**
   * <p>Setter for the field <code>revisionEntityClass</code>.</p>
   *
   * @param revisionEntityClass a {@link Class} object.
   */
  public void setRevisionEntityClass(final Class<?> revisionEntityClass) {
    super.setRevisionEntityClass(revisionEntityClass);
    this.revisionEntityClass = revisionEntityClass;
  }

  /** {@inheritDoc} */
  @Override
  protected RepositoryFactorySupport createRepositoryFactory(final EntityManager entityManager) {
    return new NRepositoryFactory(entityManager, this.revisionEntityClass);
  }

  private static class NRepositoryFactory
    extends JpaRepositoryFactory {
    private static final Logger LOGGER = LoggerFactory.getLogger(NRepositoryFactory.class);
    private final Class<?>                  revisionEntityClass;
    private final RevisionEntityInformation revisionEntityInformation;

    public NRepositoryFactory(final EntityManager entityManager,
                              final Class<?> revisionEntityClass) {
      super(entityManager);

      this.revisionEntityClass = revisionEntityClass == null ? AuditedRevisionEntity.class : revisionEntityClass;
      this.revisionEntityInformation = DefaultRevisionEntity.class
        .equals(revisionEntityClass)
        ? new AuditingRevisionEntityInformation()
        : new ReflectionRevisionEntityInformation(this.revisionEntityClass);

      if (LOGGER.isTraceEnabled()) {
        LOGGER.trace(String.format("Created %s with arguments=[em=%s,rec=%s,rei=%s]",
          NRepositoryFactory.class.getSimpleName(),
          entityManager,
          this.revisionEntityClass,
          this.revisionEntityInformation));
      }
    }

    @Override
    @SuppressWarnings({"unchecked", "UnusedDeclaration"})
    protected <T, ID extends Serializable> SimpleJpaRepository<?, ?> getTargetRepository(final RepositoryMetadata metadata, final EntityManager entityManager) {
      final Class<?> domainType = metadata.getDomainType();
      final JpaEntityInformation<T, Serializable> entityInformation = (JpaEntityInformation<T, Serializable>) this.getEntityInformation(domainType);
      final Class<?> repositoryInterface = metadata.getRepositoryInterface();
      SimpleJpaRepository<?, ?> repository;
      if (NAuditableRepository.class.isAssignableFrom(repositoryInterface)) {
        repository = new NAuditableRepositoryImpl(entityInformation, this.revisionEntityInformation, entityManager);
      } else {
        repository = new NRepositoryImpl(entityInformation, entityManager);
      }
      return repository;
    }

    @Override
    protected Class<?> getRepositoryBaseClass(final RepositoryMetadata metadata) {
      final Class<?> repositoryInterface = metadata.getRepositoryInterface();
      if (NAuditableRepository.class.isAssignableFrom(repositoryInterface)) {
        return NAuditableRepositoryImpl.class;
      }
      return NRepositoryImpl.class;
    }
  }
}

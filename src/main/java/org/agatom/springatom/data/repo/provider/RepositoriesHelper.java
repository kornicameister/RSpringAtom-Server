/**************************************************************************************************
 * This file is part of [SpringAtom] Copyright [kornicameister@gmail.com][2014]                   *
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

package org.agatom.springatom.data.repo.provider;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.mapping.PersistentEntity;
import org.springframework.data.repository.core.CrudInvoker;
import org.springframework.data.repository.core.EntityInformation;
import org.springframework.data.repository.core.RepositoryInformation;
import org.springframework.data.repository.query.QueryMethod;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * <small>Class is a part of <b>SpringAtom</b> and was created at 2014-09-19</small>
 * </p>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
public interface RepositoriesHelper {

  @Cacheable("repositoriesHelper.getRepositoryFor")
  <T> Object getRepositoryFor(final Class<T> clazz);

  @Cacheable("repositoriesHelper.getEntityInformationFor")
  <T, S extends Serializable> EntityInformation<T, S> getEntityInformationFor(Class<?> domainClass);

  @Cacheable("repositoriesHelper.getRepositoryInformationFor")
  RepositoryInformation getRepositoryInformationFor(Class<?> domainClass);

  @Cacheable("repositoriesHelper.getPersistentEntity")
  PersistentEntity<?, ?> getPersistentEntity(Class<?> domainClass);

  @Cacheable("repositoriesHelper.getQueryMethodsFor")
  List<QueryMethod> getQueryMethodsFor(Class<?> domainClass);

  @Cacheable("repositoriesHelper.getCrudInvoker")
  <T> CrudInvoker<T> getCrudInvoker(Class<T> domainClass);

  @Cacheable("repositoriesHelper.getAuditableRepositories")
  List<RepositoryInformation> getAuditableRepositories();
}

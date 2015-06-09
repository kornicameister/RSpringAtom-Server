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

import org.agatom.springatom.data.repo.repositories.NRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.QueryDslJpaRepository;

import javax.persistence.EntityManager;


public class NRepositoryImpl<T>
  extends QueryDslJpaRepository<T, Long>
  implements NRepository<T> {
  private static final Logger                        LOGGER            = LoggerFactory.getLogger(NRepositoryImpl.class);
  protected            JpaEntityInformation<T, Long> entityInformation = null;
  protected            EntityManager                 entityManager     = null;

  /**
   * <p>Constructor for SBasicRepositoryImpl.</p>
   *
   * @param entityInformation a {@link org.springframework.data.jpa.repository.support.JpaEntityInformation} object.
   * @param entityManager     a {@link javax.persistence.EntityManager} object.
   */
  public NRepositoryImpl(final JpaEntityInformation<T, Long> entityInformation,
                         final EntityManager entityManager) {
    super(entityInformation, entityManager);
    this.entityInformation = entityInformation;
    this.entityManager = entityManager;
    if (LOGGER.isTraceEnabled()) {
      LOGGER.trace(String
        .format("Created %s for arguments=[em=%s,ei=%s]", NRepositoryImpl.class
          .getSimpleName(), entityManager, entityInformation));
    }
  }

}

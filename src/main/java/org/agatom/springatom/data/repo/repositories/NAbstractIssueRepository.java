/**************************************************************************************************
 * This file is part of [SpringAtom] Copyright [kornicameister@gmail.com][2013]                   *
 *                                                                                                *
 * [SpringAtom] is free software: you can redistribute it and/or modify                           *
 * it under the terms of the GNU General Public License as published by                           *
 * the Free Software Foundation, either version 3 of the License, or                              *
 * (at your option) any later version.                                                            *
 *                                                                                                *
 * [SpringAtom] is distributed in the hope that it will be useful,                                *
 * but WITHOUT ANY WARRANTY; without even the implied warranty of                                 *
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the                                  *
 * GNU General Public License for more details.                                                   *
 *                                                                                                *
 * You should have received a copy of the GNU General Public License                              *
 * along with [SpringAtom].  If not, see <http://www.gnu.org/licenses/gpl.html>.                  *
 **************************************************************************************************/

package org.agatom.springatom.data.repo.repositories;

import org.agatom.springatom.data.model.issue.NIssue;
import org.agatom.springatom.data.model.user.NUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;

@NoRepositoryBean
public interface NAbstractIssueRepository<T extends NIssue>
        extends NRepository<T> {

  @RestResource(path = "type")
  Page<T> findByType(@Param(value = "type") String issueType, Pageable pageable);

  @RestResource(path = "assignee")
  Page<T> findByAssignee(@Param(value = "assignee") NUser assignee, Pageable pageable);

  @RestResource(path = "reporter")
  Page<T> findByReporter(@Param(value = "reporter") NUser assignee, Pageable pageable);

  @RestResource(exported = false)
  Iterable<T> findByAssignee(final NUser assignee);

  @RestResource(exported = false)
  Iterable<T> findByType(final String issueType);

  @RestResource(exported = false)
  Iterable<T> findByReporter(final NUser assignee);

}

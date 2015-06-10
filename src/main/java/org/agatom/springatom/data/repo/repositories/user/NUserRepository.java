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

package org.agatom.springatom.data.repo.repositories.user;

import org.agatom.springatom.data.model.user.NUser;
import org.agatom.springatom.data.repo.repositories.NRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.Description;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.Optional;

@RepositoryRestResource(
  itemResourceRel = NUserRepository.ITEM_REL,
  itemResourceDescription = @Description("Get single user"),
  collectionResourceRel = NUserRepository.COLLECTION_REL,
  collectionResourceDescription = @Description("Get collection of users"),
  path = NUserRepository.REST_REPO_PATH
)
public interface NUserRepository
  extends NRepository<NUser> {
  String ITEM_REL       = "user";
  String COLLECTION_REL = "users";
  String REST_REPO_PATH = ITEM_REL;

  @RestResource(path = "mail")
  Optional<NUser> findByCredentialsEmail(@Param("mail") final String mail);

  @RestResource(path = "login")
  Optional<NUser> findByCredentialsUsername(@Param("login") final String login);

  @RestResource(path = "login_containing")
  Page<NUser> findByCredentialsUsernameContaining(
    @Param("login") final String login,
    final Pageable pageable
  );
}

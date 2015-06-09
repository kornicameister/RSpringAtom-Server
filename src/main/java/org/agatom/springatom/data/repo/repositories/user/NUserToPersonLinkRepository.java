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

import org.agatom.springatom.data.model.person.NPerson;
import org.agatom.springatom.data.model.user.NUser;
import org.agatom.springatom.data.model.user.NUserToPersonLink;
import org.agatom.springatom.data.repo.repositories.NRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.Description;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

@RepositoryRestResource(
  itemResourceRel = NUserToPersonLinkRepository.ITEM_REL,
  itemResourceDescription = @Description("Get single user to person association"),
  collectionResourceRel = NUserToPersonLinkRepository.COLLECTION_REL,
  collectionResourceDescription = @Description("Get collection of user to person associations"),
  path = NUserToPersonLinkRepository.REST_REPO_PATH
)
public interface NUserToPersonLinkRepository
  extends NRepository<NUserToPersonLink> {
  String ITEM_REL       = "user_person";
  String COLLECTION_REL = "user_persons";
  String REST_REPO_PATH = ITEM_REL;

  @RestResource(path = "user")
  @Query(name = "systemMemberByUser", value = "select SM from NUserToPersonLink as SM where SM.pk.roleA = :user")
  NUserToPersonLink findByUser(@Param("user") final NUser user);

  @RestResource(path = "person")
  @Query(name = "systemMemberByUser", value = "select SM from NUserToPersonLink as SM where SM.pk.roleB = :person")
  NUserToPersonLink findByPerson(@Param("person") final NPerson person);

}

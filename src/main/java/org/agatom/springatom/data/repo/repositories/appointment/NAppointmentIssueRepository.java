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

package org.agatom.springatom.data.repo.repositories.appointment;

import org.agatom.springatom.data.model.appointment.NAppointment;
import org.agatom.springatom.data.model.appointment.NAppointmentIssue;
import org.agatom.springatom.data.repo.repositories.NAbstractIssueRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.Description;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

@RepositoryRestResource(
        itemResourceRel = NAppointmentIssueRepository.ITEM_REL,
        itemResourceDescription = @Description("Get single appointment"),
        collectionResourceRel = NAppointmentIssueRepository.COLLECTION_REL,
        collectionResourceDescription = @Description("Get collection of appointments"),
        path = NAppointmentIssueRepository.REST_REPO_PATH
)
public interface NAppointmentIssueRepository
        extends NAbstractIssueRepository<NAppointmentIssue> {
    String ITEM_REL = "appointment_issue";
    String COLLECTION_REL = "appointment_issues";
    String REST_REPO_PATH = ITEM_REL;

    @RestResource(path = "appointment")
    Page<NAppointmentIssue> findByAppointment(@Param(value = "appointment") NAppointment appointment, Pageable pageable);
}

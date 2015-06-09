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
import org.agatom.springatom.data.model.appointment.NAppointmentTask;
import org.agatom.springatom.data.repo.repositories.NRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.Description;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.scheduling.annotation.Async;

import java.util.concurrent.Future;

/**
 * <p>SAppointmentTaskRepository interface.</p>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */

@RepositoryRestResource(
        itemResourceRel = NAppointmentTaskRepository.ITEM_REL,
        itemResourceDescription = @Description("Get single appointment task"),
        collectionResourceRel = NAppointmentTaskRepository.COLLECTION_REL,
        collectionResourceDescription = @Description("Get collection of appointments' tasks"),
        path = NAppointmentTaskRepository.REST_REPO_PATH
)
public interface NAppointmentTaskRepository
        extends NRepository<NAppointmentTask> {
    String ITEM_REL = "appointment_task";
    String COLLECTION_REL = "appointment_tasks";
    String REST_REPO_PATH = ITEM_REL;

    @Async
    @RestResource(path = "appointment",
            description = @Description("Looks for appointment tasks associated with appointment"))
    Page<NAppointmentTask> findByAppointmentOrderByTypeDesc(@Param(value = "appointment") NAppointment appointment, Pageable pageable);

    @Async
    @RestResource(path = "task_containing",
            description = @Description("Looks for appointment tasks where task value matches provided string"))
    Page<NAppointmentTask> findByTaskContainingOrderByTypeDesc(@Param(value = "task") String taskLike, Pageable pageable);

    @Async
    @RestResource(path = "type",
            description = @Description("Looks for appointment tasks where task is described by specific type"))
    Page<NAppointmentTask> findByTypeOrderByTypeDesc(@Param(value = "type") String type, Pageable pageable);

}

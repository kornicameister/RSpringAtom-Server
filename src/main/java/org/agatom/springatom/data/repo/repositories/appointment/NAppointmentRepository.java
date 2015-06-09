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
import org.agatom.springatom.data.model.car.NCar;
import org.agatom.springatom.data.model.user.NUser;
import org.agatom.springatom.data.repo.repositories.NRepository;
import org.joda.time.DateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.Description;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.scheduling.annotation.Async;

@RepositoryRestResource(
  itemResourceRel = NAppointmentRepository.ITEM_REL,
  itemResourceDescription = @Description("Get single appointment"),
  collectionResourceRel = NAppointmentRepository.COLLECTION_REL,
  collectionResourceDescription = @Description("Get collection of appointments"),
  path = NAppointmentRepository.REST_REPO_PATH
)
public interface NAppointmentRepository
  extends NRepository<NAppointment> {
  String ITEM_REL       = "appointment";
  String COLLECTION_REL = "appointments";
  String REST_REPO_PATH = ITEM_REL;

  @Async
  @RestResource(path = "between_begin_and_end",
    description = @Description("Looks up for appointment in given range using DateTime as boundaries"))
  Page<NAppointment> findByBeginAfterAndEndBeforeOrderByBeginAsc(
    @Param(value = "begin") DateTime begin,
    @Param(value = "end") DateTime end,
    Pageable pageable
  );

  @Async
  @RestResource(path = "between_begin_and_end_ts",
    description = @Description("Looks up for appointment in given range using timestamps as boundaries"))
  @Query(value = "select t from NAppointment as t where t.beginTs >= :begin and t.endTs <= :end")
  Page<NAppointment> findByBeginAfterAndEndBeforeOrderByBeginAsc(
    @Param(value = "begin") long begin,
    @Param(value = "end") long end,
    Pageable pageable
  );

  @Async
  @RestResource(path = "after_begin")
  Page<NAppointment> findByBeginAfterOrderByBeginAsc(@Param(value = "begin") DateTime begin, Pageable pageable);

  @Async
  @RestResource(path = "before_begin")
  Page<NAppointment> findByBeginBeforeOrderByBeginAsc(@Param(value = "begin") DateTime begin, Pageable pageable);

  @Async
  @RestResource(path = "after_end")
  Page<NAppointment> findByEndAfterOrderByBeginAsc(@Param(value = "end") DateTime begin, Pageable pageable);

  @Async
  @RestResource(path = "before_end")
  Page<NAppointment> findByEndBeforeOrderByBeginAsc(@Param(value = "end") DateTime begin, Pageable pageable);

  @Async
  @RestResource(path = "car")
  Page<NAppointment> findByCarOrderByBeginAsc(@Param(value = "car") final NCar car, Pageable pageable);

  @Async
  @RestResource(path = "car_licence_plate",
    description = @Description("Retrieves all appointments for car identified by licence plate"))
  Page<NAppointment> findByCarLicencePlateOrderByBeginAsc(@Param(value = "licence_plate") String licencePlate, Pageable pageable);

  @Async
  @RestResource(path = "car_vin_number",
    description = @Description("Retrieves all appointments for car identified by VIN number"))
  Page<NAppointment> findByCarVinNumberOrderByBeginAsc(@Param(value = "vin_number") String vinNumber, Pageable pageable);

  @Async
  @RestResource(path = "assignee",
    description = @Description("Retrieves all appointments assigned to user"))
  Page<NAppointment> findByAssigneeOrderByBeginAsc(@Param(value = "assignee") NUser assignee, Pageable pageable);

  @Async
  @RestResource(path = "reporter",
    description = @Description("Retrieves all appointments reported by user"))
  Page<NAppointment> findByReporterOrderByBeginAsc(@Param(value = "reporter") NUser assignee, Pageable pageable);
}

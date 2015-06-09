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

package org.agatom.springatom.data.model.appointment;

import org.agatom.springatom.data.model.NAbstractPersistable;
import org.agatom.springatom.data.types.appointment.AppointmentTask;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;

/**
 * <p>SAppointmentTask class.</p>
 *
 * @author kornicamaister
 * @version 0.0.1
 * @since 0.0.1
 */
@Table
@Entity
public class NAppointmentTask
        extends NAbstractPersistable
        implements AppointmentTask<NAppointment> {
  private static final long         serialVersionUID = -300491275397373687L;
  @NotEmpty
  @Length(min = 10, max = 444)
  @Column(nullable = false, length = 444)
  private              String       task             = null;
  @NotEmpty
  @Column(nullable = false, length = 50)
  private              String       type             = null;
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(updatable = false)
  private              NAppointment appointment      = null;

  @Override
  public String getTask() {
    return task;
  }

  @Override
  public NAppointment getAppointment() {
    return this.appointment;
  }

  @Override
  public String getType() {
    return type;
  }

  public NAppointmentTask setType(final String type) {
    this.type = type;
    return this;
  }

  public NAppointmentTask setAppointment(final NAppointment appointments) {
    this.appointment = appointments;
    return this;
  }

  public NAppointmentTask setTask(final String task) {
    this.task = task;
    return this;
  }

  @Override
  public NAppointment getAssignee() {
    return this.appointment;
  }
}

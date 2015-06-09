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

package org.agatom.springatom.data.model.appointment;


import org.agatom.springatom.data.model.issue.NIssue;
import org.agatom.springatom.data.types.appointment.AppointmentIssue;
import org.agatom.springatom.data.types.notification.SimpleNotification;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;

/**
 * <p>SAppointmentIssue class.</p>
 *
 * @author kornicamaister
 * @version 0.0.1
 * @since 0.0.1
 */
@Entity
@DiscriminatorValue(value = "nai")
public class NAppointmentIssue
  extends NIssue
  implements AppointmentIssue<NAppointment>, SimpleNotification {
  private static final long         serialVersionUID = 8658810841216821601L;
  private static final String       DATE_TIME_TYPE   = "org.jadira.usertype.dateandtime.joda.PersistentDateTime";
  @Type(type = DATE_TIME_TYPE)
  @Column(name = "nai_dt_sent", nullable = false)
  protected            DateTime     sent             = null;
  @Type(type = DATE_TIME_TYPE)
  @Column(name = "nai_dt_read", nullable = false)
  protected            Boolean      read             = null;
  @ManyToOne(fetch = FetchType.LAZY, optional = true)
  @JoinColumn(name = "nai_aid", updatable = false)
  private              NAppointment appointment      = null;

  public NAppointmentIssue() {
    super();
    this.sent = DateTime.now();
    this.read = false;
  }

  @Override
  public NAppointment getAppointment() {
    return appointment;
  }

  public NAppointmentIssue setAppointment(final NAppointment appointment) {
    this.appointment = appointment;
    return this;
  }

  @Override
  public DateTime getSent() {
    return this.sent;
  }

  @Override
  public Boolean isRead() {
    return this.read;
  }

  public NAppointmentIssue setSent(final DateTime sent) {
    this.sent = sent;
    return this;
  }

  public NAppointmentIssue setRead(final Boolean read) {
    this.read = read;
    return this;
  }

}

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

package org.agatom.springatom.data.model.notification;

import org.agatom.springatom.data.model.NAbstractPersistable;
import org.agatom.springatom.data.types.notification.Notification;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.joda.time.DateTime;
import org.springframework.data.domain.Persistable;
import org.springframework.util.ClassUtils;

import javax.persistence.*;

/**
 * <p>Abstract SAbstractNotification class.</p>
 *
 * @author kornicamaister
 * @version 0.0.1
 * @since 0.0.1
 */
@Table(indexes = {
        @Index(columnList = "nam_sent"),
        @Index(columnList = "nan_t,nan_s"),
})
@Entity
public class NNotification
        extends NAbstractPersistable
        implements Notification {
  private static final String               DATE_TIME_TYPE   = "org.jadira.usertype.dateandtime.joda.PersistentDateTime";
  private static final long                 serialVersionUID = -5518002639762454805L;
  @NotBlank
  @Length(max = 1000)
  @Column(name = "nan_msg", length = 1000, nullable = true)
  private              String               message          = null;
  @Column(name = "nan_read")
  private              Boolean              read             = false;
  @Type(type = DATE_TIME_TYPE)
  @Column(name = "nam_sent", nullable = false)
  private              DateTime             sent             = null;
  @JoinColumn(updatable = false, name = "nan_t")
  @OneToOne(fetch = FetchType.EAGER, optional = true, cascade = {
    CascadeType.REFRESH,
    CascadeType.REMOVE,
    CascadeType.PERSIST
  })
  private              NNotificationTarget  target           = null;
  @JoinColumn(updatable = false, name = "nan_s")
  @OneToOne(fetch = FetchType.EAGER, optional = true, cascade = {
    CascadeType.REFRESH,
    CascadeType.REMOVE,
    CascadeType.PERSIST
  })
  private              NNotificationSubject subject          = null;

  public NNotification() {
    super();
    this.sent = DateTime.now();
  }

  @Override
  public String getMessage() {
    return message;
  }

  @Override
  public DateTime getSent() {
    return null == this.sent ? null : this.sent;
  }

  @Override
  public Boolean isRead() {
    return this.read;
  }

  public NNotification setMessage(final String notification) {
    this.message = notification;
    return this;
  }

  @Override
  @SuppressWarnings("unchecked")
  public NNotificationTarget getTarget() {
    return target;
  }

  @Override
  @SuppressWarnings("unchecked")
  public NNotificationSubject getSubject() {
    return subject;
  }

  public NNotification setSubject(final Persistable<Long> subject) {
    this.subject = (NNotificationSubject) new NNotificationSubject().setAssociateId(subject.getId()).setAssociateClass(ClassUtils.getUserClass(subject));
    return this;
  }

  public NNotification setTarget(final Persistable<Long> target) {
    this.target = (NNotificationTarget) new NNotificationTarget().setAssociateId(target.getId()).setAssociateClass(ClassUtils.getUserClass(target));
    return this;
  }

  public NNotification setRead() {
    this.read = true;
    return this;
  }

  public NNotification setNotRead() {
    this.read = false;
    return this;
  }

  public NNotification setSent(final DateTime dt) {
    this.sent = new DateTime(dt);
    return this;
  }

}

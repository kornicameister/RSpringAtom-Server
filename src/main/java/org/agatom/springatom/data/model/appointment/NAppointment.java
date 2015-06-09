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

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.agatom.springatom.data.model.activity.NAssignedActivity;
import org.agatom.springatom.data.model.car.NCar;
import org.agatom.springatom.data.model.user.NUser;
import org.agatom.springatom.data.types.appointment.Appointment;
import org.agatom.springatom.data.types.appointment.AppointmentTask;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.Type;
import org.joda.time.*;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Table
@Entity
public class NAppointment
        extends NAssignedActivity
        implements Iterable<NAppointmentTask>, Appointment<NUser, NCar> {
  private static final String                 DATE_TIME_TYPE   = "org.jadira.usertype.dateandtime.joda.PersistentDateTime";
  private static final long                   serialVersionUID = -3158182089097228777L;
  @Type(type = DATE_TIME_TYPE)
  @Column(name = "begin", nullable = false)
  @NotNull(message = MSG.BEGIN_NULL_MSG)
  private              DateTime               begin            = null;
  @Type(type = DATE_TIME_TYPE)
  @Column(name = "end", nullable = false)
  @NotNull(message = MSG.END_NULL_MSG)
  private              DateTime               end              = null;
  @Size(min = 1, message = MSG.NO_TASK_MSG)
  @OneToMany(fetch = FetchType.LAZY, mappedBy = "appointment", cascade = CascadeType.ALL)
  @OnDelete(action = OnDeleteAction.CASCADE)
  private              List<NAppointmentTask> tasks            = null;
  @NotNull(message = MSG.CAR_NULL_MSG)
  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  @JoinColumn(name = "car")
  private              NCar                   car              = null;
  @Formula(value = "UNIX_TIMESTAMP(begin)")
  private              Long                   beginTs          = null;
  @Formula(value = "UNIX_TIMESTAMP(end)")
  private              Long                   endTs            = null;
  @Column(nullable = true)
  private              boolean                allDay           = false;
  @Column(nullable = true)
  private              boolean                closed           = false;

  @Override
  public boolean isClosed() {
    return this.closed;
  }

  public NAppointment setClosed(final boolean closed) {
    this.closed = closed;
    return this;
  }

  @Override
  public boolean isAllDay() {
    return this.allDay;
  }

  public NAppointment setAllDay(final boolean allDay) {
    this.allDay = allDay;
    return this;
  }

  @Override
  public Collection<AppointmentTask> getTasks() {
    this.requireTaskList();
    final Set<AppointmentTask> set = Sets.newHashSet();
    set.addAll(this.tasks);
    return Collections.unmodifiableSet(set);
  }

  public NAppointment setTasks(final Collection<NAppointmentTask> tasks) {
    Assert.notNull(tasks);
    return this.addTask(tasks);
  }

  public NAppointment addTask(final Collection<NAppointmentTask> tasks) {
    this.requireTaskList();
    for (final NAppointmentTask task : tasks) {
      if (!this.tasks.contains(task)) {
        this.tasks.add(task.setAppointment(this));
      }
    }
    return this;
  }

  @Override
  public DateTime getBegin() {
    this.requireBeginDate();
    return this.begin.toDateTime();
  }

  public NAppointment setBegin(final DateTime begin) {
    this.begin = begin;
    return this;
  }

  @Override
  public DateTime getEnd() {
    this.requireEndDate();
    return this.end.toDateTime();
  }

  public NAppointment setEnd(final DateTime end) {
    this.end = end;
    return this;
  }

  @Override
  public Interval getInterval() {
    return new Interval(this.begin, this.end);
  }

  public NAppointment setInterval(final ReadableInterval duration) {
    this.setBegin(duration.getStart());
    this.setEnd(duration.getEnd());
    return this;
  }

  @Override
  public NCar getCar() {
    if (this.car == null) {
      this.car = new NCar();
    }
    return this.car;
  }

  public NAppointment setCar(final NCar car) {
    this.car = car;
    return this;
  }

  private void requireTaskList() {
    if (this.tasks == null) {
      this.tasks = Lists.newLinkedList();
    }
  }

  public NAppointment removeTask(final NAppointmentTask... tasks) {
    if (this.tasks == null) {
      return this;
    }
    this.tasks.removeAll(Lists.newArrayList(tasks));
    return this;
  }

  public NAppointment assignTasks() {
    if (!CollectionUtils.isEmpty(this.tasks)) {
      for (final NAppointmentTask task : this.tasks) {
        task.setAppointment(this);
            }
        }
        return this;
    }

    public long getBeginTs() {
        if (this.beginTs != null) {
            return this.beginTs;
        }
        return TimeUnit.MILLISECONDS.toSeconds(this.begin.getMillis());
    }

    public long getEndTs() {
        if (this.endTs != null) {
            return this.endTs;
        }
        return TimeUnit.MILLISECONDS.toSeconds(this.end.getMillis());
    }

    public boolean postpone(final ReadableDuration duration, final boolean toFuture) {
        if (this.begin != null && this.end != null) {
            final int scalar = toFuture ? 1 : -1;
            this.begin = this.begin.withDurationAdded(duration, scalar);
            this.end = this.end.withDurationAdded(duration, scalar);
            return true;
        }
        return false;
    }

    public LocalTime getBeginTime() {
        this.requireBeginDate();
        return this.begin.toLocalTime();
    }

    public NAppointment setBeginTime(final LocalTime localTime) {
        this.requireBeginDate();
        final MutableDateTime mutableDateTime = this.begin.toMutableDateTime();
        mutableDateTime.setTime(localTime.toDateTimeToday());
        this.begin = mutableDateTime.toDateTime();
        return this;
    }

    private void requireBeginDate() {
        if (this.begin == null) {
            this.begin = DateTime.now();
        }
    }

    public LocalTime getEndTime() {
        this.requireEndDate();
        return this.end.toLocalTime();
    }

    public NAppointment setEndTime(final LocalTime localTime) {
        this.requireEndDate();
        final MutableDateTime mutableDateTime = this.end.toMutableDateTime();
        mutableDateTime.setTime(localTime.toDateTimeToday());
        this.end = mutableDateTime.toDateTime();
        return this;
    }

    private void requireEndDate() {
        if (this.end == null) {
            this.end = DateTime.now();
        }
    }

    public LocalDate getBeginDate() {
        return this.getBegin().toLocalDate();
    }

    public NAppointment setBeginDate(final LocalDate localDate) {
        this.requireBeginDate();
        final MutableDateTime mutableDateTime = this.begin.toMutableDateTime();
        mutableDateTime.setDate(localDate.getYear(), localDate.getMonthOfYear(), localDate.getDayOfMonth());
        this.begin = mutableDateTime.toDateTime();
        return this;
    }

    public LocalDate getEndDate() {
        return this.getEnd().toLocalDate();
    }

    public NAppointment setEndDate(final LocalDate localDate) {
        this.requireEndDate();
        final MutableDateTime mutableDateTime = this.end.toMutableDateTime();
        mutableDateTime.setDate(localDate.getYear(), localDate.getMonthOfYear(), localDate.getDayOfMonth());
        this.end = mutableDateTime.toDateTime();
        return this;
    }

    @Override
    public Iterator<NAppointmentTask> iterator() {
        return this.tasks.iterator();
    }

    private static class MSG {
        static final String BEGIN_NULL_MSG = "Begin dateTime for event must not be null";
        static final String END_NULL_MSG   = "End dateTime for event must not be null";
        static final String CAR_NULL_MSG   = "Car for event must not be null";
        static final String NO_TASK_MSG    = "SAppointment must contain at least one task [SAppointmentTask]";
    }
}

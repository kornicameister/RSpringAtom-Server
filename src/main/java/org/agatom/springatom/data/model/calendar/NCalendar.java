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

package org.agatom.springatom.data.model.calendar;

import org.agatom.springatom.data.model.NAbstractPersistable;
import org.agatom.springatom.data.model.user.NUser;
import org.agatom.springatom.data.types.calendar.Calendar;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.awt.*;

@Table
@Entity
public class NCalendar
        extends NAbstractPersistable
        implements Calendar<NUser> {
  private static final long serialVersionUID = -3784841008638697396L;
  @Column(name = "cal_name", nullable = false, length = 50)
  private String name;
  @Column(name = "cal_hex_color", nullable = false)
  private Color  color;
  @NotNull
  @ManyToOne(optional = false, fetch = FetchType.EAGER)
  @JoinColumn(name = "cal_owner", updatable = true)
  @OnDelete(action = OnDeleteAction.CASCADE)
  private NUser  owner;

  @Override
  public Color getColor() {
    return color;
  }

  public NCalendar setColor(final Color color) {
    this.color = color;
    return this;
  }

  @Override
  public NUser getOwner() {
    return owner;
  }

  public NCalendar setOwner(final NUser owner) {
    this.owner = owner;
    return this;
  }

  @Override
  public String getName() {
    return name;
  }

  public NCalendar setName(final String name) {
    this.name = name;
    return this;
  }

  @Override
  public NUser getAssignee() {
    return this.getOwner();
  }
}

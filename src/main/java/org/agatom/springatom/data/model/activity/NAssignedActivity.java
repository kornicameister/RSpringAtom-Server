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

package org.agatom.springatom.data.model.activity;

import org.agatom.springatom.data.types.activity.AssignedActivity;
import org.agatom.springatom.data.model.user.NUser;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

@MappedSuperclass
abstract public class NAssignedActivity
        extends NActivity
        implements AssignedActivity<NUser> {
  private static final long serialVersionUID = -8389898294284589238L;
  @NotNull
  @ManyToOne(optional = false, fetch = FetchType.EAGER)
  @JoinColumn(name = "naa_u_id", updatable = true)
  @OnDelete(action = OnDeleteAction.CASCADE)
  protected NUser assignee;

  /** {@inheritDoc} */
  @Override
  public NUser getAssignee() {
    if (this.assignee == null) {
      this.assignee = new NUser();
    }
    return this.assignee;
  }

  public NAssignedActivity setAssignee(final NUser assignee) {
    this.assignee = assignee;
    return this;
  }
}

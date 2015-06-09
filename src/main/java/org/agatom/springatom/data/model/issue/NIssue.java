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

package org.agatom.springatom.data.model.issue;

import org.agatom.springatom.data.model.activity.NAssignedActivity;
import org.agatom.springatom.data.model.user.NUser;
import org.agatom.springatom.data.types.issue.Issue;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(
        indexes = {
                @Index(name = "ni_type", columnList = "ni_type")
        }
)
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = NIssue.ISSUE_TYPE, discriminatorType = DiscriminatorType.STRING)
public class NIssue
        extends NAssignedActivity
        implements Issue<NUser> {
  protected static final String ISSUE_TYPE       = "sit";
  private static final   long   serialVersionUID = 9153732479122207895L;
  @NotNull
  @Column(name = "ni_msg", nullable = false)
  private                String message          = null;
  @NotEmpty
  @Column(name = "ni_type", nullable = false, length = 50)
  private                String type             = null;

  @Override
  public String getMessage() {
    return this.message;
  }

  public void setMessage(final String message) {
    this.message = message;
  }

  @Override
  public String getType() {
    return this.type;
  }

  public void setType(final String type) {
    this.type = type;
  }

}

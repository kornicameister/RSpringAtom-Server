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

import org.agatom.springatom.data.types.activity.Activity;
import org.agatom.springatom.data.model.NAbstractPersistable;
import org.agatom.springatom.data.model.user.NUser;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Length;
import org.joda.time.DateTime;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

/**
 * {@code SActivity} is a generic abstract class for all sorts of activities.
 * Is is an <strong>embeddable</strong> object which means that properties specified within does not lie in external
 * table but are embedded within the table which class embeds the {@link Activity}
 *
 * @author kornicamaister
 * @version 0.0.1
 * @since 0.0.1
 */
@MappedSuperclass
abstract public class NActivity
        extends NAbstractPersistable
        implements Activity {
  private static final String   DATE_TIME_TYPE   = "org.jadira.usertype.dateandtime.joda.PersistentDateTime";
  private static final long     serialVersionUID = 2584800898826870084L;
  @NotNull
  @ManyToOne(optional = false, fetch = FetchType.EAGER)
  @JoinColumn(name = "na_r_id", updatable = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  protected            NUser    reporter         = null;
  @Past
  @Type(type = DATE_TIME_TYPE)
  @Column(name = "na_asg", nullable = false)
  protected            DateTime assigned         = null;
  @Length(max = 500)
  @Column(name = "na_com", nullable = true, length = 500)
  private              String   comment          = null;

  /**
   * <p>Constructor for SActivity.</p>
   */
  public NActivity() {
    this.assigned = DateTime.now();
  }

  /** {@inheritDoc} */
  @Override
  public NUser getReporter() {
    if (this.reporter == null) {
      this.reporter = new NUser();
    }
    return reporter;
  }

  public NActivity setReporter(final NUser user) {
    this.reporter = user;
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public DateTime getAssigned() {
    return null == this.assigned ? null : new DateTime(this.assigned);
  }

  public NActivity setAssigned(final DateTime assigned) {
    this.assigned = assigned;
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public String getComment() {
    return comment;
  }

  public NActivity setComment(final String comment) {
    this.comment = comment;
    return this;
  }

}

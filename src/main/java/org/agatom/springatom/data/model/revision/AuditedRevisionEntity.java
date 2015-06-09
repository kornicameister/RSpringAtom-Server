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

package org.agatom.springatom.data.model.revision;

import org.hibernate.envers.DefaultTrackingModifiedEntitiesRevisionEntity;
import org.hibernate.envers.RevisionEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * <p>AuditedRevisionEntity class.</p>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@Table(name = "revinfo")
@Entity(name = "revinfo")
@RevisionEntity(value = AuditingRevisionEntity.class)
public class AuditedRevisionEntity
        extends DefaultTrackingModifiedEntitiesRevisionEntity {
  private static final long   serialVersionUID = -3255456739922989639L;
  @Column(length = 50)
  private              String user             = null;

  /**
   * <p>Getter for the field <code>user</code>.</p>
   *
   * @return a {@link String} object.
   */
  public String getUser() {
    return user;
  }

  /**
   * <p>Setter for the field <code>user</code>.</p>
   *
   * @param user a {@link String} object.
   */
  public void setUser(final String user) {
    this.user = user;
  }
}

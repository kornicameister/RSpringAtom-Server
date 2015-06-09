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

package org.agatom.springatom.data.model.user.authority;

import org.agatom.springatom.data.model.NAbstractPersistable;
import org.agatom.springatom.data.types.user.authority.Role;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * <p>SAuthority class.</p>
 *
 * @author kornicamaister
 * @version 0.0.1
 * @since 0.0.1
 */
@Table
@Entity
public class NRole
        extends NAbstractPersistable
        implements Role {
  private static final long   serialVersionUID = 2893594861541235345L;
  @Column(updatable = false, unique = true, length = 50, nullable = false)
  private              String authority        = null;

  public NRole() {
  }

  public NRole(final String authority) {
    this.authority = authority;
  }

  @Override
  public String getAuthority() {
    return this.authority;
  }

  @Override
  public AuthorityType getAuthorityType() {
    return AuthorityType.ROLE;
  }

  public void setAuthority(final String authority) {
    this.authority = authority;
  }

}

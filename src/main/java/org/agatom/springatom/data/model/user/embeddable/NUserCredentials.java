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

package org.agatom.springatom.data.model.user.embeddable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.agatom.springatom.data.types.user.Credentials;
import org.hibernate.annotations.NaturalId;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.UniqueConstraint;
import java.io.Serializable;

/**
 * {@code SUserCredentials} is {@link javax.persistence.Embeddable} object used as placeholder
 * for credentials available to <b>user</b> of the application.
 *
 * @author kornicameister
 * @version 0.0.2
 * @since 0.0.1
 */

@Embeddable
public class NUserCredentials
        implements Serializable, Credentials {
  private static final long   serialVersionUID = -4152612128922561019L;
  @NotEmpty
  @NaturalId
  @Column(length = 45, unique = true, nullable = false)
  private              String username         = null;
  @JsonIgnore
  @NotEmpty
  @Column(length = 66, nullable = false)
  private              String password         = null;
  @NotEmpty
  @Column(length = 66, nullable = false)
  private              String email         = null;

  @Override
  public String getUsername() {
    return username;
  }

  public NUserCredentials setUsername(final String login) {
    this.username = login;
    return this;
  }

  @Override
  public String getPassword() {
    return password;
  }

  public NUserCredentials setPassword(final String password) {
    this.password = password;
    return this;
  }

  @Override
  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  /** {@inheritDoc} */
  @Override
  public int hashCode() {
    int result = username != null ? username.hashCode() : 0;
    result = 31 * result + (password != null ? password.hashCode() : 0);
    return result;
  }

  /** {@inheritDoc} */
  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof NUserCredentials)) {
      return false;
    }

    final NUserCredentials that = (NUserCredentials) o;

    return !(username != null ? !username
      .equals(that.username) : that.username != null) && !(password != null ? !password
      .equals(that.password) : that.password != null);
  }
}

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

package org.agatom.springatom.data.model.user;

import org.agatom.springatom.data.model.NAbstractAuditable;
import org.agatom.springatom.data.model.user.embeddable.NUserCredentials;
import org.agatom.springatom.data.types.user.User;
import org.hibernate.annotations.Type;
import org.hibernate.envers.Audited;

import javax.persistence.*;

@Entity
@Table(
  uniqueConstraints = {
    @UniqueConstraint(name = "user_username", columnNames = "username"),
    @UniqueConstraint(name = "user_email", columnNames = "email"),
  },
  indexes = {
    @Index(name = "user_password", columnList = "password"),
    @Index(name = "user_enabled", columnList = "enabled"),
    @Index(name = "user_ane", columnList = "accountNonExpired"),
    @Index(name = "user_anl", columnList = "accountNonLocked"),
    @Index(name = "user_cne", columnList = "credentialsNonExpired")
  }
)
public class NUser
  extends NAbstractAuditable
  implements User {
  private static final long             serialVersionUID      = -5918876176226057267L;
  @Audited
  @Embedded
  private              NUserCredentials credentials           = null;
  @Column
  @Audited
  @Type(type = "boolean")
  private              Boolean          enabled               = Boolean.TRUE;
  @Column
  @Audited
  @Type(type = "boolean")
  private              boolean          accountNonExpired     = Boolean.TRUE;
  @Column
  @Audited
  @Type(type = "boolean")
  private              boolean          accountNonLocked      = Boolean.TRUE;
  @Column
  @Audited
  @Type(type = "boolean")
  private              boolean          credentialsNonExpired = Boolean.TRUE;

  public NUser() {
    this.credentials = new NUserCredentials();
  }

  /** {@inheritDoc} */
  @Override
  public String getPassword() {
    return this.credentials.getPassword();
  }

  /** {@inheritDoc} */
  @Override
  public String getUsername() {
    return this.credentials.getUsername();
  }

  /** {@inheritDoc} */
  @Override
  public boolean isAccountNonExpired() {
    return this.accountNonExpired;
  }

  /** {@inheritDoc} */
  @Override
  public boolean isAccountNonLocked() {
    return this.accountNonLocked;
  }

  /** {@inheritDoc} */
  @Override
  public boolean isCredentialsNonExpired() {
    return this.credentialsNonExpired;
  }

  /** {@inheritDoc} */
  @Override
  public boolean isEnabled() {
    return this.enabled;
  }

  public NUser setEnabled(final boolean disabled) {
    this.enabled = disabled;
    return this;
  }

  public NUser setCredentialsNonExpired(final boolean credentialsNonExpired) {
    this.credentialsNonExpired = credentialsNonExpired;
    return this;
  }

  public NUser setAccountNonLocked(final boolean accountNonLocked) {
    this.accountNonLocked = accountNonLocked;
    return this;
  }

  public NUser setAccountNonExpired(final boolean accountNonExpired) {
    this.accountNonExpired = accountNonExpired;
    return this;
  }

  public NUser setUsername(final String login) {
    this.credentials.setUsername(login);
    return this;
  }

  public NUser setPassword(final String password) {
    this.credentials.setPassword(password);
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public String getEmail() {
    return this.credentials.getEmail();
  }

  public NUser setEmail(final String email) {
    this.credentials.setEmail(email);
    return this;
  }
}

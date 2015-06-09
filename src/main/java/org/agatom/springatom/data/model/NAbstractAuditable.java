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

package org.agatom.springatom.data.model;

import org.agatom.springatom.data.model.user.NUser;
import org.agatom.springatom.data.types.NAuditable;
import org.springframework.data.jpa.domain.AbstractAuditable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.annotation.Nonnull;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import javax.validation.constraints.Min;

/**
 * <p>Abstract PersistentVersionedObject class.</p>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@MappedSuperclass
@EntityListeners(value = AuditingEntityListener.class)
abstract public class NAbstractAuditable
  extends AbstractAuditable<NUser, Long>
  implements NAuditable {
  private static final long serialVersionUID = -3113664043161581649L;
  @Version
  @Column(name = "pvo_version")
  private              Long version          = null;

  @Override
  public String getIdentity() {
    return String.format("%s.%s", this.getId(), this.getVersion());
  }

  @Override
  public Long getVersion() {
    return this.version;
  }

  public void setVersion(@Nonnull @Min(value = 0) final Long version) {
    this.version = version;
  }
}

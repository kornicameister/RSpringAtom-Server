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

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.agatom.springatom.data.types.NPersistable;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Cacheable;
import javax.persistence.MappedSuperclass;

/**
 * <p>Abstract PersistentObject class.</p>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@Cacheable(value = true)
@DynamicInsert
@DynamicUpdate
@MappedSuperclass
@Access(value = AccessType.FIELD)
abstract public class NAbstractPersistable
  extends AbstractPersistable<Long>
  implements NPersistable<Long> {
  private static final long    serialVersionUID = -6950914229850313642L;
  private transient    Integer hash             = null;

  @Override
  @JsonIgnore
  public boolean isNew() {
    return super.isNew();
  }

  @Override
  @JsonIgnore
  public String getIdentity() {
    return String.valueOf(this.getId());
  }

  @Override
  public boolean equals(Object other) {
    final Long id = this.getId();
    return this == other || id != null
      && other instanceof NAbstractPersistable
      && id.equals(((NAbstractPersistable) other).getId());
  }

  @Override
  public int hashCode() {
    if (hash == null) {
      final Long id = this.getId();
      hash = id == null ? System.identityHashCode(this) : id.hashCode();
    }
    return hash.hashCode();
  }
}

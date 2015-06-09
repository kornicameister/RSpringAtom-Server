/**************************************************************************************************
 * This file is part of [SpringAtom] Copyright [kornicameister@gmail.com][2014]                   *
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

package org.agatom.springatom.data.model.enumeration;

import com.google.common.collect.ComparisonChain;
import org.agatom.springatom.data.model.NAbstractPersistable;
import org.agatom.springatom.data.types.enumeration.EnumerationEntry;

import javax.annotation.Nonnull;
import javax.persistence.*;

/**
 * <p>
 * <small>Class is a part of <b>SpringAtom</b> and was created at 2014-08-25</small>
 * </p>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
@Table(
        indexes = {
                @Index(name = "entry_val", columnList = "ee_val")
        },
        uniqueConstraints = {
                @UniqueConstraint(name = "entry_key", columnNames = "ee_key")
        }
)
@Entity
public class NEnumerationEntry
        extends NAbstractPersistable
        implements EnumerationEntry {
  private static final long         serialVersionUID = -8222922908373100651L;
  @Column(name = "ee_key", nullable = false, length = 50, unique = true)
  private              String       key              = null;
  @Column(name = "ee_val", nullable = false, length = 50)
  private              String       value            = null;
  @Column(name = "ee_com", nullable = false, length = 500)
  private              String       comment          = null;
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "ee_eid", updatable = false)
  private              NEnumeration entry            = null;

  @Override
  public String getValue() {
    return this.value;
  }

  public String getKey() {
    return this.key;
  }

  public String getComment() {
    return this.comment;
  }

  public NEnumerationEntry setComment(final String comment) {
    this.comment = comment;
    return this;
  }

  public NEnumerationEntry setKey(final String key) {
    this.key = key;
    return this;
  }

  public NEnumerationEntry setValue(final String value) {
    this.value = value;
    return this;
  }

  public NEnumeration getEntry() {
    return entry;
  }

  public NEnumerationEntry setEntry(final NEnumeration entry) {
    this.entry = entry;
    return this;
  }

  @Override
  public int compareTo(@Nonnull final EnumerationEntry o) {
    return ComparisonChain.start()
      .compare(this.key, o.getKey())
      .compare(this.value, o.getValue())
      .result();
  }
}

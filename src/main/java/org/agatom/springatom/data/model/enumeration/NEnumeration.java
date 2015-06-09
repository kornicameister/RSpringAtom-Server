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

import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Sets;
import org.agatom.springatom.data.model.NAbstractPersistable;
import org.agatom.springatom.data.types.enumeration.Enumeration;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.util.CollectionUtils;

import javax.annotation.Nullable;
import javax.persistence.*;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "e_name", columnNames = "name")
})
public class NEnumeration
        extends NAbstractPersistable
        implements Enumeration<NEnumerationEntry> {
  private static final long                          serialVersionUID = 8265311348298963881L;
  @Column(nullable = false, length = 50, unique = true)
  private              String                        name             = null;
  @OneToMany(fetch = FetchType.LAZY, mappedBy = "entry", cascade = CascadeType.ALL)
  @OnDelete(action = OnDeleteAction.CASCADE)
  private              Collection<NEnumerationEntry> entries          = null;

  @Override
  public String getName() {
    return this.name;
  }

  public NEnumeration setName(final String name) {
    this.name = name;
    return this;
  }

  @Override
  public Collection<NEnumerationEntry> getEntries() {
    return this.entries;
  }

  @Override
  public int size() {
    return this.requireEntries().size();
  }

  public NEnumeration setEntries(final List<NEnumerationEntry> entries) {
    if (CollectionUtils.isEmpty(entries)) {
      throw new IllegalArgumentException("entries can not be null / empty");
    }
    final NEnumeration self = this;
    this.entries = FluentIterable.from(entries).transform(new Function<NEnumerationEntry, NEnumerationEntry>() {
      @Nullable
      @Override
      public NEnumerationEntry apply(final NEnumerationEntry input) {
        return input.setEntry(self);
      }
    }).toSet();
    return this;
  }

  @Override
  public Iterator<NEnumerationEntry> iterator() {
    return this.requireEntries().iterator();
  }

  private Collection<NEnumerationEntry> requireEntries() {
    if (this.entries == null) {
      this.entries = Sets.newHashSet();
    }
    return this.entries;
  }

  public NEnumeration addEntry(final NEnumerationEntry entry) {
    if (entry == null) {
      return this;
    }
    this.requireEntries().add(entry);
    return this;
  }

  public NEnumeration removeEntry(final NEnumerationEntry entry) {
    if (this.entries == null || entry == null) {
      return this;
    }
    this.requireEntries().remove(entry);
    return this;
  }
}

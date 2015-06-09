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

package org.agatom.springatom.data.model.person;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.agatom.springatom.data.model.NAbstractPersistable;
import org.agatom.springatom.data.types.contact.MultiContactable;
import org.agatom.springatom.data.types.person.Person;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.util.CollectionUtils;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * <p>SPerson class.</p>
 *
 * @author kornicamaister
 * @version 0.0.1
 * @since 0.0.1
 */

@Entity
@Table(
        indexes = {
                @Index(name = "p_last_name", columnList = "lastName")
        }
)
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
public class NPerson
        extends NAbstractPersistable
        implements Person, MultiContactable<NPersonContact, NPerson> {
  private static final long serialVersionUID = -8306142304138446067L;
  @NotEmpty
  @Length(min = 3, max = 45)
  @Column(length = 45, nullable = false)
  private String               firstName;
  @NotEmpty
  @Length(min = 3, max = 45)
  @Column(length = 45, nullable = false)
  private String               lastName;
  @BatchSize(size = 10)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, mappedBy = "assignee")
  private List<NPersonContact> contacts;

  @Override
  public String getFirstName() {
    return firstName;
  }

  public NPerson setFirstName(final String firstName) {
    this.firstName = firstName;
    return this;
  }

  @Override
  public String getLastName() {
    return lastName;
  }

  public NPerson setLastName(final String lastName) {
    this.lastName = lastName;
    return this;
  }

  public boolean removeContact(final Collection<NPersonContact> contacts) {
    if (contacts.size() > 0 && this.contacts != null) {
      final Set<NPersonContact> thisContacts = Sets.newHashSet(this.getContacts());
      final Set<NPersonContact> contactSet = Sets.newHashSet(contacts);
      final Set<NPersonContact> difference = Sets.union(thisContacts, contactSet).immutableCopy();
      return this.contacts.removeAll(difference);
    }
    return false;
  }

  /** {@inheritDoc} */
  @Override
  public List<NPersonContact> getContacts() {
    if (this.contacts == null) {
      this.contacts = Lists.newArrayListWithCapacity(10);
    }
    return this.contacts;
  }

  @Override
  public String getName() {
    return this.getLastName();
  }

  public NPerson setContacts(final List<NPersonContact> contacts) {
    this.addContact(contacts);
    return this;
  }

  public boolean addContact(final Collection<NPersonContact> contacts) {
    if (!CollectionUtils.isEmpty(contacts)) {
      for (NPersonContact contact : contacts) {
        contact.setAssignee(this);
        this.getContacts().add(contact);
      }
      return true;
    }
    return false;
  }


}

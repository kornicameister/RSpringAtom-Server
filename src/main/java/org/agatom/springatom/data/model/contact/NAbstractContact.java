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

package org.agatom.springatom.data.model.contact;

import org.agatom.springatom.data.model.NAbstractPersistable;
import org.agatom.springatom.data.types.contact.Contact;
import org.hibernate.envers.Audited;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;

/**
 * <p>Abstract SAbstractContact class.</p>
 *
 * @author kornicamaister
 * @version 0.0.1
 * @since 0.0.1
 */

@Audited
@Table
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = NAbstractContact.CONTACT_FOR, discriminatorType = DiscriminatorType.STRING)
abstract public class NAbstractContact<SC_H extends Persistable>
  extends NAbstractPersistable
  implements Contact<SC_H> {
  protected static final String CONTACT_FOR      = "sac_for";
  private static final   long   serialVersionUID = 8494361809629647372L;
  @Length(min = 5, max = 60)
  @Column(length = 60)
  protected              String contact          = null;
  @NotEmpty
  @Column(nullable = false, length = 50)
  private                String type             = null;

  @Override
  public String getContact() {
    return contact;
  }

  public NAbstractContact setContact(final String contact) {
    this.contact = contact;
    return this;
  }

  @Override
  public String getType() {
    return this.type;
  }

  public NAbstractContact setType(final String type) {
    this.type = type;
    return this;
  }

}

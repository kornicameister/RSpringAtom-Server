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

import org.agatom.springatom.data.model.contact.NAbstractContact;
import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.Audited;

import javax.persistence.*;

/**
 * <p>SPersonContact class.</p>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@Entity
@Audited
@AuditOverride(name = "contact",
        forClass = NAbstractContact.class
)
@DiscriminatorValue("person")
public class NPersonContact
        extends NAbstractContact<NPerson> {
  private static final long    serialVersionUID = 86397657105677805L;
  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  @JoinColumn(name = "assignee", updatable = true)
  protected            NPerson assignee         = null;

  /** {@inheritDoc} */
  @Override
  public NPerson getAssignee() {
    return this.assignee;
  }

  public void setAssignee(final NPerson assignee) {
    this.assignee = assignee;
  }
}

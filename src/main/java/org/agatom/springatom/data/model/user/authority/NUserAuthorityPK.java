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

import org.agatom.springatom.data.model.user.NUser;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

/**
 * <p>SUserAuthorityPK class.</p>
 *
 * @author kornicamaister
 * @version 0.0.1
 * @since 0.0.1
 */
@Embeddable
public class NUserAuthorityPK
        implements Serializable {
    private static final long serialVersionUID = -605204180160028113L;
    @ManyToOne
    @JoinColumn
    private NUser user;
    @ManyToOne
    @JoinColumn
    private NRole role;

    public NUserAuthorityPK() {
        super();
    }

    public NUserAuthorityPK(final NUser user, final NRole role) {
        this.user = user;
        this.role = role;
    }

    public NUser getUser() {
        return user;
    }

    public void setUser(final NUser user) {
        this.user = user;
    }

    public NRole getRole() {
        return role;
    }

    public void setRole(final NRole role) {
        this.role = role;
    }
}

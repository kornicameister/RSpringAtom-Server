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


import org.agatom.springatom.data.model.link.NObjectToObjectLink;
import org.agatom.springatom.data.model.user.NUser;

import javax.persistence.*;

/**
 * <p>SUserAuthority class.</p>
 *
 * @author kornicamaister
 * @version 0.0.1
 * @since 0.0.1
 */
@Entity
@Table
@AssociationOverrides({
        @AssociationOverride(name = UserToRoleLink.ROLE_USER, joinColumns = @JoinColumn(name = "user")),
        @AssociationOverride(name = UserToRoleLink.ROLE_ROLE, joinColumns = @JoinColumn(name = "role"))
})
public class UserToRoleLink
        extends NObjectToObjectLink<NUser, NRole> {
    public static final  String ROLE_USER        = ROLE_A_ASSOC;
    public static final  String ROLE_ROLE        = ROLE_B_ASSOC;
    private static final long   serialVersionUID = -61697944597116291L;

    public UserToRoleLink() {
        super();
    }

    public UserToRoleLink(final NUser user, final NRole role) {
        super();
        this.setRoleA(user);
        this.setRoleB(role);
    }

    @Transient
    public NUser getUser() {
        return this.getRoleA();
    }

    @Transient
    public void setUser(final NUser user) {
        this.setRoleA(user);
    }

    @Transient
    public NRole getRole() {
        return this.getRoleB();
    }

    @Transient
    public void setRole(final NRole role) {
        this.setRoleB(role);
    }
}

package org.agatom.springatom.data.model.user;


import org.agatom.springatom.data.types.user.SystemMember;
import org.agatom.springatom.data.model.link.NObjectToObjectLink;
import org.agatom.springatom.data.model.person.NPerson;

import javax.persistence.*;

/**
 * <p>
 * <small>Class is a part of <b>SpringAtom2</b> and was created at 2014-09-29</small>
 * </p>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
@Table
@Entity
@AssociationOverrides({
        @AssociationOverride(name = NObjectToObjectLink.ROLE_A_ASSOC, joinColumns = @JoinColumn(name = "nu_id")),
        @AssociationOverride(name = NObjectToObjectLink.ROLE_B_ASSOC, joinColumns = @JoinColumn(name = "np_id"))
})
public class NUserToPersonLink
        extends NObjectToObjectLink<NUser, NPerson>
        implements SystemMember<NUser, NPerson> {
    private static final long serialVersionUID = -5771858396401813077L;

    @Override
    public NPerson getPerson() {
        return this.getRoleB();
    }

    public NUserToPersonLink setPerson(final NPerson person) {
        return (NUserToPersonLink) this.setRoleB(person);
    }

    @Override
    public NUser getUser() {
        return this.getRoleA();
    }

    public NUserToPersonLink setUser(final NUser user) {
        return (NUserToPersonLink) this.setRoleA(user);
    }

}

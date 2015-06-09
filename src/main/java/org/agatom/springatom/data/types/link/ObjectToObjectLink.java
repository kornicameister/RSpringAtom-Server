package org.agatom.springatom.data.types.link;

import java.io.Serializable;

/**
 * <p>
 * <small>Class is a part of <b>SpringAtom2</b> and was created at 2014-09-25</small>
 * </p>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
public interface ObjectToObjectLink<A extends Serializable, B extends Serializable>
        extends Link {
    A getRoleA();

    B getRoleB();

    Object getRole(final Role role);

    static enum Role {
        ROLE_A,
        ROLE_B
    }
}

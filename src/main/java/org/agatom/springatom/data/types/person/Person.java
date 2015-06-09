package org.agatom.springatom.data.types.person;

import org.agatom.springatom.data.types.named.Named;

/**
 * <p>
 * <small>Class is a part of <b>SpringAtom2</b> and was created at 2014-09-28</small>
 * </p>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
public interface Person
        extends Named {
    String getFirstName();

    String getLastName();
}

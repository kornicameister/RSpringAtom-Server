package org.agatom.springatom.data.types.user;


import org.agatom.springatom.data.types.person.Person;
import org.agatom.springatom.data.types.person.Personable;

/**
 * <p>
 * <small>Class is a part of <b>SpringAtom2</b> and was created at 2014-09-29</small>
 * </p>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
public interface SystemMember<U extends User, P extends Person>
        extends Personable<P> {

    U getUser();

}

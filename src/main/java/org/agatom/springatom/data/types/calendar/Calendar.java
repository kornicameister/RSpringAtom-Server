package org.agatom.springatom.data.types.calendar;

import org.agatom.springatom.data.types.assignable.Assigned;
import org.agatom.springatom.data.types.user.User;

import java.awt.*;

/**
 * <p>
 * <small>Class is a part of <b>SpringAtom2</b> and was created at 2014-09-29</small>
 * </p>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
public interface Calendar<U extends User>
        extends Assigned<U> {
    Color getColor();

    U getOwner();

    String getName();
}

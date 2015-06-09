package org.agatom.springatom.data.types.car;

import org.agatom.springatom.data.types.user.User;

import java.util.Collection;

/**
 * <p>
 * <small>Class is a part of <b>SpringAtom2</b> and was created at 2014-09-26</small>
 * </p>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
public interface CarOwnership {
    User getOwner();

    Collection<Car> getCars();
}

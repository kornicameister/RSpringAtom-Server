package org.agatom.springatom.data.types.rupdate;

import org.agatom.springatom.data.types.reference.EntityReference;
import org.joda.time.DateTime;
import org.springframework.data.domain.Persistable;

/**
 * {@code RecentUpdate} describes an API of the model
 *
 * <p>
 * <small>Class is a part of <b>SpringAtom2</b> and was created at 2014-12-02</small>
 * </p>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
public interface RecentUpdate
        extends Persistable<Long> {
    /**
     * Retrieve reference to the underlying object
     *
     * @return a ref to an underlying object
     */
    EntityReference getRef();

    DateTime getTs();

    RecentUpdateType getType();
}

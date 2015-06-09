package org.agatom.springatom.data.types.enumeration;

import org.agatom.springatom.data.types.named.Named;

/**
 * <p>
 * <small>Class is a part of <b>SpringAtom2</b> and was created at 2014-09-29</small>
 * </p>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
public interface Enumeration<EE extends EnumerationEntry>
        extends Iterable<EE>, Named {
    Iterable<EE> getEntries();

    int size();
}

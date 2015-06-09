package org.agatom.springatom.data.types.preference;

import java.io.Serializable;

/**
 * <p>
 * <small>Class is a part of <b>SpringAtom2</b> and was created at 2014-11-26</small>
 * </p>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
public interface PreferenceValue<T extends Serializable> {
    T getValue();
}

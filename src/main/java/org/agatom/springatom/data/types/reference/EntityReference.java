package org.agatom.springatom.data.types.reference;

import java.io.Serializable;

/**
 * {@code EntityReference} describes an information implementing classes should return about referenced {@code entity}
 *
 * <p>
 * <small>Class is a part of <b>SpringAtom2</b> and was created at 2014-12-02</small>
 * </p>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
public interface EntityReference
        extends Serializable {
    Class<?> getRefClass();

    Long getRefId();
}

package org.agatom.springatom.data.types.blob;

import java.io.Serializable;

/**
 * <p>
 * <small>Class is a part of <b>SpringAtom2</b> and was created at 2014-10-17</small>
 * </p>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
public interface Blob {
    Class<?> getDataType();

    Serializable getData();

    long getDataLength();
}

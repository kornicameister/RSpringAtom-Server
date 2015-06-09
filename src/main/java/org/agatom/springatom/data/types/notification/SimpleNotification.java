package org.agatom.springatom.data.types.notification;

import org.joda.time.DateTime;

/**
 * <p>
 * <small>Class is a part of <b>SpringAtom2</b> and was created at 2014-10-16</small>
 * </p>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
public interface SimpleNotification {
    String getMessage();

    DateTime getSent();

    Boolean isRead();
}

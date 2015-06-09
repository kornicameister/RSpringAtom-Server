package org.agatom.springatom.data.types.notification;

/**
 * <p>
 * <small>Class is a part of <b>SpringAtom2</b> and was created at 2014-10-16</small>
 * </p>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
public interface NotificationAssociate {
    Long getAssociateId();

    Class<?> getAssociateClass();
}

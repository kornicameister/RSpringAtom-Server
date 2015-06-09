package org.agatom.springatom.data.model.notification;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * <p>
 * <small>Class is a part of <b>SpringAtom2</b> and was created at 2014-10-16</small>
 * </p>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
@Entity
@DiscriminatorValue(value = "2")
public class NNotificationTarget
        extends NNotificationAssociate {
    private static final long serialVersionUID = 8038641803417158285L;
}

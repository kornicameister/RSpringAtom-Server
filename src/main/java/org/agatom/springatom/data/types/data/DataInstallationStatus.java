package org.agatom.springatom.data.types.data;

import org.joda.time.DateTime;

/**
 * <p>
 * <small>Class is a part of <b>SpringAtom2</b> and was created at 2014-10-15</small>
 * </p>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
public interface DataInstallationStatus {
    Long getInstallationTimestampTs();

    Long getInstallationHash();

    Class<?> getInstallationHandler();

    String getInstallationPath();

    InstallStatus getInstallationStatus();

    DateTime getTimestamp();

    Throwable getFailureReason();

    static enum InstallStatus {
        SUCCESS,
        FAILED
    }
}

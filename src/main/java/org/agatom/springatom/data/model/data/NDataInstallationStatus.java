package org.agatom.springatom.data.model.data;

import org.agatom.springatom.data.model.NAbstractPersistable;
import org.agatom.springatom.data.types.data.DataInstallationStatus;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.springframework.util.SerializationUtils;

import javax.persistence.*;

/**
 * <p>
 * <small>Class is a part of <b>SpringAtom2</b> and was created at 2014-10-15</small>
 * </p>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
@Entity
@Table(
        indexes = {
                @Index(name = "dis_hashStatus", columnList = "i_hash,i_status")
        }
)
public class NDataInstallationStatus
        extends NAbstractPersistable
        implements DataInstallationStatus {
  public static final  String        ENUM_TYPE               = "org.hibernate.type.EnumType";
  private static final long          serialVersionUID        = -6023561051367671039L;
  private static final String        DATE_TIME_TYPE          = "org.jadira.usertype.dateandtime.joda.PersistentDateTime";
  @Column(name = "i_hash", nullable = false)
  private              Long          installationHash        = null;
  @Column(name = "i_handler", length = 400)
  private              Class<?>      installationHandler     = null;
  @Column(name = "i_path", length = 500, nullable = false)
  private              String        installationPath        = null;
  @Column(name = "i_status", length = 15, nullable = false)
  @Type(type = ENUM_TYPE)
  @Enumerated(value = EnumType.STRING)
  private              InstallStatus installationStatus      = null;
  @Type(type = DATE_TIME_TYPE)
  @Column(name = "i_ts", nullable = false)
  private              DateTime      installationTimestamp   = null;
  @Lob
  @Basic(fetch = FetchType.LAZY, optional = true)
  @Column(name = "i_failure", nullable = true)
  private              byte[]        installationFailure     = null;
  @Formula(value = "UNIX_TIMESTAMP(i_ts)")
  private              Long          installationTimestampTs = null;

  @Override
  public Long getInstallationTimestampTs() {
    return this.installationTimestampTs;
  }

  @Override
  public Long getInstallationHash() {
    return this.installationHash;
  }

  @Override
  public Class<?> getInstallationHandler() {
    return this.installationHandler;
  }

  @Override
  public String getInstallationPath() {
    return this.installationPath;
  }

  @Override
  public InstallStatus getInstallationStatus() {
    return this.installationStatus;
  }

  @Override
  public DateTime getTimestamp() {
    return this.installationTimestamp;
  }

  @Override
  public Throwable getFailureReason() {
    return (Throwable) SerializationUtils.deserialize(this.installationFailure);
  }

  public NDataInstallationStatus setInstallationStatus(final InstallStatus installationStatus) {
        this.installationStatus = installationStatus;
        return this;
    }

    public NDataInstallationStatus setInstallationPath(final String installationPath) {
        this.installationPath = installationPath;
        return this;
    }

    public NDataInstallationStatus setInstallationHandler(final Class<?> installationHandler) {
        this.installationHandler = installationHandler;
        return this;
    }

    public NDataInstallationStatus setInstallationHash(final Long installationHash) {
        this.installationHash = installationHash;
        return this;
    }

    public NDataInstallationStatus setInstallationFailure(final Throwable installationFailure) {
        this.installationFailure = SerializationUtils.serialize(installationFailure);
        return this;
    }

    public NDataInstallationStatus setInstallationTimestamp(final DateTime installationTimestamp) {
        this.installationTimestamp = installationTimestamp;
        return this;
    }


}

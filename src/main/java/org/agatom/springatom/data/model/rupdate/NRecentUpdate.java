package org.agatom.springatom.data.model.rupdate;

import org.agatom.springatom.data.model.NAbstractPersistable;
import org.agatom.springatom.data.model.reference.NEntityReference;
import org.agatom.springatom.data.types.rupdate.RecentUpdate;
import org.agatom.springatom.data.types.rupdate.RecentUpdateType;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * {@code NRecentUpdate} is an implementation of {@link org.agatom.springatom.data.types.rupdate.RecentUpdate}
 *
 * <p>
 * <small>Class is a part of <b>SpringAtom2</b> and was created at 2014-12-02</small>
 * </p>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
@Entity
@Table(
        indexes = {
                @Index(name = "ts_idx", columnList = "ru_ts"),
                @Index(name = "ref_idx", columnList = "ref_c,ref_id", unique = true)
        }
)
public class NRecentUpdate
        extends NAbstractPersistable
        implements RecentUpdate {
  private static final long             serialVersionUID = -170801423066805823L;
  private static final String           DATE_TIME_TYPE   = "org.jadira.usertype.dateandtime.joda.PersistentDateTime";
  @Embedded
  private              NEntityReference ref              = null;
  @NotNull
  @Type(type = DATE_TIME_TYPE)
  @Column(name = "ru_ts", nullable = false)
  private              DateTime         ts               = null;
  @NotNull
  @Enumerated(value = EnumType.STRING)
  @Column(name = "ru_type", nullable = false)
  private              RecentUpdateType type             = null;

  public NRecentUpdate() {
    this.ref = new NEntityReference();
  }

  @Override
  public NEntityReference getRef() {
    return this.ref;
  }

  public NRecentUpdate setRef(final NEntityReference ref) {
    this.ref = ref;
    return this;
  }

  @Override
  public DateTime getTs() {
    return this.ts;
  }

  public NRecentUpdate setTs(final DateTime ts) {
    this.ts = ts;
    return this;
  }

  @Override
  public RecentUpdateType getType() {
    return this.type;
  }

  public NRecentUpdate setType(final RecentUpdateType type) {
    this.type = type;
    return this;
  }


}

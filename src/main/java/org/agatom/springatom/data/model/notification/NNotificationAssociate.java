package org.agatom.springatom.data.model.notification;

import org.agatom.springatom.data.model.NAbstractPersistable;
import org.agatom.springatom.data.types.notification.NotificationAssociate;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * <p>
 * <small>Class is a part of <b>SpringAtom2</b> and was created at 2014-10-16</small>
 * </p>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
@Table
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = NNotificationAssociate.ASSOC_TYPE, discriminatorType = DiscriminatorType.INTEGER)
abstract class NNotificationAssociate
        extends NAbstractPersistable
        implements NotificationAssociate {
  protected static final String   ASSOC_TYPE       = "nna_t";
  private static final   long     serialVersionUID = -9073584339164182056L;
  @Min(value = 1)
  @NotNull
  @Column(name = "nna_id")
  private                Long     associateId      = null;
  @NotNull
  @Column(name = "nna_c", length = 400)
  private                Class<?> associateClass   = null;

  @Override
  public Long getAssociateId() {
    return associateId;
  }

  public NNotificationAssociate setAssociateId(final Long associateId) {
    this.associateId = associateId;
    return this;
  }

  @Override
  public Class<?> getAssociateClass() {
    return associateClass;
  }

  public NNotificationAssociate setAssociateClass(final Class<?> associateClass) {
    this.associateClass = associateClass;
    return this;
  }
}

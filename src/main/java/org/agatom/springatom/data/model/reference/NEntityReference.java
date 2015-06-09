package org.agatom.springatom.data.model.reference;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import org.agatom.springatom.data.types.reference.EntityReference;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * {@code NEntityReference} wraps information about entity that other one refers to.
 * <p>
 * <small>Class is a part of <b>SpringAtom2</b> and was created at 2014-12-02</small>
 * </p>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
@Embeddable
public class NEntityReference
  implements EntityReference {
  private static final long     serialVersionUID = 2106085283451189416L;
  @NotNull
  @Column(name = "ref_c", length = 400, nullable = false)
  private              Class<?> refClass         = null;
  @Min(value = 1)
  @NotNull
  @Column(name = "ref_id", nullable = false)
  private              Long     refId            = null;

  @Override
  public Class<?> getRefClass() {
    return this.refClass;
  }

  public NEntityReference setRefClass(final Class<?> refClass) {
    this.refClass = refClass;
    return this;
  }

  @Override
  public Long getRefId() {
    return this.refId;
  }

  public NEntityReference setRefId(final Long refId) {
    this.refId = refId;
    return this;
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(refClass, refId);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    NEntityReference that = (NEntityReference) o;

    return Objects.equal(this.refClass, that.refClass) &&
      Objects.equal(this.refId, that.refId);
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
      .add("refClass", refClass)
      .add("refId", refId)
      .toString();
  }
}

package org.agatom.springatom.data.model.link;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import org.springframework.data.domain.Persistable;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import java.io.Serializable;

/**
 * <p>
 * <small>Class is a part of <b>SpringAtom2</b> and was created at 2014-10-09</small>
 * </p>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
@Embeddable
class NObjectToObjectPK<A extends Persistable<Long>, B extends Persistable<Long>>
        implements Serializable {
  private static final long serialVersionUID = 4384077901776288042L;
  @ManyToOne(cascade = {CascadeType.REFRESH})
  private              A    roleA            = null;
  @ManyToOne(cascade = {CascadeType.REFRESH})
  private              B    roleB            = null;

  public A getRoleA() {
    return this.roleA;
  }

  public NObjectToObjectPK setRoleA(final A roleA) {
    this.roleA = roleA;
    return this;
  }

  public B getRoleB() {
    return this.roleB;
  }

  public NObjectToObjectPK setRoleB(final B roleB) {
    this.roleB = roleB;
    return this;
  }

  boolean isNew() {
    return (this.roleA == null || this.roleB == null) || (this.roleA.isNew() || this.roleB.isNew());
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(roleA, roleB);
  }

  @Override
  public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NObjectToObjectPK that = (NObjectToObjectPK) o;

        return Objects.equal(this.roleA, that.roleA) &&
                Objects.equal(this.roleB, that.roleB);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("roleA", roleA)
                .add("roleB", roleB)
                .toString();
    }
}

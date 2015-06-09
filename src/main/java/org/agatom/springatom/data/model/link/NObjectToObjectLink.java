package org.agatom.springatom.data.model.link;

import org.agatom.springatom.data.types.link.ObjectToObjectLink;
import org.springframework.data.domain.Persistable;

import javax.persistence.EmbeddedId;
import javax.persistence.MappedSuperclass;

/**
 * <p>
 * <small>Class is a part of <b>SpringAtom2</b> and was created at 2014-09-25</small>
 * </p>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
@MappedSuperclass
abstract public class NObjectToObjectLink<A extends Persistable<Long>, B extends Persistable<Long>>
        implements ObjectToObjectLink<A, B> {
    public static final  String                  ROLE_A_ASSOC     = "pk.roleA";
    public static final  String                  ROLE_B_ASSOC     = "pk.roleB";
    private static final long                    serialVersionUID = -1805174658485710892L;
    @EmbeddedId
    protected            NObjectToObjectPK<A, B> pk               = null;

    @Override
    public Long getId() {
        return null;
    }

    @Override
    public boolean isNew() {
        return this.requirePK().isNew();
    }

    private NObjectToObjectPK<A, B> requirePK() {
        if (this.pk == null) {
            this.pk = new NObjectToObjectPK<>();
        }
        return this.pk;
    }

    @Override
    @SuppressWarnings("unchecked")
    public A getRoleA() {
        return (A) this.getRole(Role.ROLE_A);
    }

    public NObjectToObjectLink<A, B> setRoleA(final A roleA) {
        this.requirePK().setRoleA(roleA);
        return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public B getRoleB() {
        return (B) this.getRole(Role.ROLE_B);
    }

    public NObjectToObjectLink<A, B> setRoleB(final B roleB) {
        this.requirePK().setRoleB(roleB);
        return this;
    }

    @Override
    public Object getRole(final Role role) {
        switch (role) {
            case ROLE_A:
                return this.requirePK().getRoleA();
            case ROLE_B:
                return this.requirePK().getRoleB();
        }
        throw new IllegalStateException(String.format("Role=%s is not supported", role));
    }
}

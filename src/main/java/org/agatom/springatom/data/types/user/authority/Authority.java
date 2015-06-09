package org.agatom.springatom.data.types.user.authority;

/**
 * <p>
 * <small>Class is a part of <b>SpringAtom2</b> and was created at 2014-09-29</small>
 * </p>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
public interface Authority {
    String getAuthority();

    AuthorityType getAuthorityType();

    static enum AuthorityType {
        ROLE,
        GROUP
    }
}

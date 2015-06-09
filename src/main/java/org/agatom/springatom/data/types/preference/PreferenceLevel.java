package org.agatom.springatom.data.types.preference;

/**
 * {@code PreferenceLevel} describes the level on which preference is valid,
 * therefore where its value is valid.
 * {@link org.agatom.springatom.data.types.preference.PreferenceLevel#APPLICATION} has precedence
 * over {@link org.agatom.springatom.data.types.preference.PreferenceLevel#USER}
 *
 * <p>
 * <small>Class is a part of <b>SpringAtom2</b> and was created at 2014-11-26</small>
 * </p>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
public enum PreferenceLevel {
    APPLICATION,
    USER
}

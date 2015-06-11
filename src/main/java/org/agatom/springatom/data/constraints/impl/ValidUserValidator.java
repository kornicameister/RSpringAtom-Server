/**************************************************************************************************
 * This file is part of [SpringAtom] Copyright [kornicameister@gmail.com][2014]                   *
 * *
 * [SpringAtom] is free software: you can redistribute it and/or modify                           *
 * it under the terms of the GNU General Public License as published by                           *
 * the Free Software Foundation, either version 3 of the License, or                              *
 * (at your option) any later version.                                                            *
 * *
 * [SpringAtom] is distributed in the hope that it will be useful,                                *
 * but WITHOUT ANY WARRANTY; without even the implied warranty of                                 *
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the                                  *
 * GNU General Public License for more details.                                                   *
 * *
 * You should have received a copy of the GNU General Public License                              *
 * along with [SpringAtom].  If not, see <http://www.gnu.org/licenses/gpl.html>.                  *
 **************************************************************************************************/

package org.agatom.springatom.data.constraints.impl;

import org.agatom.springatom.data.constraints.Password;
import org.agatom.springatom.data.constraints.UserName;
import org.agatom.springatom.data.constraints.ValidUser;
import org.agatom.springatom.data.types.user.User;
import org.hibernate.validator.constraints.Length;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraints.Pattern;

/**
 * <small>Class is a part of <b>SpringAtom</b> and was created at 05.04.14</small>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class ValidUserValidator
  implements ConstraintValidator<ValidUser, User> {
  private static final Logger LOGGER            = LoggerFactory.getLogger(ValidUserValidator.class);
  private              int    minUserNameLength = -1;
  private              int    maxUserNameLength = -1;
  private              int    minPasswordLength = -1;
  private              int    maxPasswordLength = -1;
  private              String passwordPattern   = null;

  /** {@inheritDoc} */
  @Override
  public void initialize(final ValidUser constraintAnnotation) {
    LOGGER.trace(String.format("%s initialized", ClassUtils.getShortName(ValidUserValidator.class)));

    // retrieve constraints from UserName
    this.minUserNameLength = UserName.class.getAnnotation(Length.class).min();
    this.maxUserNameLength = UserName.class.getAnnotation(Length.class).max();

    // retrieve constraint from Password

    this.minPasswordLength = Password.class.getAnnotation(Length.class).min();
    this.maxPasswordLength = Password.class.getAnnotation(Length.class).max();

    this.passwordPattern = Password.class.getAnnotation(Pattern.class).regexp();
  }

  /** {@inheritDoc} */
  @Override
  public boolean isValid(final User value, final ConstraintValidatorContext context) {
    if (this.minUserNameLength != -1 && this.maxUserNameLength != -1) {
      final String username = value.getUsername();
      boolean valid = this.validateUserName(username);
      valid = validatePassword(value, valid);
      return valid;
    }
    return true;
  }

  private boolean validateUserName(final String username) {
    boolean valid = StringUtils.hasText(username);
    if (valid) {
      final int length = username.length();
      valid = length >= this.minUserNameLength && length <= this.maxUserNameLength;
    }
    if (valid) {
      valid = !"boot".equalsIgnoreCase(username) || !"system".equalsIgnoreCase(username);
    }
    return valid;
  }

  private boolean validatePassword(final User value, boolean valid) {
    if (!valid) {
      return false;
    }
    final String password = value.getPassword();
    valid = StringUtils.hasText(password);
    if (valid) {
      final int length = password.length();
      valid = length >= this.minPasswordLength && length <= this.maxPasswordLength;
    }
    if (valid) {
      final java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(this.passwordPattern);
      valid = pattern.matcher(password).matches();
    }
    return valid;
  }

}

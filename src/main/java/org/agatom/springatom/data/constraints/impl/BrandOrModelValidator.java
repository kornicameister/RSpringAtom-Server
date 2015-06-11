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

import org.agatom.springatom.data.constraints.BrandOrModel;
import org.springframework.util.CollectionUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

import static org.agatom.springatom.common.RegexpPatterns.BIG_FIRST_LETTER_PATTERN;

/**
 * <small>Class is a part of <b>SpringAtom</b> and was created at 07.04.14</small>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class BrandOrModelValidator
  implements ConstraintValidator<BrandOrModel, String[]> {

  /** {@inheritDoc} */
  @Override
  public void initialize(final BrandOrModel constraintAnnotation) {
  }

  /** {@inheritDoc} */
  @Override
  public boolean isValid(final String[] value, final ConstraintValidatorContext context) {
    final List<?> list = CollectionUtils.arrayToList(value);
    for (Object str : list) {
      final boolean valid = this.isValid((String) str);
      if (!valid) {
        return false;
      }
    }
    return true;
  }

  private boolean isValid(final String value) {
    return value == null || java.util.regex.Pattern.compile(BIG_FIRST_LETTER_PATTERN).matcher(value).matches();
  }
}

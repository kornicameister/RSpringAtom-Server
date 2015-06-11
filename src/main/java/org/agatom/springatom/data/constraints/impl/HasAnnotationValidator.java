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

import org.agatom.springatom.data.constraints.HasAnnotation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.annotation.Annotation;

/**
 * {@code VinNumberValidator} validates given <b>vin number</b>.
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class HasAnnotationValidator
  implements ConstraintValidator<HasAnnotation, Class<?>> {
  private static final Logger                      LOGGER          = LoggerFactory.getLogger(HasAnnotationValidator.class);
  private              String                      message         = null;
  private              Class<? extends Annotation> annotationClass = null;

  /** {@inheritDoc} */
  @Override
  public void initialize(final HasAnnotation constraintAnnotation) {
    if (LOGGER.isTraceEnabled()) {
      this.message = constraintAnnotation.message();
      this.annotationClass = constraintAnnotation.annotation();
      LOGGER.trace(String.format("%s initialized", HasAnnotationValidator.class.getSimpleName()));
    }
  }

  /** {@inheritDoc} */
  @Override
  public boolean isValid(final Class<?> vinNumber, final ConstraintValidatorContext context) {
    final boolean annotationPresent = vinNumber.isAnnotationPresent(this.annotationClass);
    if (!annotationPresent) {
      context.buildConstraintViolationWithTemplate(this.message).addPropertyNode("annotation").addBeanNode().addConstraintViolation();
    }
    return annotationPresent;
  }
}

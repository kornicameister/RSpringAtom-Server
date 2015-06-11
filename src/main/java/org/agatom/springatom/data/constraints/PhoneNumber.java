/**************************************************************************************************
 * This file is part of [SpringAtom] Copyright [kornicameister@gmail.com][2013]                   *
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

package org.agatom.springatom.data.constraints;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;

/**
 * {@code LicencePlatePL} is the annotation used to validate any {@code String} againts being
 * validate value for licence plates in <b>Poland</b>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */

//business-logic
@NotNull
@NotEmpty
@Length(
  min = 4,
  max = 16,
  message = "Invalid phone number length"
)
@Pattern(
  regexp = "^(\\+\\(?\\d{1,2}\\)?\\s)?" +         //area code
    "((\\d{7,9})|" +                        //no whitespaces, no dashes
    "((\\d{3}(-|\\s)){2}\\d{3})|" +         //home number
    "(\\d{3}(-|\\s)\\d{2}(-|\\s)\\d{2}))$", //cell phone number
  flags = Pattern.Flag.CASE_INSENSITIVE,
  message = "Invalid phone number format"
)
//business-logic
@Target(value = {METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(value = RetentionPolicy.RUNTIME)
@Inherited
@Documented
@Constraint(validatedBy = {})
@ReportAsSingleViolation
public @interface PhoneNumber {
  String message() default "{org.agatom.springatom.server.service.support.constraints.PhoneNumber}";

  Class<?>[] groups() default {};

  @Deprecated Class<? extends Payload>[] payload() default {};
}

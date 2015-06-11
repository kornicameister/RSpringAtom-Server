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

import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
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
@Length(
  min = 3,
  max = 8,
  message = "Invalid licence plate length"
)
@Pattern(
  regexp = "^(\\w{2}\\s\\w{1,5})|(\\w{3}\\s\\w{1,4})$",
  flags = Pattern.Flag.CASE_INSENSITIVE,
  message = "Invalid licence plate format"
)
//business-logic
@Target(value = {METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(value = RetentionPolicy.RUNTIME)
@Inherited
@Documented
@ReportAsSingleViolation
public @interface LicencePlatePL {
  String message() default "{org.agatom.springatom.server.service.support.constraints.LicencePlatePL}";

  Class<?>[] groups() default {};

  @Deprecated Class<? extends Payload>[] payload() default {};
}

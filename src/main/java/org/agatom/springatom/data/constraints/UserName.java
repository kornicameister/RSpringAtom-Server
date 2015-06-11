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

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import javax.validation.constraints.NotNull;
import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;

/**
 * {@code UserName} is the part of {@code Constraints} that defines a set of conditions to be met to acknowledge
 * given <b>username</b> as valid.
 * <p/>
 * <p>
 * Conditions to be met are:
 * <ol>
 * <li>user name can not be null</li>
 * <li>user name length must be between <b>5</b> and <b>20</b> inclusively</li>
 * </ol>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@NotNull(message = "Username can not be null")
@Length(min = 5, max = 20, message = "Username length must be between 5 and 20")
//business-logic
@Target(value = {METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(value = RetentionPolicy.RUNTIME)
@Inherited
@Documented
@Constraint(validatedBy = {})
@ReportAsSingleViolation
public @interface UserName {
  String message() default "{org.agatom.springatom.server.service.support.constraints.UserName}";

  Class<?>[] groups() default {};

  @Deprecated Class<? extends Payload>[] payload() default {};
}

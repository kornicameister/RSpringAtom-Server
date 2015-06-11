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
import javax.validation.constraints.Pattern;
import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;

/**
 * {@code Password} is the part of {@code Constraints} that defines set of conditions designated to acknowledge
 * password as valid.
 * <p>
 * Valid password is defined as:
 * <ol>
 * <li>not null {@link String}</li>
 * <li>{@link String} which length is between <b>6</b> and <b>20</b> inclusively</li>
 * <li>{@link String} that matches following pattern=<i><pre>((?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]))</pre></i>
 * <ol>
 * <li>at least one digit=[0-9]</li>
 * <li>at least one uppercased letter=[A-Z]</li>
 * <li>at least one lowercased letter=[a-z]</li>
 * <li>at least one special character</li>
 * </ol>
 * </li>
 * </ol>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@NotNull
@Length(min = 6,
  max = 20,
  message = "Password length must be between [6,20]")
@Pattern(regexp = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})",
  message = "Password must contain at least one digit[0-9],uppercase letter[A-Z],lowercase letter[a-z] and special character")
//business-logic
@Target(value = {METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(value = RetentionPolicy.RUNTIME)
@Inherited
@Documented
@Constraint(validatedBy = {})
@ReportAsSingleViolation
public @interface Password {
  String message() default "{org.agatom.springatom.server.service.support.constraints.Password}";

  Class<?>[] groups() default {};

  @Deprecated Class<? extends Payload>[] payload() default {};
}

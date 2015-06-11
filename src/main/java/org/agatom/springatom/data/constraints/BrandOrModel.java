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

import org.agatom.springatom.data.constraints.impl.BrandOrModelValidator;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import javax.validation.constraints.NotNull;
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
//business-logic
@Target(value = {METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(value = RetentionPolicy.RUNTIME)
@Inherited
@Documented
@Constraint(validatedBy = {BrandOrModelValidator.class})
@ReportAsSingleViolation
public @interface BrandOrModel {
  String message() default "{org.agatom.springatom.server.service.support.constraints.BrandOrModel}";

  Class<?>[] groups() default {};

  @Deprecated Class<? extends Payload>[] payload() default {};
}

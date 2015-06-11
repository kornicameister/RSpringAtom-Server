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

package org.agatom.springatom.data.constraints;


import org.agatom.springatom.data.constraints.impl.HasAnnotationValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import javax.validation.constraints.NotNull;
import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;


@NotNull
@Target(value = {METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(value = RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = HasAnnotationValidator.class)
@ReportAsSingleViolation
public @interface HasAnnotation {
  String message() default "{org.agatom.springatom.server.service.support.constraints.IsReportableEntity}";

  Class<? extends Annotation> annotation();

  Class<?>[] groups() default {};

  @Deprecated Class<? extends Payload>[] payload() default {};
}

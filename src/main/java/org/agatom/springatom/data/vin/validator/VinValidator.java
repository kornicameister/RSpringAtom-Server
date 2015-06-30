package org.agatom.springatom.data.vin.validator;

import org.agatom.springatom.data.model.vin.VinNumber;
import org.springframework.util.ClassUtils;
import org.springframework.validation.Validator;

public interface VinValidator
  extends Validator {

  @Override
  default boolean supports(Class<?> clazz) {
    return ClassUtils.isAssignable(VinNumber.class, clazz);
  }
}

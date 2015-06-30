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

package org.agatom.springatom.data.vin.validator;

import com.google.common.base.Preconditions;
import org.agatom.springatom.data.model.vin.VinNumber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
class VinNumberValidator
  implements VinValidator {
  private static final Logger LOGGER     = LoggerFactory.getLogger(VinNumberValidator.class);
  private static final int    VIN_LENGTH = 17;
  private static final int[]  WEIGHTS    = {8, 7, 6, 5, 4, 3, 2, 10, 0, 9, 8, 7, 6, 5, 4, 3, 2};

  @Override
  public void validate(final Object target, final Errors errors) {
    Preconditions.checkNotNull(target, "target");
    final String vinNumber = ((VinNumber) target).getNumber();
    if (!this.validate(vinNumber)) {
      errors.reject("number", String.format("Invalid checksum of vin=%s", vinNumber));
    }
  }

  protected boolean validate(final String vinNumber) {
    int crc = 0;
    for (int i = 0; i < VIN_LENGTH; i++) {
      crc += WEIGHTS[i] * this.resolveCharacterValue(vinNumber.charAt(i));
    }
    final char checksum = (crc %= 11) == 10 ? 'X' : Character.forDigit(crc, 10);
    final boolean valid = this.isChecksumValid(vinNumber, checksum);
    if (valid && LOGGER.isDebugEnabled()) {
      LOGGER.debug(String.format("VinNumber=%s is valid [checksum=%s]", vinNumber, checksum));
    } else if (!valid) {
      LOGGER.warn(String.format("VinNumber=%s is invalid [checksum=%s]", vinNumber, checksum));
    }
    return valid;
  }

  private int resolveCharacterValue(final int c) {
    return c <= '9' ? c - '0' : ((c >= 'S' ? c + 1 : c) - 'A') % 9 + 1;
  }

  private boolean isChecksumValid(final String number, final char checksum) {
    return number.charAt(8) == checksum;
  }
}

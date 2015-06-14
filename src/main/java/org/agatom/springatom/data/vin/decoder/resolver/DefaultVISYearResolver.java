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

package org.agatom.springatom.data.vin.decoder.resolver;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.agatom.springatom.data.vin.exception.VinNumberServiceException;
import org.joda.time.DateTime;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Description;
import org.springframework.context.annotation.Role;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@Role(BeanDefinition.ROLE_SUPPORT)
@Description("Retrieves year of car production")
class DefaultVISYearResolver
  implements VISYearResolver {
  private static final Map<Character, Integer> CODES           = Maps.newHashMap();
  private static final int                     BEGIN_YEAR_YEAR = 1980;
  private static final int                     LOOP_STEP       = 30;

  static {
    final Character[] arr = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J', 'K', 'L', 'M', 'N', 'P', 'R', 'S', 'T', 'V', 'W', 'X', 'Y'};
    int step = 0;
    for (Character character : arr) {
      CODES.put(character, BEGIN_YEAR_YEAR + (step++));
    }
    for (int i = 1; i <= 9; i++) {
      CODES.put(String.valueOf(i).charAt(0), BEGIN_YEAR_YEAR + (step++));
    }
  }

  /** {@inheritDoc} */
  @Override
  public List<Integer> getYear(final String visNumber) throws VinNumberServiceException {
    final List<Integer> years = Lists.newArrayList();
    final char yearCode = visNumber.charAt(0);
    final int currentYear = DateTime.now().getYear();
    final int yearFromYearCode = CODES.get(yearCode);

    this.addToYears(years, currentYear, yearFromYearCode);

    return years;
  }

  private void addToYears(final List<Integer> years, final int currentYear, int year) {
    do {
      years.add(year);
      year += LOOP_STEP;
    } while (year <= currentYear);
  }


}

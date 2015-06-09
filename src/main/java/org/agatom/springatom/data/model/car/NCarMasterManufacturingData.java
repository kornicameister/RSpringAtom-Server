/**************************************************************************************************
 * This file is part of [SpringAtom] Copyright [kornicameister@gmail.com][2013]                   *
 *                                                                                                *
 * [SpringAtom] is free software: you can redistribute it and/or modify                           *
 * it under the terms of the GNU General Public License as published by                           *
 * the Free Software Foundation, either version 3 of the License, or                              *
 * (at your option) any later version.                                                            *
 *                                                                                                *
 * [SpringAtom] is distributed in the hope that it will be useful,                                *
 * but WITHOUT ANY WARRANTY; without even the implied warranty of                                 *
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the                                  *
 * GNU General Public License for more details.                                                   *
 *                                                                                                *
 * You should have received a copy of the GNU General Public License                              *
 * along with [SpringAtom].  If not, see <http://www.gnu.org/licenses/gpl.html>.                  *
 **************************************************************************************************/

package org.agatom.springatom.data.model.car;

import com.google.common.base.Objects;
import com.neovisionaries.i18n.CountryCode;
import org.agatom.springatom.data.types.car.ManufacturingData;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.util.StringUtils;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

import static org.agatom.springatom.common.RegexpPatterns.BIG_FIRST_LETTER_PATTERN;

@Embeddable
public class NCarMasterManufacturingData
        implements ManufacturingData, Serializable {
  private static final long   serialVersionUID = 545689870492641597L;
  @NotBlank
  @Pattern(regexp = BIG_FIRST_LETTER_PATTERN)
  @Column(nullable = false, length = 45, updatable = true, insertable = true)
  private              String brand            = null;
  @NotBlank
  @Pattern(regexp = BIG_FIRST_LETTER_PATTERN)
  @Column(nullable = false, length = 45, updatable = true, insertable = true)
  private              String model            = null;
  @Column(nullable = false, length = 100, updatable = true, insertable = true)
  private              String country = null;
  @Column(nullable = false, length = 100, updatable = true, insertable = true)
  private              String manufacturer    = null;

  public NCarMasterManufacturingData() {
    super();
  }

  public NCarMasterManufacturingData(final String brand, final String model) {
    this.brand = brand;
    this.model = model;
  }

  @Override
  public String getBrand() {
    return brand;
  }

  public NCarMasterManufacturingData setBrand(final String brand) {
    this.brand = brand;
    return this;
  }

  @Override
  public String getModel() {
    return model;
  }

  public NCarMasterManufacturingData setModel(final String model) {
    this.model = model;
    return this;
  }

  @Override
  public CountryCode getCountry() {
    if (StringUtils.hasText(this.country)) {
      return CountryCode.getByCode(this.country);
    }
    return null;
  }

  public NCarMasterManufacturingData setCountry(final CountryCode manufacturedIn) {
    this.country = manufacturedIn.getAlpha3();
    return this;
  }

  @Override
  public String getManufacturer() {
    return manufacturer;
  }

  public NCarMasterManufacturingData setManufacturer(final String manufacturer) {
    this.manufacturer = manufacturer;
    return this;
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(brand, model, country, manufacturer);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    NCarMasterManufacturingData that = (NCarMasterManufacturingData) o;

    return Objects.equal(this.brand, that.brand) &&
      Objects.equal(this.model, that.model) &&
      Objects.equal(this.country, that.country) &&
      Objects.equal(this.manufacturer, that.manufacturer);
  }
}

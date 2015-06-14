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

package org.agatom.springatom.data.model.vin;

import com.google.common.base.Objects;
import com.neovisionaries.i18n.CountryCode;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Embeddable
public class VinNumberData
  implements Serializable {
  private static final long          serialVersionUID = -6905013953824625804L;
  @Column(nullable = true)
  private              String        brand            = null;
  @Column(nullable = true)
  private              String        model            = null;
  @Column(nullable = true)
  private              String        manufacturedIn   = null;
  @Column(nullable = true)
  private              String        manufacturedBy   = null;
  @Column(nullable = true)
  private              String        type             = null;
  @Column(nullable = true)
  private              String        engineSeries     = null;
  @Column(nullable = true)
  private              String        engineType       = null;
  @Column(nullable = true)
  private              String        fuelType         = null;
  @ElementCollection
  @CollectionTable(name = "VinNumberDataYears", joinColumns = @JoinColumn(name = "vin_id"))
  @Column(name = "year", nullable = true)
  private              List<Integer> years            = null;

  public String getBrand() {
    return brand;
  }

  public VinNumberData setBrand(final String brand) {
    this.brand = brand;
    return this;
  }

  public String getModel() {
    return model;
  }

  public VinNumberData setModel(final String model) {
    this.model = model;
    return this;
  }

  public CountryCode getManufacturedIn() {
    if (StringUtils.hasText(this.manufacturedIn)) {
      return CountryCode.getByCode(this.manufacturedIn);
    }
    return null;
  }

  public VinNumberData setManufacturedIn(final CountryCode manufacturedIn) {
    this.manufacturedIn = manufacturedIn.getAlpha3();
    return this;
  }

  public String getManufacturedBy() {
    return manufacturedBy;
  }

  public VinNumberData setManufacturedBy(final String manufacturedBy) {
    this.manufacturedBy = manufacturedBy;
    return this;
  }

  public String getType() {
    return type;
  }

  public VinNumberData setType(final String type) {
    this.type = type;
    return this;
  }

  public String getEngineSeries() {
    return engineSeries;
  }

  public VinNumberData setEngineSeries(final String engineSeries) {
    this.engineSeries = engineSeries;
    return this;
  }

  public String getEngineType() {
    return engineType;
  }

  public VinNumberData setEngineType(final String engineType) {
    this.engineType = engineType;
    return this;
  }

  public String getFuelType() {
    return fuelType;
  }

  public VinNumberData setFuelType(final String fuelType) {
    this.fuelType = fuelType;
    return this;
  }

  public List<Integer> getYears() {
    return years;
  }

  public VinNumberData setYears(final List<Integer> years) {
    this.years = years;
    return this;
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(brand, model, manufacturedIn, manufacturedBy, type,
      engineSeries, engineType, fuelType, years);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    VinNumberData that = (VinNumberData) o;

    return Objects.equal(this.brand, that.brand) &&
      Objects.equal(this.model, that.model) &&
      Objects.equal(this.manufacturedIn, that.manufacturedIn) &&
      Objects.equal(this.manufacturedBy, that.manufacturedBy) &&
      Objects.equal(this.type, that.type) &&
      Objects.equal(this.engineSeries, that.engineSeries) &&
      Objects.equal(this.engineType, that.engineType) &&
      Objects.equal(this.fuelType, that.fuelType) &&
      Objects.equal(this.years, that.years);
  }
}

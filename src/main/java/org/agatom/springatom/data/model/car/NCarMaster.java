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

package org.agatom.springatom.data.model.car;

import com.google.common.collect.Sets;
import com.neovisionaries.i18n.CountryCode;
import org.agatom.springatom.data.model.NAbstractPersistable;
import org.agatom.springatom.data.types.car.CarMaster;
import org.hibernate.annotations.BatchSize;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

import static org.agatom.springatom.common.RegexpPatterns.BIG_FIRST_LETTER_PATTERN;

/**
 * <p>SCarMaster class.</p>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@Table
@Entity
public class NCarMaster
  extends NAbstractPersistable
  implements CarMaster<NCar> {
  private static final long serialVersionUID = -4932035593494629555L;
  @BatchSize(size = 10)
  @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH}, orphanRemoval = true, mappedBy = "carMaster")
  private              Set<NCar> children         = Sets.newHashSet();
  @NotBlank
  @Pattern(regexp = BIG_FIRST_LETTER_PATTERN)
  @Column(nullable = false, length = 45, updatable = true, insertable = true)
  private              String    brand            = null;
  @NotBlank
  @Pattern(regexp = BIG_FIRST_LETTER_PATTERN)
  @Column(nullable = false, length = 45, updatable = true, insertable = true)
  private              String    model            = null;
  @Column(nullable = false, length = 100, updatable = true, insertable = true)
  private              String    country          = null;
  @Column(nullable = false, length = 100, updatable = true, insertable = true)
  private              String    manufacturer     = null;

  public NCarMaster() {
    super();
  }

  public NCarMaster(final String brand, final String model) {
    this.brand = brand;
    this.model = model;
  }

  @Override
  public String getBrand() {
    return brand;
  }

  @Override
  public String getModel() {
    return model;
  }

  @Override
  public CountryCode getCountry() {
    if (StringUtils.hasText(this.country)) {
      return CountryCode.getByCode(this.country);
    }
    return null;
  }

  @Override
  public String getManufacturer() {
    return manufacturer;
  }

  @Override
  public Iterator<NCar> iterator() {
    return this.getChildren().iterator();
  }

  @Override
  public Set<NCar> getChildren() {
    if (this.children == null) {
      this.children = Sets.newHashSet();
    }
    return children;
  }

  public NCarMaster setChildren(final Collection<NCar> children) {
    this.children = Sets.newHashSet(children);
    return this;
  }

  public NCarMaster setManufacturer(final String manufacturer) {
    this.manufacturer = manufacturer;
    return this;
  }

  public NCarMaster setCountry(final CountryCode manufacturedIn) {
    this.country = manufacturedIn.getAlpha3();
    return this;
  }

  public NCarMaster setModel(final String model) {
    this.model = model;
    return this;
  }

  public NCarMaster setBrand(final String brand) {
    this.brand = brand;
    return this;
  }

}

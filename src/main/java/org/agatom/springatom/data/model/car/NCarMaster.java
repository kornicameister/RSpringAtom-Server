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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Sets;
import com.neovisionaries.i18n.CountryCode;
import org.agatom.springatom.data.model.NAbstractPersistable;
import org.agatom.springatom.data.types.car.CarMaster;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

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
  private static final long                        serialVersionUID  = -4932035593494629555L;
  @Embedded
  @JsonIgnore
  private              NCarMasterManufacturingData manufacturingData = null;
  @BatchSize(size = 10)
  @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH}, orphanRemoval = true, mappedBy = "carMaster")
  private              Set<NCar>                   children          = Sets.newHashSet();

  public NCarMaster() {
    super();
    this.manufacturingData = new NCarMasterManufacturingData();
  }

  @Override
  public String getBrand() {
    return this.manufacturingData.getBrand();
  }

  @Override
  public String getModel() {
    return this.manufacturingData.getModel();
  }

  @Override
  public CountryCode getCountry() {
    return this.manufacturingData.getCountry();
  }

  @Override
  public String getManufacturer() {
    return this.manufacturingData.getManufacturer();
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

  public NCarMasterManufacturingData setBrand(final String brand) {
    return this.manufacturingData.setBrand(brand);
  }

  public NCarMasterManufacturingData setModel(final String model) {
    return this.manufacturingData.setModel(model);
  }

  public NCarMasterManufacturingData setManufacturedIn(final CountryCode manufacturedIn) {
    return this.manufacturingData.setCountry(manufacturedIn);
  }

  public NCarMasterManufacturingData setManufacturer(final String manufacturedBy) {
    return this.manufacturingData.setManufacturer(manufacturedBy);
  }

  public NCarMasterManufacturingData getManufacturingData() {
    return manufacturingData;
  }

  public NCarMaster setManufacturingData(final NCarMasterManufacturingData manufacturingData) {
    this.manufacturingData = manufacturingData;
    return this;
  }
}

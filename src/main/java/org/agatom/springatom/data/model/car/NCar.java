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

import org.agatom.springatom.data.model.NAbstractAuditable;
import org.agatom.springatom.data.model.user.NUser;
import org.agatom.springatom.data.types.car.Car;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * <p>SCar class.</p>
 *
 * @author kornicamaister
 * @version 0.0.1
 * @since 0.0.1
 */
@Table
@Entity
public class NCar
        extends NAbstractAuditable
        implements Car<NUser, NCarMaster> {
  private static final long       serialVersionUID = -1473162805427581686L;
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(updatable = false)
  private              NCarMaster carMaster        = null;
  @NotNull
  @Audited(targetAuditMode = RelationTargetAuditMode.AUDITED)
  @ManyToOne(optional = false, fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.REFRESH})
  @JoinColumn(updatable = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  private              NUser      owner            = null;
  @Audited
  @NotNull
  @Column(nullable = false, length = 45, unique = true)
  @NaturalId(mutable = true)
  private              String     licencePlate     = null;
  @NotNull
  @Column(nullable = false, length = 17, unique = true)
  @NaturalId(mutable = true)
  private              String     vinNumber        = null;
  @NotEmpty
  @Column(nullable = false, length = 50)
  private              String     fuelType         = null;
  @NotNull
  @Min(value = 1900, message = "Year Production must be past 1900")
  @Column(updatable = true, unique = false, length = 50, nullable = true)
  private              Long       yearOfProduction = null;

  @Override
  public NCarMaster getCarMaster() {
    return carMaster;
  }

  public NCar setCarMaster(final NCarMaster carMaster) {
    this.carMaster = carMaster;
    return this;
  }

  @Override
  public String getLicencePlate() {
    return licencePlate;
  }

  public NCar setLicencePlate(final String registrationNumber) {
    this.licencePlate = registrationNumber;
    return this;
  }

  @Override
  public NUser getOwner() {
    return this.owner;
  }

  public NCar setOwner(final NUser client) {
    this.owner = client;
    return this;
  }

  @Override
  public String getBrand() {
    return this.carMaster.getBrand();
  }

  public NCar setBrand(final String brand) {
    this.carMaster.setBrand(brand);
    return this;
  }

  @Override
  public String getModel() {
    return this.carMaster.getModel();
  }

  public NCar setModel(final String model) {
    this.carMaster.setModel(model);
    return this;
  }

  @Override
  public String getFuelType() {
    return this.fuelType;
  }

  public NCar setFuelType(final String fuelType) {
    this.fuelType = fuelType;
    return this;
  }

  @Override
  public Long getYearOfProduction() {
    return this.yearOfProduction;
  }

  public NCar setYearOfProduction(final Long yearOfProduction) {
    this.yearOfProduction = yearOfProduction;
    return this;
  }

  @Override
  public String getVinNumber() {
    return vinNumber;
  }

  public NCar setVinNumber(final String vinNumber) {
    this.vinNumber = vinNumber;
    return this;
  }

}

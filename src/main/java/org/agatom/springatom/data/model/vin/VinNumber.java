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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Lists;
import org.agatom.springatom.data.model.NAbstractPersistable;
import org.agatom.springatom.data.model.car.NCar;
import org.agatom.springatom.data.vin.exception.VinNumberServiceException;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.util.Assert;

import javax.annotation.Nonnull;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.Optional;

/**
 * {@code VinNumber} holds reference to the {@code number}. Provides
 * basis splitting the number into meaningful parts of {@link VinNumberElement}
 * <small>Class is a part of <b>SpringAtom</b> and was created at 08.04.14</small>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@Table(
  uniqueConstraints = {
    @UniqueConstraint(name = "uniqueVin", columnNames = "number")
  }
)
@Entity
public class VinNumber
  extends NAbstractPersistable
  implements Comparable<VinNumber> {
  private static final long                         serialVersionUID = -2070896970742015138L;
  private transient    Collection<VinNumberElement> elements         = null;
  @NotNull
  @NotEmpty
  @Size(max = 17, min = 17, message = "VInNumber does not have correct length, should be 17 characters")
  private              String                       number           = null;
  @Embedded
  private              VinNumberData                data             = null;
  @NotNull(message = "Car must be set for vin number")
  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  @JoinColumn(name = "car")
  private              NCar                         car              = null;

  private VinNumber(final String number) throws VinNumberServiceException {
    this();
    try {
      Assert.hasText(number, "VinNumber must not be empty or null");
      Assert.isTrue(number.length() == 17, "VinNumber must have correct length");
    } catch (Exception exp) {
      throw new VinNumberServiceException("VinNumber is either null or has insufficient length 17", exp);
    }
    this.setNumber(number);
  }

  public VinNumber() {
    super();
  }

  private void splitVinNumber() throws VinNumberServiceException {
    try {
      this.retrieveElement(VinNumberElementType.VDS);
      this.retrieveElement(VinNumberElementType.VIS);
      this.retrieveElement(VinNumberElementType.WMI);
    } catch (Exception exp) {
      throw new VinNumberServiceException(String.format("VinNumber %s has invalid form", this.number), exp);
    }
  }

  private VinNumberElement retrieveElement(final VinNumberElementType type) {
    VinNumberElement element = null;

    if (this.elements == null) {
      this.elements = Lists.newArrayListWithCapacity(VinNumberElementType.values().length);
    } else {
      final Optional<VinNumberElement> first = this.elements.stream().filter(el -> el.getType().equals(type)).findFirst();
      element = first.isPresent() ? first.get() : null;
    }

    if (element == null) {
      switch (type) {
        case VDS:
          element = new VinNumberElement(type, this.number.substring(3, 9));
          break;
        case VIS:
          element = new VinNumberElement(type, this.number.substring(9, this.number.length()));
          break;
        case WMI:
          element = new VinNumberElement(type, this.number.substring(0, 3));
          break;
      }
      this.elements.add(element);
    }

    return element;
  }

  public static VinNumber newVinNumber(final String vinNumber) throws VinNumberServiceException {
    return new VinNumber(vinNumber);
  }

  public String getNumber() {
    return number;
  }

  public void setNumber(final String number) {
    this.number = number;
    this.splitVinNumber();
  }

  public VinNumberData getData() {
    return data;
  }

  public VinNumber setData(final VinNumberData data) {
    this.data = data;
    return this;
  }

  public NCar getCar() {
    return car;
  }

  public VinNumber setCar(final NCar car) {
    this.car = car;
    return this;
  }

  public String getWmi() {
    return this.getElement(VinNumberElementType.WMI).getValue();
  }

  @JsonIgnore
  public VinNumberElement getElement(final VinNumberElementType type) {
    return this.retrieveElement(type);
  }

  public String getVds() {
    return this.getElement(VinNumberElementType.VDS).getValue();
  }

  public String getVis() {
    return this.getElement(VinNumberElementType.VIS).getValue();
  }

  @Override
  public int compareTo(@Nonnull final VinNumber vinNumber) {
    return ComparisonChain.start()
      .compare(this.number, vinNumber.number)
      .result();
  }
}

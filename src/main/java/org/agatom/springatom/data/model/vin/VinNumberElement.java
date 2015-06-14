package org.agatom.springatom.data.model.vin;

import com.google.common.base.Objects;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

@Embeddable
public class VinNumberElement {
  public static final String               ENUM_TYPE = "org.hibernate.type.EnumType";
  @Type(type = ENUM_TYPE)
  @Column(length = 4, nullable = false)
  @NotNull
  @NotEmpty
  @Enumerated(value = EnumType.STRING)
  private             VinNumberElementType type      = null;
  @NotNull
  @NotEmpty
  private             String               value     = null;

  protected VinNumberElement(final VinNumberElementType type, final String value) {
    this();
    this.type = type;
    this.value = value;
  }

  public VinNumberElement() {
    super();
  }

  public VinNumberElementType getType() {
    return type;
  }

  public VinNumberElement setType(final VinNumberElementType type) {
    this.type = type;
    return this;
  }

  public String getValue() {
    return value;
  }

  public VinNumberElement setValue(final String value) {
    this.value = value;
    return this;
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(type, value);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    VinNumberElement that = (VinNumberElement) o;

    return Objects.equal(this.type, that.type) &&
      Objects.equal(this.value, that.value);
  }
}

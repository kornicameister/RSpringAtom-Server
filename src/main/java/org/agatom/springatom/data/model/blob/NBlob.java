package org.agatom.springatom.data.model.blob;

import org.agatom.springatom.data.model.NAbstractPersistable;
import org.agatom.springatom.data.types.blob.Blob;
import org.springframework.util.ClassUtils;
import org.springframework.util.SerializationUtils;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * <p>
 * <small>Class is a part of <b>SpringAtom2</b> and was created at 2014-10-17</small>
 * </p>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
@Entity
@Table(indexes = @Index(columnList = "c_type"))
public class NBlob
        extends NAbstractPersistable
        implements Blob {
  private static final long     serialVersionUID = -2618280735300088556L;
  @NotNull
  @Column(name = "c_type", length = 400, nullable = false)
  protected            Class<?> dataType         = null;
  @Lob
  @NotNull
  @Column(name = "c_data", nullable = false)
  private              byte[]   dataObject       = null;

  @Override
  public Class<?> getDataType() {
    return dataType;
  }

  @Override
  public Serializable getData() {
    return (Serializable) SerializationUtils.deserialize(this.dataObject);
  }

  @Override
  public long getDataLength() {
    return this.dataObject != null ? this.dataObject.length : -1;
  }

  public NBlob setData(final Serializable pageObject) {
    this.dataObject = SerializationUtils.serialize(pageObject);
    this.dataType = ClassUtils.getUserClass(pageObject);
    return this;
  }

}

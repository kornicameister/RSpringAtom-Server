package org.agatom.springatom.data.model.error;

import org.agatom.springatom.data.model.NAbstractPersistable;
import org.agatom.springatom.data.model.blob.NBlob;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * <p>
 * <small>Class is a part of <b>SpringAtom2</b> and was created at 2014-11-27</small>
 * </p>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
@Table
@Entity
public class NError
        extends NAbstractPersistable
        implements org.agatom.springatom.data.types.error.Error {
  private static final long      serialVersionUID = 5237724471333071330L;
  @NotNull
  @OneToOne(optional = false, cascade = {CascadeType.REMOVE, CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
  private              NBlob     errorObject      = null;
  private transient    Throwable error            = null;

  @Override
  public Throwable getError() {
    if (this.error == null) {
      this.error = (Throwable) this.errorObject.getData();
    }
    return this.error;
  }

  @Override
  public String getMessage() {
    if (this.getError() == null) {
      return null;
    }
    return error.getMessage();
  }

  @Override
  public StackTraceElement[] getStackTrace() {
    if (this.getError() == null) {
      return null;
    }
    return error.getStackTrace();
  }

  @Override
  public Throwable getCause() {
        if (this.getError() == null) {
            return null;
        }
        return error.getCause();
    }

    public NError setError(final Throwable error) {
        this.error = null;
        this.errorObject = new NBlob().setData(error);
        return this;
    }
}

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

package org.agatom.springatom.data.vin.exception;


import org.springframework.dao.DataAccessException;

/**
 * {@code VinServiceException} is a base class for all exceptions related to the vin decoding process
 * <small>Class is a part of <b>SpringAtom</b> and was created at 11.04.14</small>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
abstract public class VinServiceException
  extends DataAccessException {
  private static final long serialVersionUID = 4967227587313445039L;

  /**
   * <p>Constructor for VinServiceException.</p>
   *
   * @param cause a {@link Throwable} object.
   */
  public VinServiceException(final Throwable cause) {
    super("Error when accessing VIN data", cause);
  }

  /**
   * <p>Constructor for VinServiceException.</p>
   *
   * @param message a {@link String} object.
   * @param cause   a {@link Throwable} object.
   */
  public VinServiceException(final String message, final Throwable cause) {
    super(message, cause);
  }

  /**
   * <p>Constructor for VinServiceException.</p>
   *
   * @param message a {@link String} object.
   */
  public VinServiceException(final String message) {
    super(message);
  }
}

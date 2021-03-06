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

/**
 * {@code VinDecodingException} is thrown if any error comes up when decoding the vin number
 * <p/>
 * <small>Class is a part of <b>SpringAtom</b> and was created at 11.04.14</small>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class VinDecodingException
  extends VinServiceException {
  private static final long serialVersionUID = 6260529301188575535L;

  /**
   * <p>Constructor for VinDecodingException.</p>
   *
   * @param message a {@link String} object.
   */
  public VinDecodingException(final String message) {
    super(message);
  }

  /**
   * <p>Constructor for VinDecodingException.</p>
   *
   * @param cause a {@link Throwable} object.
   */
  public VinDecodingException(final Throwable cause) {
    super(cause);
  }

  /**
   * <p>Constructor for VinDecodingException.</p>
   *
   * @param message a {@link String} object.
   * @param cause   a {@link Throwable} object.
   */
  public VinDecodingException(final String message, final Throwable cause) {
    super(message, cause);
  }
}

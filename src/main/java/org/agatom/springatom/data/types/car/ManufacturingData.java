/**************************************************************************************************
 * This file is part of [SpringAtom] Copyright [kornicameister@gmail.com][2014]                   *
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

package org.agatom.springatom.data.types.car;

import com.neovisionaries.i18n.CountryCode;

/**
 * {@code ManufacturingData} carries information about {@link #getModel()} and {@link #getBrand()}
 * of single vehicle
 * <p/>
 * <small>Class is a part of <b>SpringAtom</b> and was created at 08.04.14</small>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public interface ManufacturingData {
    /**
     * <p>getBrand.</p>
     *
     * @return a {@link String} object.
     */
    String getBrand();

    /**
     * <p>getModel.</p>
     *
     * @return a {@link String} object.
     */
    String getModel();

    /**
     * <p>getManufacturedIn.</p>
     *
     * @return a {@link com.neovisionaries.i18n.CountryCode} object.
     */
    CountryCode getCountry();

    /**
     * <p>getManufacturedBy.</p>
     *
     * @return a {@link String} object.
     */
    String getManufacturer();
}

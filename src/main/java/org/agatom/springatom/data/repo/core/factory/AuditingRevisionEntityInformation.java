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

package org.agatom.springatom.data.repo.core.factory;

import org.agatom.springatom.data.model.revision.AuditedRevisionEntity;
import org.springframework.data.repository.history.support.RevisionEntityInformation;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
class AuditingRevisionEntityInformation
        implements RevisionEntityInformation {
    /**
     * <p>Constructor for AuditingRevisionEntityInformation.</p>
     */
    public AuditingRevisionEntityInformation() {
        super();
    }

    /** {@inheritDoc} */
    @Override
    public Class<?> getRevisionNumberType() {
        return Integer.class;
    }

    /** {@inheritDoc} */
    @Override
    public boolean isDefaultRevisionEntity() {
        return false;
    }

    /** {@inheritDoc} */
    @Override
    public Class<?> getRevisionEntityClass() {
        return AuditedRevisionEntity.class;
    }
}

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

package org.agatom.springatom.data.repo.repositories.car;

import com.neovisionaries.i18n.CountryCode;
import org.agatom.springatom.data.model.car.NCarMaster;
import org.agatom.springatom.data.repo.repositories.NRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.Description;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

@RepositoryRestResource(
        itemResourceRel = NCarMasterRepository.ITEM_REL,
        itemResourceDescription = @Description("Get single car master"),
        collectionResourceRel = NCarMasterRepository.COLLECTION_REL,
        collectionResourceDescription = @Description("Get collection of car masters"),
        path = NCarMasterRepository.REST_REPO_PATH
)
public interface NCarMasterRepository
        extends NRepository<NCarMaster> {
    String ITEM_REL       = "car_master";
    String COLLECTION_REL = "car_masters";
    String REST_REPO_PATH = ITEM_REL;

    @RestResource(path = "brand_and_model")
    NCarMaster findByBrandAndModelAllIgnoreCase(
      @Param("brand") final String brand,
      @Param("model") final String model
    );

    @RestResource(path = "brand")
    Page<NCarMaster> findByBrandIgnoreCase(
      @Param(value = "brand") final String brand,
      final Pageable pageable
    );

    @RestResource(path = "model")
    Page<NCarMaster> findByModelIgnoreCase(
      @Param(value = "model") final String model,
      final Pageable pageable
    );

    @RestResource(path = "manufactured_in")
    Page<NCarMaster> findByCountry(
      @Param(value = "cc") final CountryCode cc,
      final Pageable pageable
    );

    @RestResource(path = "manufactured_by")
    Page<NCarMaster> findByManufacturerStartingWithIgnoreCase(
      @Param(value = "manufacturer") final String manufacturer,
      final Pageable pageable
    );

}

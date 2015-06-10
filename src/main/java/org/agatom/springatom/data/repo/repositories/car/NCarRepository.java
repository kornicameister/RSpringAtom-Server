package org.agatom.springatom.data.repo.repositories.car;

import org.agatom.springatom.data.model.car.NCar;
import org.agatom.springatom.data.repo.repositories.NRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.Description;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

@RepositoryRestResource(
  itemResourceRel = NCarRepository.ITEM_REL,
  itemResourceDescription = @Description("Get single car"),
  collectionResourceRel = NCarRepository.COLLECTION_REL,
  collectionResourceDescription = @Description("Get collection of cars"),
  path = NCarRepository.REST_REPO_PATH
)
public interface NCarRepository
  extends NRepository<NCar> {
  String ITEM_REL       = "car";
  String COLLECTION_REL = "car";
  String REST_REPO_PATH = ITEM_REL;

  /**
   * <p>findByLicencePlate.</p>
   *
   * @param licencePlate a {@link String} object.
   *
   * @return a {@link org.agatom.springatom.data.model.car.NCar} object.
   */
  @RestResource(path = "licence_plate")
  NCar findByLicencePlateIgnoreCase(
    @Param(value = "lp") final String licencePlate
  );

  /**
   * <p>findByLicencePlateStartingWith.</p>
   *
   * @param licencePlate a {@link String} object.
   * @param pageable     a {@link org.springframework.data.domain.Pageable} object.
   *
   * @return a {@link org.springframework.data.domain.Page} object.
   */
  @RestResource(path = "licence_plate/starting")
  Page<NCar> findByLicencePlateStartingWithIgnoreCase(
    @Param(value = "lp") final String licencePlate,
    final Pageable pageable
  );

  /**
   * <p>findByLicencePlateEndingWith.</p>
   *
   * @param licencePlate a {@link String} object.
   * @param pageable     a {@link org.springframework.data.domain.Pageable} object.
   *
   * @return a {@link org.springframework.data.domain.Page} object.
   */
  @RestResource(path = "licence_plate/ending")
  Page<NCar> findByLicencePlateEndingWithIgnoreCase(
    @Param(value = "lp") final String licencePlate,
    final Pageable pageable
  );

  /**
   * <p>findByLicencePlateContaining.</p>
   *
   * @param licencePlate a {@link String} object.
   * @param pageable     a {@link org.springframework.data.domain.Pageable} object.
   *
   * @return a {@link org.springframework.data.domain.Page} object.
   */
  @RestResource(path = "licence_plate/containing")
  Page<NCar> findByLicencePlateContainingIgnoreCase(
    @Param(value = "lp") final String licencePlate,
    final Pageable pageable
  );

  /**
   * <p>findByCarMasterManufacturingDataBrand.</p>
   *
   * @param brand    a {@link String} object.
   * @param pageable a {@link org.springframework.data.domain.Pageable} object.
   *
   * @return a {@link org.springframework.data.domain.Page} object.
   */
  @RestResource(path = "brand")
  Page<NCar> findByCarMasterManufacturingDataBrandStartingWithIgnoreCase(
    @Param(value = "brand") final String brand,
    final Pageable pageable
  );

  /**
   * <p>findByCarMasterManufacturingDataModel.</p>
   *
   * @param model    a {@link String} object.
   * @param pageable a {@link org.springframework.data.domain.Pageable} object.
   *
   * @return a {@link org.springframework.data.domain.Page} object.
   */
  @RestResource(path = "model")
  Page<NCar> findByCarMasterManufacturingDataModelStartingWithIgnoreCase(
    @Param(value = "model") final String model,
    final Pageable pageable
  );

  /**
   * <p>findByCarMasterManufacturingDataBrandAndCarMasterManufacturingDataModel.</p>
   *
   * @param brand    a {@link String} object.
   * @param model    a {@link String} object.
   * @param pageable a {@link org.springframework.data.domain.Pageable} object.
   *
   * @return a {@link org.springframework.data.domain.Page} object.
   */
  @RestResource(path = "brand_and_model")
  Page<NCar> findByCarMasterManufacturingDataBrandAndCarMasterManufacturingDataModelAllIgnoreCase(
    @Param(value = "brand") final String brand,
    @Param(value = "model") final String model,
    final Pageable pageable
  );

  /**
   * <p>findByOwnerPersonLastNameContaining.</p>
   *
   * @param owner    a {@link String} object.
   * @param pageable a {@link org.springframework.data.domain.Pageable} object.
   *
   * @return a {@link org.springframework.data.domain.Page} object.
   */
  @RestResource(path = "owner/last_name")
  Page<NCar> findByOwnerCredentialsUsernameContainingIgnoreCase(
    @Param(value = "owner") final String owner,
    final Pageable pageable
  );

  /**
   * <p>findByVinNumber.</p>
   *
   * @param vinNumber a {@link String} object.
   *
   * @return a {@link org.agatom.springatom.data.model.car.NCar} object.
   */
  @RestResource(path = "vin_number")
  NCar findByVinNumberIgnoreCase(
    @Param(value = "vin") final String vinNumber
  );
}

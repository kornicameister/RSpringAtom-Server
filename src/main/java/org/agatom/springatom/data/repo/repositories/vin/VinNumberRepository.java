package org.agatom.springatom.data.repo.repositories.vin;

import org.agatom.springatom.data.model.car.NCar;
import org.agatom.springatom.data.model.vin.VinNumber;
import org.agatom.springatom.data.repo.repositories.NRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.Description;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource(
  itemResourceRel = VinNumberRepository.ITEM_REL,
  itemResourceDescription = @Description("VinNumber"),
  collectionResourceRel = VinNumberRepository.COLLECTION_REL,
  collectionResourceDescription = @Description("Collection of vin numbers"),
  path = VinNumberRepository.REST_REPO_PATH
)
public interface VinNumberRepository
  extends NRepository<VinNumber> {
  String ITEM_REL       = "vin";
  String COLLECTION_REL = "vins";
  String REST_REPO_PATH = ITEM_REL;

  @RestResource(path = "car")
  Optional<VinNumber> findByCar(@Param("car") final NCar car);

  @Override
  @RestResource(exported = false)
  <S extends VinNumber> List<S> save(Iterable<S> entities);

  @Override
  @RestResource(exported = false)
  <S extends VinNumber> S saveAndFlush(S entity);

  @Override
  @RestResource(exported = false)
  <S extends VinNumber> S save(S entity);

  @Override
  @RestResource(exported = false)
  void delete(Long id);

  @Override
  @RestResource(exported = false)
  void delete(VinNumber entity);

  @Override
  @RestResource(exported = false)
  void delete(Iterable<? extends VinNumber> entities);

  @Override
  @RestResource(exported = false)
  void deleteAll();

}

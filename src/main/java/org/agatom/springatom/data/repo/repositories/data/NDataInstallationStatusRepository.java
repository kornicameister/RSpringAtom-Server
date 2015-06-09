package org.agatom.springatom.data.repo.repositories.data;

import org.agatom.springatom.data.model.data.NDataInstallationStatus;
import org.agatom.springatom.data.repo.repositories.NRepository;
import org.agatom.springatom.data.types.data.DataInstallationStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.Description;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.Collection;

@RepositoryRestResource(
  itemResourceRel = NDataInstallationStatusRepository.ITEM_REL,
  itemResourceDescription = @Description("Get single appointment"),
  collectionResourceRel = NDataInstallationStatusRepository.COLLECTION_REL,
  collectionResourceDescription = @Description("Get collection of appointments"),
  path = NDataInstallationStatusRepository.REST_REPO_PATH
)
public interface NDataInstallationStatusRepository
  extends NRepository<NDataInstallationStatus> {
  String ITEM_REL       = "data_installation_status";
  String COLLECTION_REL = "data_installation_statuses";
  String REST_REPO_PATH = ITEM_REL;


  @RestResource(path = "status")
  Page<NDataInstallationStatus> getByInstallationStatus(@Param("status") final DataInstallationStatus.InstallStatus status, final Pageable pageable);

  @RestResource(exported = false)
  Collection<NDataInstallationStatus> getByInstallationStatus(final DataInstallationStatus.InstallStatus status);

}

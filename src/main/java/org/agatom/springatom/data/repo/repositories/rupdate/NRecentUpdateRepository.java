package org.agatom.springatom.data.repo.repositories.rupdate;

import org.agatom.springatom.data.model.rupdate.NRecentUpdate;
import org.agatom.springatom.data.repo.repositories.NRepository;
import org.springframework.data.rest.core.annotation.Description;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@RepositoryRestResource(
  itemResourceRel = NRecentUpdateRepository.ITEM_REL,
  itemResourceDescription = @Description("Get single recent update"),
  collectionResourceRel = NRecentUpdateRepository.COLLECTION_REL,
  collectionResourceDescription = @Description("Get collection of recent updates"),
  path = NRecentUpdateRepository.REST_REPO_PATH
)
public interface NRecentUpdateRepository
        extends NRepository<NRecentUpdate> {
  String ITEM_REL       = "recent_update";
  String COLLECTION_REL = "recent_updates";
  String REST_REPO_PATH = ITEM_REL;
}

package org.agatom.springatom.data.repo.repositories.person;

import org.agatom.springatom.data.model.person.NPerson;
import org.agatom.springatom.data.repo.repositories.NRepository;
import org.springframework.data.rest.core.annotation.Description;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(
  itemResourceRel = NPersonRepository.ITEM_REL,
  itemResourceDescription = @Description("Get single person"),
  collectionResourceRel = NPersonRepository.COLLECTION_REL,
  collectionResourceDescription = @Description("Get collection of persons"),
  path = NPersonRepository.REST_REPO_PATH
)
public interface NPersonRepository
        extends NRepository<NPerson> {
  String ITEM_REL       = "person";
  String COLLECTION_REL = "persons";
  String REST_REPO_PATH = ITEM_REL;
}

package org.agatom.springatom.data.repo.repositories.enumeration;

import org.agatom.springatom.data.model.enumeration.NEnumeration;
import org.agatom.springatom.data.repo.repositories.NRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.Description;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.Optional;


@RepositoryRestResource(
        itemResourceRel = NEnumerationRepository.ITEM_REL,
        itemResourceDescription = @Description("Get single enumeration"),
        collectionResourceRel = NEnumerationRepository.COLLECTION_REL,
        collectionResourceDescription = @Description("Get collection of enumerations"),
        path = NEnumerationRepository.REST_REPO_PATH
)
public interface NEnumerationRepository
        extends NRepository<NEnumeration> {
    String ITEM_REL = "enumeration";
    String COLLECTION_REL = "enumerations";
    String REST_REPO_PATH = ITEM_REL;

    @RestResource(path = "name")
    Optional<NEnumeration> findByName(@Param("name") final String name);

}

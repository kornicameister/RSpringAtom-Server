package org.agatom.springatom.data.repo.repositories.authority;

import org.agatom.springatom.data.model.user.authority.NRole;
import org.agatom.springatom.data.repo.repositories.NRepository;
import org.springframework.data.rest.core.annotation.Description;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(
        itemResourceRel = NRoleRepository.ITEM_REL,
        itemResourceDescription = @Description("Get single role"),
        collectionResourceRel = NRoleRepository.COLLECTION_REL,
        collectionResourceDescription = @Description("Get collection of roles"),
        path = NRoleRepository.REST_REPO_PATH
)
public interface NRoleRepository
        extends NRepository<NRole> {
    String ITEM_REL = "role";
    String COLLECTION_REL = "roles";
    String REST_REPO_PATH = ITEM_REL;

    NRole findByAuthority(final String authority);

}

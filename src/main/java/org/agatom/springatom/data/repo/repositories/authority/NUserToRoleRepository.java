package org.agatom.springatom.data.repo.repositories.authority;

import org.agatom.springatom.data.model.user.NUser;
import org.agatom.springatom.data.model.user.authority.NRole;
import org.agatom.springatom.data.model.user.authority.UserToRoleLink;
import org.agatom.springatom.data.repo.repositories.NRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.Description;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.Collection;

@RepositoryRestResource(
        itemResourceRel = NUserToRoleRepository.ITEM_REL,
        itemResourceDescription = @Description("Get single user to role association"),
        collectionResourceRel = NUserToRoleRepository.COLLECTION_REL,
        collectionResourceDescription = @Description("Get collection of user to role associations"),
        path = NUserToRoleRepository.REST_REPO_PATH
)
public interface NUserToRoleRepository
        extends NRepository<UserToRoleLink> {
    String ITEM_REL = "user_role";
    String COLLECTION_REL = "user_roles";
    String REST_REPO_PATH = ITEM_REL;

    @RestResource(path = "user")
    @Query(name = "byUser", value = "select SM from UserToRoleLink as SM where SM.pk.roleA = :user")
    Collection<UserToRoleLink> findByUser(@Param("user") final NUser user);

    @RestResource(path = "role")
    @Query(name = "byRole", value = "select SM from UserToRoleLink as SM where SM.pk.roleB = :role")
    Collection<UserToRoleLink> findByRole(@Param("role") final NRole role);

}

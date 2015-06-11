package org.agatom.springatom.data.service.services;

import org.agatom.springatom.data.constraints.Password;
import org.agatom.springatom.data.constraints.UserName;
import org.agatom.springatom.data.constraints.ValidUser;
import org.agatom.springatom.data.model.person.NPerson;
import org.agatom.springatom.data.model.user.NUser;
import org.agatom.springatom.data.service.core.NDataLoaderService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;
import java.util.Collection;

@Validated
public interface NUserService
  extends NDataLoaderService<Collection<NUser>> {

  /**
   * {@code org.agatom.springatom.server.service.domain.SUserService#registerNewUser(String, String, long)} registers new user
   * in the system.
   *
   * @param userName username (length=[5,20]
   * @param password password (can not be empty,null, its length must be between 6,20 and must be in special format)
   * @param person   person instance
   *
   * @return an instance of the {@link org.agatom.springatom.data.types.user.User}
   *
   * @throws java.lang.Exception if any
   */
  @NotNull
  NUser registerNewUser(@UserName final String userName, @Password final String password, @Nullable final NPerson person) throws Exception;

  /**
   * Performs direct registration from instance of {@link org.agatom.springatom.data.types.user.User}.
   * {@code user} will be validated against {@link org.agatom.springatom.data.constraints.ValidUser}
   *
   * @param user user to be registered
   *
   * @return registered user
   *
   * @throws java.lang.Exception if any
   */
  @NotNull
  NUser registerNewUser(@ValidUser final NUser user) throws Exception;

  /**
   * @param user   user to register
   * @param person person to associate user with
   *
   * @return registered user
   *
   * @throws Exception if any
   * @see #registerNewUser(NUser)
   */
  @NotNull
  NUser registerNewUser(@ValidUser final NUser user, @Nullable final NPerson person) throws Exception;

  /**
   * @param user        user to register
   * @param person      person to associate user with
   * @param authorities roles
   *
   * @return registered user
   *
   * @throws Exception iff any
   */
  @NotNull
  <GA extends GrantedAuthority> NUser registerNewUser(@ValidUser final NUser user, final NPerson person, final Collection<GA> authorities) throws Exception;

}

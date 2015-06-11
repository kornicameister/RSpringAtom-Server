package org.agatom.springatom.data.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.agatom.springatom.data.model.user.NUser;
import org.agatom.springatom.data.model.user.authority.NRole;
import org.agatom.springatom.data.model.user.authority.UserToRoleLink;
import org.agatom.springatom.data.repo.repositories.authority.NUserToRoleRepository;
import org.agatom.springatom.data.repo.repositories.user.NUserRepository;
import org.agatom.springatom.data.service.services.NUserDetailsService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.ClassUtils;

import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Optional;

@Service
public class UserDetailsService
  implements NUserDetailsService {
  private static final Logger                LOGGER               = LogManager.getLogger(UserDetailsService.class);
  @Autowired
  private              NUserRepository       userRepository       = null;
  @Autowired
  private              NUserToRoleRepository userToRoleRepository = null;

  @Override
  public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
    final Optional<NUser> optionalUser = this.userRepository.findByCredentialsUsername(username);
    if (!optionalUser.isPresent()) {
      throw new UsernameNotFoundException(String.format("User with userName=%s not found", username));
    }
    return this.getUserDetails(optionalUser.get());
  }

  private UserDetails getUserDetails(final NUser user) {
    final Collection<GrantedAuthority> roles = Sets.newHashSet();

    this.getNonNullRoles(user).forEach(input -> {
      final NRole role = input.getRole();
      final String authority = role.getAuthority();
      if (LOGGER.isTraceEnabled()) {
        LOGGER.trace("Transforming {} to {} with authority = {}",
          ClassUtils.getShortName(NRole.class),
          ClassUtils.getShortName(SimpleGrantedAuthority.class),
          authority
        );
      }
      roles.add(new SimpleGrantedAuthority(authority));
    });

    return new User(
      user.getUsername(),
      user.getPassword(),
      user.isEnabled(),
      user.isAccountNonExpired(),
      user.isCredentialsNonExpired(),
      user.isAccountNonLocked(),
      roles
    );
  }

  private Collection<UserToRoleLink> getNonNullRoles(final NUser user) {
    Collection<UserToRoleLink> roles = this.userToRoleRepository.findByUser(user);
    if (roles == null) {
      roles = Lists.newArrayList();
    }
    return roles;
  }

  @Override
  public UserDetails loadUserByEmail(@NotNull final String email) {
    final Optional<NUser> optional = this.userRepository.findByCredentialsEmail(email);
    if (!optional.isPresent()) {
      throw new UsernameNotFoundException(String.format("User with email=%s not found", email));
    }
    return this.getUserDetails(optional.get());
  }
}

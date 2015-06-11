package org.agatom.springatom.data.service.services;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.validation.constraints.NotNull;

public interface NUserDetailsService
  extends UserDetailsService {

  UserDetails loadUserByEmail(@NotNull String email);

}

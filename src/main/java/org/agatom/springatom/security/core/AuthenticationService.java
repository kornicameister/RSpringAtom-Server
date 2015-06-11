package org.agatom.springatom.security.core;

import org.agatom.springatom.security.token.TokenInfo;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

public interface AuthenticationService {

  TokenInfo generateToken(final Authentication authentication);

  void checkToken(final String token);

}


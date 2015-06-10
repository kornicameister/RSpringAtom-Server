package org.agatom.springatom.security.core;

import org.agatom.springatom.security.token.TokenInfo;
import org.springframework.security.core.userdetails.UserDetails;

public interface AuthenticationService {

  TokenInfo authenticate(final String login, final String password);

  void checkToken(final String token);

  void logout(final String token);

  UserDetails currentUser();

}


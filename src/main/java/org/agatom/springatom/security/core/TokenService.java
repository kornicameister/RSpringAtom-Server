package org.agatom.springatom.security.core;


import org.agatom.springatom.security.token.TokenInfo;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;

public interface TokenService {

  TokenInfo createNewToken(final UserDetails userDetails) throws Exception;

  void removeUserDetails(final UserDetails userDetails);

  UserDetails removeToken(final String token);

  UserDetails getUserDetails(final String token);

  Iterable<TokenInfo> getUserTokens(final UserDetails userDetails);

  Map<String, UserDetails> getValidUsers();

  class InvalidTokenException
    extends AuthenticationException {
    private static final long serialVersionUID = -6578462071390347376L;

    public InvalidTokenException(final String token, final Throwable t) {
      super(token, t);
    }

    public InvalidTokenException(final String token) {
      super(String.format("Token %s is invalid", token));
    }
  }

}

package org.agatom.springatom.security.core.impl;

import org.agatom.springatom.security.core.AuthenticationService;
import org.agatom.springatom.security.core.TokenService;
import org.agatom.springatom.security.core.TokenService.InvalidTokenException;
import org.agatom.springatom.security.core.TokenService.TokenExpiredException;
import org.agatom.springatom.security.token.TokenInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.MarkerManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;


@Service
class AuthenticationServiceImpl
    implements AuthenticationService {
  private static final Logger LOGGER = LogManager.getLogger(AuthenticationServiceImpl.class);
  @Autowired
  private TokenService tokenManager;

  @Override
  public TokenInfo generateToken(final Authentication authentication) {
    LOGGER.entry(authentication);
    TokenInfo info = null;
    try {
      if (authentication.getPrincipal() != null) {
        final UserDetails userContext = (UserDetails) authentication.getPrincipal();
        info = this.tokenManager.createNewToken(userContext);
      }
    } catch (Exception exp) {
      LOGGER.error(MarkerManager.getMarker("Auth"), String.format("Failed to generateToken %s", authentication), exp);
    }
    LOGGER.exit(info);
    return info;
  }

  @Override
  public void checkToken(final String token) {
    final UserDetails userDetails = this.tokenManager.getUserDetails(token);
    if (userDetails == null) {
      throw new InvalidTokenException(token);
    } else if (this.tokenManager.isTokenExpired(token)) {

      this.tokenManager.removeToken(token);
      SecurityContextHolder.clearContext();

      throw new TokenExpiredException(token);
    }

    SecurityContextHolder
        .getContext()
        .setAuthentication(
            new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities()
            )
        );
  }

}

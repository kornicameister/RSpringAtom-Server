package org.agatom.springatom.mvc.security.core.impl;

import org.agatom.springatom.mvc.security.core.AuthenticationService;
import org.agatom.springatom.mvc.security.core.TokenService;
import org.agatom.springatom.mvc.security.core.TokenService.InvalidTokenException;
import org.agatom.springatom.mvc.security.token.TokenInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.MarkerManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;


@Service
class AuthenticationServiceImpl
    implements AuthenticationService {
  private static final Logger LOGGER = LogManager.getLogger(AuthenticationServiceImpl.class);
  @Autowired
  private ApplicationContext    applicationContext;
  @Autowired
  private AuthenticationManager authenticationManager;
  @Autowired
  private TokenService          tokenManager;

  @Override
  public TokenInfo authenticate(final String login, final String password) {
    LOGGER.entry(login, password);
    TokenInfo info = null;
    try {
      final Authentication authentication = this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login, password));
      SecurityContextHolder.getContext().setAuthentication(authentication);

      if (authentication.getPrincipal() != null) {
        final UserDetails userContext = (UserDetails) authentication.getPrincipal();
        info = this.tokenManager.createNewToken(userContext);
      }
    } catch (Exception exp) {
      LOGGER.error(MarkerManager.getMarker("Auth"), String.format("Failed to authenticate %s", login), exp);
    }
    LOGGER.exit(info);
    return info;
  }

  @Override
  public void checkToken(final String token) {
    final UserDetails userDetails = this.tokenManager.getUserDetails(token);
    if (userDetails == null) {
      throw new InvalidTokenException(token);
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

  @Override
  public void logout(final String token) {
    SecurityContextHolder.clearContext();
  }

  @Override
  public UserDetails currentUser() {
    final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication == null) {
      return null;
    }
    return (UserDetails) authentication.getPrincipal();
  }

}

package org.agatom.springatom.security.support;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.agatom.springatom.security.core.AuthenticationService;
import org.agatom.springatom.security.core.TokenService;
import org.agatom.springatom.security.token.TokenInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TokenAuthenticationFilter
  extends OncePerRequestFilter {
  private static final Logger                LOGGER                = LogManager.getLogger(TokenAuthenticationFilter.class);
  private static final String                SKIP_FILTER           = "TAF_SKIP";
  private              AuthenticationService authenticationService = null;
  private              ObjectMapper          objectMapper          = null;
  private              TokenHeaders          tokenHeaders          = null;

  public TokenAuthenticationFilter setTokenHeaders(final TokenHeaders tokenHeaders) {
    this.tokenHeaders = tokenHeaders;
    return this;
  }

  public TokenAuthenticationFilter setAuthenticationService(final AuthenticationService authenticationService) {
    this.authenticationService = authenticationService;
    return this;
  }

  public TokenAuthenticationFilter setObjectMapper(final ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
    return this;
  }


  public interface TokenHeaders {
    String getHeaderToken();

    String getHeaderUsername();

    String getHeaderPassword();
  }

  @Override
  protected boolean shouldNotFilter(final HttpServletRequest request) throws ServletException {
    return StringUtils.hasText((String) request.getAttribute(SKIP_FILTER));
  }


  @Override
  protected void doFilterInternal(final HttpServletRequest request,
                                  final HttpServletResponse response,
                                  final FilterChain filterChain) throws ServletException, IOException {
    LOGGER.entry();
    final boolean tokenOk = this.checkToken(request, response);

    if (!this.shouldNotFilter(request)) {
      if (!tokenOk) {
        if (this.checkLogin(request, response)) {
          LOGGER.exit("Successfully logged in");
        } else {
          LOGGER.exit("Failed to log in");
        }
      } else {
        LOGGER.exit("Token successfully verified");
      }
    } else {
      LOGGER.exit("Something went wrong, processing was skipped due to an error");
    }

  }

  private boolean checkLogin(final HttpServletRequest request, final HttpServletResponse response) throws IOException {
    final String headerUsername = this.tokenHeaders.getHeaderUsername();
    final String headerPassword = this.tokenHeaders.getHeaderPassword();

    final String username = request.getHeader(headerUsername);
    final String password = request.getHeader(headerPassword);

    if (StringUtils.hasLength(username) && StringUtils.hasText(password)) {
      final TokenInfo token = this.authenticationService.authenticate(username, password);
      if (token != null) {
        response.setHeader(this.tokenHeaders.getHeaderToken(), token.getToken());
        response.setStatus(HttpServletResponse.SC_OK);
        this.objectMapper.writeValue(response.getOutputStream(), token);
        return true;
      } else {
        response.sendError(
          HttpServletResponse.SC_UNAUTHORIZED,
          String.format("Failed to authenticate user %s", username)
        );
      }
    } else {
      response.sendError(
        HttpServletResponse.SC_BAD_REQUEST,
        String.format("Either %s or %s not supplied", headerUsername, headerPassword)
      );
      skipFilterNext(request);
    }
    return false;
  }

  private boolean checkToken(final HttpServletRequest request,
                             final HttpServletResponse response) throws IOException {
    final String token = request.getHeader(this.tokenHeaders.getHeaderToken());
    if (StringUtils.hasLength(token)) {
      try {
        this.authenticationService.checkToken(token);
        return true;
      } catch (TokenService.InvalidTokenException ite) {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, ite.getMessage());
        skipFilterNext(request);
      }
    }
    return false;
  }

  private void skipFilterNext(HttpServletRequest httpRequest) {
    httpRequest.setAttribute(SKIP_FILTER, "");
  }


}

package org.agatom.springatom.security.support;

import org.agatom.springatom.security.core.AuthenticationService;
import org.agatom.springatom.security.core.TokenService;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class TokenAuthenticationFilter
    extends OncePerRequestFilter {
  private AuthenticationService authenticationService = null;
  private TokenHeaders          tokenHeaders          = null;
  private TokenIgnoredPaths     tokenIgnoredPaths     = null;

  public TokenAuthenticationFilter setTokenHeaders(final TokenHeaders tokenHeaders) {
    this.tokenHeaders = tokenHeaders;
    return this;
  }

  public TokenAuthenticationFilter setTokenIgnoredPaths(final TokenIgnoredPaths tokenIgnoredPaths) {
    this.tokenIgnoredPaths = tokenIgnoredPaths;
    return this;
  }

  public TokenAuthenticationFilter setAuthenticationService(final AuthenticationService authenticationService) {
    this.authenticationService = authenticationService;
    return this;
  }

  @Override
  protected boolean shouldNotFilter(final HttpServletRequest request) throws ServletException {
    return this.tokenIgnoredPaths.getPaths().contains(request.getRequestURI());
  }


  @Override
  protected void doFilterInternal(final HttpServletRequest request,
                                  final HttpServletResponse response,
                                  final FilterChain filterChain) throws ServletException, IOException {
    try {
      this.checkToken(request);
      filterChain.doFilter(request, response);
    } catch (TokenService.InvalidTokenException | TokenService.TokenExpiredException tee) {
      response.sendError(HttpServletResponse.SC_UNAUTHORIZED, tee.getMessage());
    } catch (Exception exp) {
      response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, exp.getMessage());
    }

  }

  private void checkToken(final HttpServletRequest request) {
    final String token = request.getHeader(this.tokenHeaders.getHeaderToken());
    if (StringUtils.hasLength(token)) {
      this.authenticationService.checkToken(token);
    }
  }

  @FunctionalInterface
  public interface TokenHeaders {
    String getHeaderToken();
  }

  @FunctionalInterface
  public interface TokenIgnoredPaths {
    List<String> getPaths();
  }


}

package org.agatom.springatom.security;

import com.google.common.collect.Lists;
import org.agatom.springatom.security.support.TokenAuthenticationFilter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties(prefix = "sa.security")
public class SSecurityProperties {
  private Token token = new Token();
  private Paths paths = new Paths();

  public Token getToken() {
    return token;
  }

  public SSecurityProperties setToken(final Token token) {
    this.token = token;
    return this;
  }

  public SSecurityProperties setPaths(final Paths paths) {
    this.paths = paths;
    return this;
  }

  public Paths getPaths() {
    return paths;
  }

  public void configureTokenFilter(final TokenAuthenticationFilter filter) {
    filter.setTokenHeaders(token::getHeaderToken);
    filter.setTokenIgnoredPaths(() -> Lists.<String>newArrayList(paths.getLogin(), paths.getProcessing()));
  }

  public static class Paths {
    private String login;
    private String processing;

    public Paths setLogin(final String login) {
      this.login = login;
      return this;
    }

    public Paths setProcessing(final String processing) {
      this.processing = processing;
      return this;
    }

    public String getLogin() {
      return login;
    }

    public String getProcessing() {
      return processing;
    }
  }

  public static class Token {
    private long   ttl;
    private int    initialSize;
    private String headerToken;
    private String headerUsername;
    private String headerPassword;

    public int getInitialSize() {
      return initialSize;
    }

    public Token setInitialSize(final int initialSize) {
      this.initialSize = initialSize;
      return this;
    }

    public long getTtl() {
      return ttl;
    }

    public Token setTtl(final long ttl) {
      this.ttl = ttl;
      return this;
    }

    public String getHeaderToken() {
      return headerToken;
    }

    public Token setHeaderToken(final String headerToken) {
      this.headerToken = headerToken;
      return this;
    }

    public String getHeaderUsername() {
      return headerUsername;
    }

    public Token setHeaderUsername(final String headerUsername) {
      this.headerUsername = headerUsername;
      return this;
    }

    public String getHeaderPassword() {
      return headerPassword;
    }

    public Token setHeaderPassword(final String headerPassword) {
      this.headerPassword = headerPassword;
      return this;
    }
  }


}

package org.agatom.springatom.security;

import org.agatom.springatom.security.support.TokenAuthenticationFilter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "sa.security")
public class SSecurityProperties {
  private Token token = new Token();

  public Token getToken() {
    return token;
  }

  public SSecurityProperties setToken(final Token token) {
    this.token = token;
    return this;
  }

  public void configureTokenFilter(final TokenAuthenticationFilter filter) {
    filter.setTokenHeaders(new TokenAuthenticationFilter.TokenHeaders() {
      @Override
      public String getHeaderToken() {
        return token.getHeaderToken();
      }

      @Override
      public String getHeaderUsername() {
        return token.getHeaderUsername();
      }

      @Override
      public String getHeaderPassword() {
        return token.getHeaderPassword();
      }
    });
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

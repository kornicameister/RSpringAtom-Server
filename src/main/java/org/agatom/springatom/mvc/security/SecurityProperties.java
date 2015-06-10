package org.agatom.springatom.mvc.security;

import com.google.common.base.MoreObjects;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "sa.security")
public class SecurityProperties {
  private Token token = new Token();

  public SecurityProperties setToken(final Token token) {
    this.token = token;
    return this;
  }

  public Token getToken() {
    return token;
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("token", token)
        .toString();
  }

  public static class Token {
    private long ttl;
    private int  initialSize;

    public Token setInitialSize(final int initialSize) {
      this.initialSize = initialSize;
      return this;
    }

    public int getInitialSize() {
      return initialSize;
    }

    public Token setTtl(final long ttl) {
      this.ttl = ttl;
      return this;
    }

    public long getTtl() {
      return ttl;
    }
  }


}

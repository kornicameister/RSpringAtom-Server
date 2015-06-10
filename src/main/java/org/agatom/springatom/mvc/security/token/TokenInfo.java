package org.agatom.springatom.mvc.security.token;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.Objects;
import com.google.common.collect.ComparisonChain;
import org.joda.time.DateTime;
import org.springframework.security.core.userdetails.UserDetails;

import javax.annotation.Nonnull;
import java.io.Serializable;

public class TokenInfo
    implements Serializable, Comparable<TokenInfo> {
  private static final long        serialVersionUID = -8023866526045724870L;
  private              DateTime    issuedAt         = null;
  private              DateTime    expiresAt        = null;
  private              String      token            = null;
  @JsonIgnore
  private              UserDetails userDetails      = null;

  public TokenInfo() {
    this.issuedAt = DateTime.now();
  }

  public TokenInfo(final String token) {
    this();
    this.token = token;
  }

  public TokenInfo setIssuedAt(final DateTime issuedAt) {
    this.issuedAt = issuedAt;
    return this;
  }

  public TokenInfo setExpiresAt(final DateTime expiresAt) {
    this.expiresAt = expiresAt;
    return this;
  }

  public TokenInfo setToken(final String token) {
    this.token = token;
    return this;
  }

  public DateTime getIssuedAt() {
    return issuedAt;
  }

  public DateTime getExpiresAt() {
    return expiresAt;
  }

  public String getToken() {
    return token;
  }

  public TokenInfo setUserDetails(final UserDetails userDetails) {
    this.userDetails = userDetails;
    return this;
  }

  public UserDetails getUserDetails() {
    return userDetails;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    TokenInfo that = (TokenInfo) o;

    return Objects.equal(this.token, that.token);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(token);
  }

  @Override
  public int compareTo(@Nonnull final TokenInfo tokenInfo) {
    return ComparisonChain.start().compare(this.token, tokenInfo.token).result();
  }
}

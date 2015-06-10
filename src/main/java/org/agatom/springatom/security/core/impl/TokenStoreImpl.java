package org.agatom.springatom.security.core.impl;

import com.google.common.base.Optional;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.FluentIterable;
import org.agatom.springatom.security.SSecurityProperties;
import org.agatom.springatom.security.core.TokenStore;
import org.agatom.springatom.security.token.TokenInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;

@Service
@Validated
class TokenStoreImpl
    implements TokenStore {
  private static final Logger                        LOGGER              = LogManager.getLogger(TokenStoreImpl.class);
  private static final Marker                        TS_C_MARKER         = MarkerManager.getMarker("TokenStore::Cache");
  @Autowired
  private              SSecurityProperties           SSecurityProperties = null;
  private              Cache<UserDetails, TokenInfo> cache               = null;

  @Scheduled(fixedRate = 60 * 1000)
  private void logCacheStats() {
    if (LOGGER.isTraceEnabled()) {
      LOGGER.trace(TS_C_MARKER, this.cache.stats());
    }
  }

  @PostConstruct
  private void setUp() {
    final SSecurityProperties.Token token = this.SSecurityProperties.getToken();

    this.cache = CacheBuilder.<String, TokenInfo>newBuilder()
      .expireAfterWrite(token.getTtl(), TimeUnit.MILLISECONDS)
      .initialCapacity(token.getInitialSize())
      .weakKeys()
      .recordStats()
      .build();
  }

  @PreDestroy
  private void destroy() {
    this.cache.cleanUp();
  }

  @Override
  public TokenInfo store(final TokenInfo info) {
    final DateTime issuedAt = DateTime.now();
    info.setIssuedAt(issuedAt)
      .setExpiresAt(issuedAt.plusMillis((int) this.SSecurityProperties.getToken().getTtl()));

    this.cache.put(info.getUserDetails(), info);

    return info;
  }

  @Override
  public TokenInfo read(final Predicate<TokenInfo> predicate) {
    final Collection<TokenInfo> values = this.cache
      .asMap()
      .values();

    final Optional<TokenInfo> first = FluentIterable.from(values).filter(predicate::test).first();
    if (first.isPresent()) {
      return this.cache.getIfPresent(first.get().getUserDetails());
    }
    return null;
  }

  @Override
  public TokenInfo remove(final Predicate<TokenInfo> predicate) {
    final TokenInfo first = this.cache
        .asMap()
        .values()
        .stream()
        .filter(predicate)
        .findFirst()
        .orElse(null);

    if(first != null){
      this.cache.invalidate(first.getUserDetails());
    }

    return first;
  }

  @Override
  public Iterable<TokenInfo> list() {
    return Collections.unmodifiableCollection(this.cache.asMap().values());
  }

}

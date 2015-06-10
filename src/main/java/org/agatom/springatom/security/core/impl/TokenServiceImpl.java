package org.agatom.springatom.security.core.impl;

import com.google.common.collect.Maps;
import org.agatom.springatom.security.core.TokenFactory;
import org.agatom.springatom.security.core.TokenService;
import org.agatom.springatom.security.core.TokenStore;
import org.agatom.springatom.security.token.TokenInfo;
import org.apache.logging.log4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.Map;
import java.util.function.Predicate;

@Service
class TokenServiceImpl
    implements TokenService {
  private static final Logger LOGGER = LogManager.getLogger(TokenServiceImpl.class);
  private static final Marker MARKER = MarkerManager.getMarker("TokenService");
  @Autowired
  private TokenStore   tokenStore;
  @Autowired
  private TokenFactory tokenFactory;

  @Override
  public TokenInfo createNewToken(final UserDetails userDetails) throws Exception {
    LOGGER.entry(userDetails);
    final String token;
    try {
      token = this.tokenFactory.generateToken(userDetails);
    } catch (NoSuchAlgorithmException exp) {
      throw LOGGER.throwing(Level.ERROR, exp);
    }

    // issued_at and expires_at will be set in tokenStore
    final TokenInfo tokenInfo = new TokenInfo()
        .setToken(token)
        .setUserDetails(userDetails);

    this.removeUserDetails(userDetails);

    return this.tokenStore.store(tokenInfo);
  }

  @Override
  public void removeUserDetails(final UserDetails userDetails) {
    this.tokenStore.remove(ti -> ti.getUserDetails().equals(userDetails));
  }

  @Override
  public UserDetails removeToken(final String token) {
    final Predicate<TokenInfo> predicate = tr -> tr.getToken().equals(token);

    final UserDetails userDetails = this.tokenStore.read(predicate).getUserDetails();
    this.tokenStore.remove(predicate);

    return userDetails;
  }

  @Override
  public UserDetails getUserDetails(final String token) {
    return this.tokenStore.read(tr -> tr.getToken().equals(token)).getUserDetails();
  }

  @Override
  public Iterable<TokenInfo> getUserTokens(final UserDetails userDetails) {
    final TokenInfo read = this.tokenStore.read(tr -> tr.getUserDetails().equals(userDetails));
    return Collections.singletonList(read);
  }

  @Override
  public Map<String, UserDetails> getValidUsers() {
    final Iterable<TokenInfo> list = this.tokenStore.list();
    final Map<String, UserDetails> map = Maps.newHashMap();

    list.forEach(ti -> map.put(ti.getToken(), ti.getUserDetails()));

    return map;
  }

}

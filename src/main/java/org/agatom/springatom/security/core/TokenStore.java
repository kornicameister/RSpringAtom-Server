package org.agatom.springatom.security.core;


import org.agatom.springatom.security.token.TokenInfo;

import java.util.function.Predicate;

public interface TokenStore {

  TokenInfo store(final TokenInfo tokenInfo);

  TokenInfo read(final Predicate<TokenInfo> predicate);

  TokenInfo remove(final Predicate<TokenInfo> predicate);

  Iterable<TokenInfo> list();

}

package org.agatom.springatom.mvc.security.core;


import org.agatom.springatom.mvc.security.token.TokenInfo;

import java.util.function.Predicate;

public interface TokenStore {

  TokenInfo store(final TokenInfo tokenInfo);

  TokenInfo read(final Predicate<TokenInfo> predicate);

  TokenInfo remove(final Predicate<TokenInfo> predicate);

  Iterable<TokenInfo> list();

}

package org.agatom.springatom.mvc.security.core;

import org.springframework.security.core.userdetails.UserDetails;

import java.security.NoSuchAlgorithmException;

public interface TokenFactory {

  String generateToken(final UserDetails source) throws NoSuchAlgorithmException;

}

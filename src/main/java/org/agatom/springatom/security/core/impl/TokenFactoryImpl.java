package org.agatom.springatom.security.core.impl;

import org.agatom.springatom.security.core.TokenFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.SerializationUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
class TokenFactoryImpl
    implements TokenFactory {

  @Override
  public String generateToken(final UserDetails source) throws NoSuchAlgorithmException {
    byte[] bytes = SerializationUtils.serialize(source);
    final MessageDigest instance = MessageDigest.getInstance("SHA-256");

    instance.update(bytes);
    bytes = instance.digest();

    StringBuilder hexString = new StringBuilder();
    for (final byte aByte : bytes) {
      hexString.append(Integer.toHexString(0xFF & aByte));
    }

    return hexString.toString();
  }
}

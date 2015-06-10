package org.agatom.springatom.security.core.impl;

import org.agatom.springatom.security.core.TokenFactory;
import org.joda.time.DateTime;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.SerializationUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

@Service
class TokenFactoryImpl
  implements TokenFactory {
  private static final String ALGORITHM = "SHA-256";

  @Override
  public String generateToken(final UserDetails source) throws NoSuchAlgorithmException {
    final byte[] user = SerializationUtils.serialize(source);       // user instance as bytes
    final byte[] now = SerializationUtils.serialize(DateTime.now());// current time as bytes, ie salt
    final byte[] securedRandom = SecureRandom.getSeed(16);

    final MessageDigest instance = MessageDigest.getInstance(ALGORITHM);

    instance.update(user);
    instance.update(now);
    instance.update(securedRandom);

    return this.toString(instance);
  }

  private String toString(final MessageDigest instance) {
    final byte[] result = instance.digest();

    final StringBuilder hexString = new StringBuilder();
    for (final byte aByte : result) {
      hexString.append(Integer.toHexString(0xFF & aByte));
    }

    return hexString.toString();
  }
}

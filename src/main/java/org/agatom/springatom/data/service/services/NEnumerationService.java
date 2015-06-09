package org.agatom.springatom.data.service.services;


import org.agatom.springatom.data.model.enumeration.NEnumeration;
import org.agatom.springatom.data.model.enumeration.NEnumerationEntry;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.InputStream;
import java.util.Collection;

public interface NEnumerationService {
  String CACHE_NAME = "enumerations";

  @CacheEvict(value = CACHE_NAME, allEntries = true, beforeInvocation = false)
  @Transactional(rollbackFor = EnumerationServiceException.class)
  NEnumeration getEnumeration(final String name) throws EnumerationServiceException;

  @CacheEvict(value = CACHE_NAME, allEntries = true, beforeInvocation = false)
  @Transactional(rollbackFor = EnumerationServiceException.class)
  NEnumeration newEnumeration(final String name, final File file) throws EnumerationServiceException;

  @CacheEvict(value = CACHE_NAME, allEntries = true, beforeInvocation = false)
  @Transactional(rollbackFor = EnumerationServiceException.class)
  NEnumeration newEnumeration(final String name, final InputStream stream) throws EnumerationServiceException;

  @CacheEvict(value = CACHE_NAME, allEntries = true, beforeInvocation = false)
  @Transactional(rollbackFor = EnumerationServiceException.class)
  NEnumeration newEnumeration(final String name, final Collection<NEnumerationEntry> entries) throws EnumerationServiceException;

  @NotNull
  @CacheEvict(value = CACHE_NAME, allEntries = true, beforeInvocation = false)
  @Transactional(rollbackFor = EnumerationServiceException.class)
  Iterable<NEnumeration> newEnumerations(@NotNull final File stream) throws EnumerationServiceException;

  @NotNull
  @CacheEvict(value = CACHE_NAME, allEntries = true, beforeInvocation = false)
  @Transactional(rollbackFor = EnumerationServiceException.class)
  Iterable<NEnumeration> newEnumerations(@NotNull final InputStream stream) throws EnumerationServiceException;

  @Cacheable(value = CACHE_NAME)
  @Transactional(readOnly = true, rollbackFor = EnumerationServiceException.class, isolation = Isolation.READ_COMMITTED)
  NEnumerationEntry getEnumerationEntry(String name, String entryKey) throws EnumerationServiceException;

  /**
   * Looks for {@link org.agatom.springatom.data.types.enumeration.Enumeration} by given {@code name} and if found
   * tries to resolve {@link org.agatom.springatom.data.types.enumeration.EnumerationEntry} for {@code entryKey}.
   * This method works similar to {@link #getEnumerationEntry(String, String)}. Difference is that it returns
   * a {@link org.agatom.springatom.data.types.enumeration.EnumerationEntry#getKey()}
   *
   * @param name     {@link org.agatom.springatom.data.types.enumeration.Enumeration#getName()}
   * @param entryKey {@link org.agatom.springatom.data.types.enumeration.EnumerationEntry#getKey()}
   *
   * @return the value
   *
   * @throws EnumerationServiceException
   */
  @Cacheable(value = CACHE_NAME)
  @Transactional(readOnly = true, rollbackFor = EnumerationServiceException.class, isolation = Isolation.READ_COMMITTED)
  String getEnumeratedValue(@NotNull String name, @NotNull String entryKey) throws EnumerationServiceException;

  class EnumerationServiceException
    extends DataAccessException {
    private static final long serialVersionUID = 133681215179736030L;

    public EnumerationServiceException(final Throwable exp) {
      this("EnumerationException", exp);
    }

    public EnumerationServiceException(final String message, final Throwable root) {
      super(message, root);
    }

    public EnumerationServiceException(final String message) {
      super(message);
    }

    public static EnumerationServiceException enumerationNotFound(final String key) {
      return new EnumerationServiceException(String.format("Enumeration for key=%s not found", key));
    }

    public static EnumerationServiceException enumerationEntryNotFound(final String key, final String enumerationKey) {
      return new EnumerationServiceException(String.format("Enumeration(key=%s) contains no entry of %s", key, enumerationKey));
    }
  }
}

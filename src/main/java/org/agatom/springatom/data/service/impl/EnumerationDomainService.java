package org.agatom.springatom.data.service.impl;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.agatom.springatom.data.model.enumeration.NEnumeration;
import org.agatom.springatom.data.model.enumeration.NEnumerationEntry;
import org.agatom.springatom.data.repo.repositories.enumeration.NEnumerationRepository;
import org.agatom.springatom.data.service.services.NEnumerationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.io.*;
import java.util.Collection;
import java.util.Set;

@Service
class EnumerationDomainService
  implements NEnumerationService {
  private static final String                      ENUMERATIONS          = "enumerations";
  @Autowired
  private              ObjectMapper                objectMapper          = null;
  @Autowired
  private              NEnumerationRepository      enumerationRepository = null;


  @Override
  public NEnumeration newEnumeration(final String name, final File file) throws EnumerationServiceException {
    final BufferedInputStream stream;
    try {
      stream = new BufferedInputStream(new FileInputStream(file));
    } catch (FileNotFoundException exp) {
      throw new EnumerationServiceException(exp);
    }
    return this.newEnumeration(name, stream);
  }

  @Override
  public NEnumeration newEnumeration(final String name, final InputStream stream) throws EnumerationServiceException {
    try {
      final Object object = this.objectMapper.readTree(new BufferedInputStream(stream));
      Assert.isInstanceOf(Collection.class, object);
    } catch (Exception exp) {
      throw new EnumerationServiceException(exp);
    }
    return null;
  }

  @Override
  public Iterable<NEnumeration> newEnumerations(final File file) throws EnumerationServiceException {
    final BufferedInputStream stream;
    try {
      stream = new BufferedInputStream(new FileInputStream(file));
    } catch (FileNotFoundException exp) {
      throw new EnumerationServiceException(exp);
    }
    return this.newEnumerations(stream);
  }

  @Override
  public Iterable<NEnumeration> newEnumerations(final InputStream stream) throws EnumerationServiceException {
    final Set<NEnumeration> nEnumerations = Sets.newHashSet();
    try {
      final JsonNode node = this.objectMapper.readTree(new BufferedInputStream(stream));
      final ArrayNode enumerations = (ArrayNode) node.get(ENUMERATIONS);
      for (final JsonNode next : enumerations) {
        final String name = next.get("name").asText();
        final Collection<NEnumerationEntry> entries = this.readEntries((ArrayNode) next.get("entries"));
        nEnumerations.add(this.newEnumeration(name, entries));
      }
    } catch (Exception exp) {
      throw new EnumerationServiceException(exp);
    }
    return nEnumerations;
  }

  private Collection<NEnumerationEntry> readEntries(final ArrayNode entries) {
    final Set<NEnumerationEntry> set = Sets.newHashSetWithExpectedSize(entries.size());
    for (final JsonNode entry : entries) {
      final NEnumerationEntry enumerationEntry = new NEnumerationEntry();
      enumerationEntry.setKey(entry.get("key").asText())
        .setComment(entry.get("comment").asText())
        .setValue(entry.get("value").asText());
      set.add(enumerationEntry);
    }
    return set;
  }

  @Override
  public NEnumeration newEnumeration(final String name, final Collection<NEnumerationEntry> entries) {
    final NEnumeration enumeration = new NEnumeration()
      .setName(name)
      .setEntries(Lists.newArrayList(entries));
    return this.enumerationRepository.save(enumeration);
  }

  @Override
  public String getEnumeratedValue(final String name, final String entryKey) throws EnumerationServiceException {
    final NEnumerationEntry entry = this.getEnumerationEntry(name, entryKey);
    return entry != null ? entry.getKey() : null;
  }

  @Override
  public NEnumerationEntry getEnumerationEntry(final String name, final String entryKey) throws EnumerationServiceException {
    final NEnumeration enumeration = this.getEnumeration(name);
    final Collection<NEnumerationEntry> entries = enumeration.getEntries();
    if (CollectionUtils.isEmpty(entries)) {
      return null;
    }
    final Optional<NEnumerationEntry> match = FluentIterable.from(entries).firstMatch(new Predicate<NEnumerationEntry>() {
      @Override
      public boolean apply(final NEnumerationEntry input) {
        return input != null && input.getKey().equalsIgnoreCase(entryKey);
      }
    });
    if (match.isPresent()) {
      return match.get();
    }
    throw EnumerationServiceException.enumerationEntryNotFound(name, entryKey);
  }

  @Override
  public NEnumeration getEnumeration(final String name) throws EnumerationServiceException {
    NEnumeration value;
    try {
      final java.util.Optional<NEnumeration> byName = this.enumerationRepository.findByName(name);
      value = byName.isPresent() ? byName.get() : null;
      Assert.notNull(value);
    } catch (IllegalArgumentException exp) {
      throw EnumerationServiceException.enumerationNotFound(name);
    } catch (Exception exp) {
      throw new EnumerationServiceException(exp);
    }
    return value;
  }
}

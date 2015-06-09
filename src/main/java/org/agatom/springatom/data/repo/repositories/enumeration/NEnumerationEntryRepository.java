package org.agatom.springatom.data.repo.repositories.enumeration;

import org.agatom.springatom.data.model.enumeration.NEnumerationEntry;
import org.agatom.springatom.data.repo.repositories.NRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

@Repository
@RestResource(exported = false)
public interface NEnumerationEntryRepository
        extends NRepository<NEnumerationEntry> {
}

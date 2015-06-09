package org.agatom.springatom.data.repo.repositories.error;

import org.agatom.springatom.data.model.error.NError;
import org.agatom.springatom.data.repo.repositories.NRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * <small>Class is a part of <b>SpringAtom2</b> and was created at 2014-11-27</small>
 * </p>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
@Repository
@RestResource(exported = false)
public interface NErrorRepository
        extends NRepository<NError> {
}

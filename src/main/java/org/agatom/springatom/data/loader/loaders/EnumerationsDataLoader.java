package org.agatom.springatom.data.loader.loaders;

import org.agatom.springatom.data.loader.annotation.DataLoader;
import org.agatom.springatom.data.loader.srv.AbstractDataLoaderService;
import org.agatom.springatom.data.service.services.NEnumerationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.InputStream;

/**
 * <p>
 * <small>Class is a part of <b>SpringAtom2</b> and was created at 2014-10-11</small>
 * </p>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
@DataLoader
class EnumerationsDataLoader
        extends AbstractDataLoaderService {
  private static final Logger              LOGGER             = LoggerFactory.getLogger(EnumerationsDataLoader.class);
  private static final String              PATH               = "classpath:org/agatom/springatom/data/enumerations.json";
  @Autowired
  private              NEnumerationService enumerationService = null;

  @Override
  public InstallationMarker loadData() {
    final InstallationMarker marker = new InstallationMarker();
    try {

      final InputStream stream = this.getStream(PATH);
      this.enumerationService.newEnumerations(stream);

      marker.setHash(stream.hashCode());
      marker.setPath(PATH);

    } catch (Exception exp) {
      LOGGER.error("Failed to load enumerations data", exp);
      marker.setError(exp);
    }
    return marker;
  }
}

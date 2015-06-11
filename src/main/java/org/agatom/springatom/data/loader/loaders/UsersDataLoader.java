package org.agatom.springatom.data.loader.loaders;

import org.agatom.springatom.data.loader.annotation.DataLoader;
import org.agatom.springatom.data.loader.srv.AbstractDataLoaderService;
import org.agatom.springatom.data.service.services.NUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;

import java.util.Objects;

/**
 * <p>
 * <small>Class is a part of <b>SpringAtom2</b> and was created at 2014-10-11</small>
 * </p>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
@Order(2)
@DataLoader
class UsersDataLoader
  extends AbstractDataLoaderService {
  private static final Logger       LOGGER      = LoggerFactory.getLogger(UsersDataLoader.class);
  private static final String       PATH        = "classpath:org/agatom/springatom/data/users.json";
  @Autowired
  private              NUserService userService = null;

  @Override
  public InstallationMarker loadData() {
    final InstallationMarker marker = new InstallationMarker();

    try {
      marker.setHash(Objects.hashCode(this.userService.loadData(this.getStream(PATH))));
      marker.setPath(PATH);
    } catch (Exception exp) {
      LOGGER.error(String.format("Error when loading users from %s", PATH), exp);
      marker.setError(exp);
    }

    return marker;
  }
}

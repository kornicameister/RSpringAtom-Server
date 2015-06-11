package org.agatom.springatom.data.loader.loaders;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.google.common.collect.Sets;
import org.agatom.springatom.data.loader.annotation.DataLoader;
import org.agatom.springatom.data.loader.srv.AbstractDataLoaderService;
import org.agatom.springatom.data.model.user.authority.NRole;
import org.agatom.springatom.data.repo.repositories.authority.NRoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;

import java.io.InputStream;
import java.util.Iterator;
import java.util.Set;

/**
 * <p>
 * <small>Class is a part of <b>SpringAtom2</b> and was created at 2014-10-10</small>
 * </p>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
@Order(1)
@DataLoader
class RolesDataLoader
  extends AbstractDataLoaderService {
  private static final Logger          LOGGER       = LoggerFactory.getLogger(RolesDataLoader.class);
  private static final String          PATH         = "classpath:org/agatom/springatom/data/roles.json";
  @Autowired
  private              ObjectMapper    objectMapper = null;
  @Autowired
  private              NRoleRepository repository   = null;

  @Override
  public InstallationMarker loadData() {
    final InstallationMarker marker = new InstallationMarker();

    try {

      final InputStream stream = this.getStream(PATH);
      final JsonNode jsonNode = this.objectMapper.readTree(stream);
      final JsonNode roles = jsonNode.get("roles");
      final Set<NRole> nRoles = this.readRoles(roles);
      if (LOGGER.isTraceEnabled()) {
        LOGGER.trace(String.format("Read %d roles to persist", nRoles.size()));
      }
      this.repository.save(nRoles);

      marker.setHash(stream.hashCode());
      marker.setPath(PATH);

    } catch (Exception exp) {
      LOGGER.error("Failed to load roles data", exp);
      marker.setError(exp);
    }

    return marker;
  }

  private Set<NRole> readRoles(final JsonNode roles) {
    final ArrayNode node = (ArrayNode) roles;
    final Iterator<JsonNode> iterator = node.iterator();
    final Set<NRole> roleSet = Sets.newHashSet();
    while (iterator.hasNext()) {
      final JsonNode next = iterator.next();
      final NRole role = new NRole();
      role.setAuthority(next.asText());
      roleSet.add(role);
    }
    return roleSet;
  }

}

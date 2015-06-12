package org.agatom.springatom.data.loader.loaders;


import com.google.common.collect.Sets;
import org.agatom.springatom.data.loader.annotation.DataLoader;
import org.agatom.springatom.data.loader.srv.DataLoaderService;
import org.agatom.springatom.data.model.person.NPerson;
import org.agatom.springatom.data.model.user.NUser;
import org.agatom.springatom.data.model.user.authority.NRole;
import org.agatom.springatom.data.repo.repositories.authority.NRoleRepository;
import org.agatom.springatom.data.service.services.NUserService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.GrantedAuthority;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.List;
import java.util.Random;

@DataLoader
@Order(Ordered.LOWEST_PRECEDENCE)
class DebugDataLoader
    implements DataLoaderService {
  private static final int             MAX_VALS       = 50;
  private static final int             ROLES_COUNT    = 5;
  @Autowired
  private              NUserService    userService    = null;
  @Autowired
  private              NRoleRepository roleRepository = null;
  private              List<NRole>     allRoles       = null;

  @PostConstruct
  private void init() {
    this.allRoles = this.roleRepository.findAll();
  }

  @Override
  public InstallationMarker loadData() {
    final InstallationMarker marker = new InstallationMarker();
    final Random random = new Random(DateTime.now().getMillis());

    try {
      this.loadUsers(this.getCount(random));
    } catch (Exception exp) {
      marker.setError(exp);
    }

    return marker;
  }

  private void loadUsers(int count) throws Exception {

    NUser user;
    NPerson person = null;
    Collection<GrantedAuthority> authorities = null;

    while (count-- > 0) {
      user = new NUser()
          .setUsername(String.format("user_%d", count))
          .setPassword("test")
          .setAccountNonLocked(count % 2 == 0)
          .setEmail(String.format("user_%d@gmail.com", count));

      if (count % 3 == 0) {
        person = new NPerson()
            .setFirstName(String.format("John %d", count))
            .setLastName("Doe");
      }

      if (count % 5 != 0) {
        authorities = Sets.newHashSet();
        while (authorities.size() != ROLES_COUNT) {
          authorities.add((GrantedAuthority) () -> allRoles.get(new Random().nextInt(allRoles.size())).getAuthority());
        }
      }

      this.userService.registerNewUser(user, person, authorities);

    }
  }

  private int getCount(final Random random) {
    return Math.abs(random.nextInt(MAX_VALS));
  }
}

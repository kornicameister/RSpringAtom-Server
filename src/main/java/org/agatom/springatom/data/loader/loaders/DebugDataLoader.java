package org.agatom.springatom.data.loader.loaders;


import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.neovisionaries.i18n.CountryCode;
import org.agatom.springatom.data.loader.annotation.DataLoader;
import org.agatom.springatom.data.loader.srv.DataLoaderService;
import org.agatom.springatom.data.model.car.NCarMaster;
import org.agatom.springatom.data.model.enumeration.NEnumerationEntry;
import org.agatom.springatom.data.model.person.NPerson;
import org.agatom.springatom.data.model.person.NPersonContact;
import org.agatom.springatom.data.model.user.NUser;
import org.agatom.springatom.data.model.user.authority.NRole;
import org.agatom.springatom.data.repo.repositories.authority.NRoleRepository;
import org.agatom.springatom.data.repo.repositories.car.NCarMasterRepository;
import org.agatom.springatom.data.repo.repositories.person.NPersonRepository;
import org.agatom.springatom.data.service.services.NEnumerationService;
import org.agatom.springatom.data.service.services.NUserService;
import org.agatom.springatom.data.service.services.NUserService.UserRegistrationBean;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.Ordered;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.GrantedAuthority;

import java.util.*;

@DataLoader
@ConditionalOnProperty(name = "debug", havingValue = "true")
class DebugDataLoader
    implements DataLoaderService {
  private static final int                  MAX_VALS            = 50;
  private static final int                  ROLES_COUNT         = 6;
  private static final int                  CONTACTS_COUNT      = 12;
  @Autowired
  private              NUserService         userService         = null;
  @Autowired
  private              NRoleRepository      roleRepository      = null;
  @Autowired
  private              NPersonRepository    personRepository    = null;
  @Autowired
  private              NEnumerationService  enumerationService  = null;
  @Autowired
  private              NCarMasterRepository carMasterRepository = null;
  private              List<NRole>          allRoles            = null;

  @Override
  public InstallationMarker loadData() {
    final InstallationMarker marker = new InstallationMarker();
    final Random random = new Random(DateTime.now().getMillis());

    this.allRoles = this.roleRepository.findAll();

    try {
      this.loadUsers(this.getCount(random));
      this.loadCarMasters(this.getCount(random));
    } catch (Exception exp) {
      marker.setError(exp);
    }

    marker.setHash(DateTime.now().hashCode());
    marker.setPath("MEMORY");

    return marker;
  }

  private void loadUsers(int count) throws Exception {
    final List<NEnumerationEntry> contactTypes = Lists.newArrayList(this.enumerationService.getEnumeration("CONTACT_TYPES").getEntries());
    final int size = contactTypes.size();

    NUser user;
    NPerson person;
    Collection<GrantedAuthority> authorities;

    long latestPersonId;
    int subItemsProcessed;
    latestPersonId = this.personRepository.findAll(
        new Sort(Sort.Direction.DESC, "id")
    ).get(0).getId();

    final Collection<UserRegistrationBean> beans = Lists.newArrayListWithExpectedSize(count);

    while (count-- > 0) {
      person = null;
      authorities = null;
      user = new NUser()
          .setUsername(String.format("user_%d", count))
          .setPassword("test")
          .setAccountNonLocked(count % 2 == 0)
          .setEmail(String.format("user_%d@gmail.com", count));

      if (count % 3 == 0) {
        person = new NPerson()
            .setFirstName(String.format("John %d", latestPersonId + (count == 0 ? count + 1 : count)))
            .setLastName("Doe");

        final Random random = new Random(DateTime.now().hashCode());
        final int nextInt = random.nextInt(CONTACTS_COUNT);

        subItemsProcessed = 0;
        while (subItemsProcessed != nextInt) {
          final NEnumerationEntry entry = contactTypes.get(random.nextInt(size));
          final String type = entry.getKey();
          String contact = null;

          switch (type) {
            case "SCT_FAX":
            case "SCT_CELL_PHONE":
            case "SCT_PHONE":
            case "SCT_BUSINESS_PHONE": {
              contact = String.format("%d%d%d-%d%d%d-%d%d%d", count, count, count, count, count, count, count, count, count);
            }
            break;
            case "SCT_MAIL":
              contact = String.format("user_%d@gmail_%d.com", count, count % 2);
          }

          person.addContact(
              (NPersonContact) new NPersonContact()
                  .setType(type)
                  .setContact(contact)
          );

          subItemsProcessed++;
        }
      }

      if (count % 5 != 0) {
        final int nextInt = new Random().nextInt(ROLES_COUNT);
        authorities = Sets.newHashSet();
        subItemsProcessed = 0;

        while (subItemsProcessed != nextInt) {
          final GrantedAuthority authority = (GrantedAuthority) () -> {
            int index = new Random().nextInt(allRoles.size());
            if (index < 0) {
              index = Math.abs(index);
            }
            return allRoles.get(index).getAuthority();
          };
          if (authorities.contains(authority)) {
            continue;
          }
          authorities.add(authority);
          subItemsProcessed++;
        }
      }

      final NPerson finalPerson = person;
      final Collection<GrantedAuthority> finalAuthorities = authorities;
      final NUser finalUser = user;

      beans.add(new UserRegistrationBean() {
        @Override
        public NUser getUser() {
          return finalUser;
        }

        @Override
        public NPerson getPerson() {
          return finalPerson;
        }

        @Override
        @SuppressWarnings("unchecked")
        public <GA extends GrantedAuthority> Collection<GA> getAuthorities() {
          return (Collection<GA>) finalAuthorities;
        }
      });
    }

    this.userService.registerNewUsers(beans);
  }

  private int getCount(final Random random) {
    final int abs = Math.abs(random.nextInt(MAX_VALS));
    return abs != 0 ? abs : 1;
  }

  private void loadCarMasters(int count) {
    final Collection<NCarMaster> carMasters = Lists.newArrayListWithExpectedSize(count);
    final ArrayList<CountryCode> codes = Lists.newArrayList(CountryCode.values());
    Iterator<CountryCode> countryCodes = codes.iterator();

    while (count-- > 0) {
      CountryCode next = null;
      if (!countryCodes.hasNext()) {
        countryCodes = codes.iterator();
      } else {
        next = countryCodes.next();
        if (next == CountryCode.UNDEFINED) {
          if (countryCodes.hasNext()) {
            next = countryCodes.next();
          } else {
            countryCodes = codes.iterator();
            next = countryCodes.next();
          }
        }
      }
      carMasters.add(
          new NCarMaster(String.format("Brand_%d", count), String.format("Model_%d", count))
              .setCountry(next)
              .setManufacturer(String.format("Manufactured_%d", count))
      );
    }

    this.carMasterRepository.save(carMasters);
  }

  @Override
  public int getOrder() {
    return Ordered.LOWEST_PRECEDENCE;
  }
}

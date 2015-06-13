package org.agatom.springatom.data.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.agatom.springatom.data.constraints.Password;
import org.agatom.springatom.data.constraints.UserName;
import org.agatom.springatom.data.constraints.ValidUser;
import org.agatom.springatom.data.model.person.NPerson;
import org.agatom.springatom.data.model.person.NPersonContact;
import org.agatom.springatom.data.model.user.NUser;
import org.agatom.springatom.data.model.user.NUserToPersonLink;
import org.agatom.springatom.data.model.user.authority.UserToRoleLink;
import org.agatom.springatom.data.repo.repositories.authority.NRoleRepository;
import org.agatom.springatom.data.repo.repositories.authority.NUserToRoleRepository;
import org.agatom.springatom.data.repo.repositories.person.NPersonRepository;
import org.agatom.springatom.data.repo.repositories.user.NUserRepository;
import org.agatom.springatom.data.repo.repositories.user.NUserToPersonLinkRepository;
import org.agatom.springatom.data.service.services.NEnumerationService;
import org.agatom.springatom.data.service.services.NUserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import javax.annotation.Nullable;
import java.io.InputStream;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

@Service
@Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
class UserDomainService
  implements NUserService {
  private static final Logger                      LOGGER                     = LogManager.getLogger(UserDomainService.class);
  @Autowired
  @SuppressWarnings("SpringJavaAutowiringInspection")
  private              ObjectMapper                objectMapper               = null;
  @Autowired
  private              NEnumerationService         enumerationService         = null;
  @Autowired
  private              PasswordEncoder             passwordEncoder            = null;
  @Autowired
  private              NUserRepository             userRepository             = null;
  @Autowired
  private              NPersonRepository           personRepository           = null;
  @Autowired
  private              NUserToPersonLinkRepository userToPersonLinkRepository = null;
  @Autowired
  private              NRoleRepository             roleRepository             = null;
  @Autowired
  private              NUserToRoleRepository       userToRoleRepository       = null;

  @Override
  public Collection<NUser> loadData(final InputStream stream) throws Exception {
    LOGGER.entry(stream);
    final JsonNode node = this.objectMapper.readTree(stream);
    final ArrayNode users = (ArrayNode) node.get("users");
    return LOGGER.exit(this.readAndRegister(users.iterator()));
  }

  private Collection<NUser> readAndRegister(final Iterator<JsonNode> iterator) throws Exception {
    LOGGER.entry(iterator);

    final Collection<NUser> users = Lists.newArrayList();
    while (iterator.hasNext()) {
      final JsonNode node = iterator.next();

      final NUser user = this.getUser(node);
      final NPerson person = this.getPerson(node);
      final Collection<GrantedAuthority> authorities = this.getRoles(node);

      users.add(this.registerLoadedUser(user, person, authorities));
    }

    return LOGGER.exit(users);
  }

  private NUser getUser(final JsonNode node) {
    return new NUser()
      .setUsername(node.get("login").asText())
      .setEnabled(true)
      .setPassword(node.get("password").asText())
      .setEmail(node.get("email").asText());
  }

  private NPerson getPerson(final JsonNode node) throws Exception {
    if (!node.has("person")) {
      return null;
    }
    final JsonNode personNode = node.get("person");
    final NPerson person = new NPerson();
    return person.setFirstName(personNode.get("firstName").asText())
      .setLastName(personNode.get("lastName").asText())
      .setContacts(this.getContacts(personNode));
  }

  private Collection<GrantedAuthority> getRoles(final JsonNode node) {
    final ArrayNode roles = (ArrayNode) node.get("roles");
    final Collection<GrantedAuthority> collection = Sets.newHashSetWithExpectedSize(roles.size());
    for (final JsonNode roleNode : roles) {
      collection.add(roleNode::asText);
    }
    return collection;
  }

  private NUser registerLoadedUser(final NUser user, final NPerson person, final Collection<GrantedAuthority> authorities) throws Exception {
    final boolean authoritiesEmpty = CollectionUtils.isEmpty(authorities);
    if (person != null && !authoritiesEmpty) {
      return this.registerNewUser(user, person, authorities);
    } else if (person != null) {
      return this.registerNewUser(user, person);
    } else if (!authoritiesEmpty) {
      return this.registerNewUser(user, null, authorities);
    }
    return this.registerNewUser(user);
  }

  private List<NPersonContact> getContacts(final JsonNode personNode) throws Exception {
    if (!personNode.has("contacts")) {
      return null;
    }
    final JsonNode contactsNode = personNode.get("contacts");
    final List<NPersonContact> contacts = Lists.newArrayListWithExpectedSize(contactsNode.size());
    for (final JsonNode cn : contactsNode) {
      contacts.add(this.getContact(cn));
    }
    return contacts;
  }

  private NPersonContact getContact(final JsonNode cn) throws Exception {
    final NPersonContact pc = new NPersonContact();

    pc.setContact(cn.get("value").asText());
    pc.setType(this.enumerationService.getEnumeratedValue("CONTACT_TYPES", cn.get("type").asText()));

    return pc;
  }

  @Override
  public NUser registerNewUser(@UserName final String userName, @Password final String password, @Nullable final NPerson person) throws Exception {
    return this.registerNewUser(new NUser().setUsername(userName).setPassword(password), person);
  }

  @Override
  public NUser registerNewUser(@ValidUser final NUser user) throws Exception {
    return this.registerNewUser(user, null);
  }

  @Override
  public NUser registerNewUser(@ValidUser final NUser user, @Nullable final NPerson person) throws Exception {
    return this.registerNewUser(user, person, Collections.EMPTY_LIST);
  }

  @Override
  public <GA extends GrantedAuthority> NUser registerNewUser(@ValidUser NUser user, final NPerson rawPerson, final Collection<GA> authorities) throws Exception {
    boolean authoritiesEmpty = CollectionUtils.isEmpty(authorities);

    // user logic
    user.setPassword(this.passwordEncoder.encode(user.getPassword()));
    if (authoritiesEmpty) {
      LOGGER.trace(String.format("%s has no authorities, his account will be disabled", user));
      user.setEnabled(false);
    }

    // save user for further associations
    user = this.userRepository.save(user);

    LOGGER.info("{} has been registered", user.getUsername());

    // if person not null associate with user
    if (rawPerson != null) {
      final NPerson person = this.getOrCreatePerson(rawPerson);
      Assert.isTrue(!person.isNew(), String.format("Person=%s should be persisted", person));
      final NUserToPersonLink link = this.userToPersonLinkRepository.save(new NUserToPersonLink().setPerson(person).setUser(user));
      user = link.getUser();
    }

    // if authorities not empty/null create links
    if (!authoritiesEmpty) {
      final NUser localUser = user;
      final Collection<UserToRoleLink> set = FluentIterable.from(authorities).transform(new Function<GrantedAuthority, UserToRoleLink>() {
        @Nullable
        @Override
        public UserToRoleLink apply(@Nullable final GrantedAuthority input) {
          assert input != null;
          return new UserToRoleLink(localUser, roleRepository.findByAuthority(input.getAuthority()));
        }
      }).toSet();
      this.userToRoleRepository.save(set);
    }

    return user;
  }

  @Override
  public Iterable<NUser> registerNewUsers(final Collection<UserRegistrationBean> beans) {
    return FluentIterable.from(beans)
      .transform(new Function<UserRegistrationBean, NUser>() {
        @Nullable
        @Override
        public NUser apply(@Nullable final UserRegistrationBean urb) {
          try {
            assert urb != null;
            return registerNewUser(urb.getUser(), urb.getPerson(), urb.getAuthorities());
          } catch (Exception ex) {
            LOGGER.error(String.format("Failed to register user out of bean %s urb", urb), ex);
          }
          return null;
        }
      })
      .toList();
  }

  private NPerson getOrCreatePerson(final NPerson person) throws Exception {
    if (person.isNew()) {
      return this.personRepository.save(person);
    }
    this.checkIfPersonAlreadyAssignedToUser(person);
    return this.personRepository.findOne(person.getId());
  }

  private void checkIfPersonAlreadyAssignedToUser(final NPerson person) throws Exception {
    final NUserToPersonLink userToPersonLink = this.userToPersonLinkRepository.findByPerson(person);
    final NUser one = userToPersonLink != null ? userToPersonLink.getRoleA() : null;
    if (one != null) {
      throw new Exception(String.format("%s already has user associated with it", person));
    }
  }
}

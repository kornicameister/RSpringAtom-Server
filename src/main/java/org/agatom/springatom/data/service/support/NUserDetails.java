package org.agatom.springatom.data.service.support;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.agatom.springatom.data.model.user.NUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class NUserDetails
  implements UserDetails {
  private static final long                         serialVersionUID = -5474298872680391042L;
  @JsonIgnore
  private              NUser                        user             = null;
  private              Collection<GrantedAuthority> roles            = null;

  public NUserDetails(final NUser user, final Collection<GrantedAuthority> roles) {
    this.user = user;
    this.roles = roles;
  }

  @JsonIgnore
  public NUser getUser() {
    return this.user;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return this.roles;
  }

  @Override
  public String getPassword() {
    return user.getPassword();
  }

  @Override
  public String getUsername() {
    return user.getUsername();
  }

  @Override
  public boolean isAccountNonExpired() {
    return user.isAccountNonExpired();
  }

  @Override
  public boolean isAccountNonLocked() {
    return user.isAccountNonLocked();
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return user.isCredentialsNonExpired();
  }

  @Override
  public boolean isEnabled() {
    return user.isEnabled();
  }
}

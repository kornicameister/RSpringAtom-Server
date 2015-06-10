package org.agatom.springatom.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.agatom.springatom.security.core.AuthenticationService;
import org.agatom.springatom.security.support.AjaxHttpFirewall;
import org.agatom.springatom.security.support.TokenAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityAuthorizeMode;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.security.web.header.HeaderWriterFilter;

import java.util.ArrayList;
import java.util.List;

@Configuration
@ComponentScan(
  basePackageClasses = SecurityConfiguration.class,
  excludeFilters = @ComponentScan.Filter(value = Configuration.class)
)
@EnableWebSecurity
@EnableWebMvcSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration {

  @Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
  @Configuration
  public static class RestWebSecurityConfigurationAdapter
    extends WebSecurityConfigurerAdapter {
    @Autowired
    private SSecurityProperties   sSecurity;
    @Autowired
    private SecurityProperties    security;
    @Autowired
    private ObjectMapper          objectMapper;
    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private AjaxHttpFirewall      ajaxHttpFirewall;

    @Override
    public void configure(final WebSecurity web) throws Exception {
      web.httpFirewall(this.ajaxHttpFirewall);
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
      this.configurePaths(http);
      this.configureAuthorizeMode(http);

      http.addFilterAfter(this.tokenAuthenticationFilter(), HeaderWriterFilter.class);
    }

    private void configurePaths(final HttpSecurity http) throws Exception {
      final String[] paths = this.getSecureApplicationPaths();
      if (paths.length > 0) {
        final AuthenticationEntryPoint entryPoint = this.entryPoint();

        http.exceptionHandling().authenticationEntryPoint(entryPoint);
        http.httpBasic().authenticationEntryPoint(entryPoint);
        http.authorizeRequests().antMatchers(paths).authenticated();

      }
    }

    private void configureAuthorizeMode(final HttpSecurity http) throws Exception {
      final List<String> role = this.security.getUser().getRole();
      final String[] roles = role.toArray(new String[role.size()]);
      final SecurityAuthorizeMode mode = this.security.getBasic().getAuthorizeMode();

      if (mode == null || mode == SecurityAuthorizeMode.ROLE) {
        http.authorizeRequests().anyRequest().hasAnyRole(roles);
      } else if (mode == SecurityAuthorizeMode.AUTHENTICATED) {
        http.authorizeRequests().anyRequest().authenticated();
      }
    }

    private TokenAuthenticationFilter tokenAuthenticationFilter() {
      final TokenAuthenticationFilter filter = new TokenAuthenticationFilter();

      filter.setAuthenticationService(this.authenticationService)
        .setObjectMapper(this.objectMapper);
      this.sSecurity.configureTokenFilter(filter);

      return filter;
    }

    private String[] getSecureApplicationPaths() {
      List<String> list = new ArrayList<String>();
      for (String path : this.security.getBasic().getPath()) {
        path = (path == null ? "" : path.trim());
        if (path.equals("/**")) {
          return new String[]{path};
        }
        if (!path.equals("")) {
          list.add(path);
        }
      }
      return list.toArray(new String[list.size()]);
    }

    private AuthenticationEntryPoint entryPoint() {
      BasicAuthenticationEntryPoint entryPoint = new BasicAuthenticationEntryPoint();
      entryPoint.setRealmName(this.security.getBasic().getRealm());
      return entryPoint;
    }

  }

}

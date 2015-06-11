package org.agatom.springatom.security;

import com.google.common.collect.Lists;
import org.agatom.springatom.security.core.AuthenticationService;
import org.agatom.springatom.security.core.TokenService;
import org.agatom.springatom.security.support.AjaxHttpFirewall;
import org.agatom.springatom.security.support.TokenAuthenticationFilter;
import org.agatom.springatom.security.token.TokenInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.MarkerManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityAuthorizeMode;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

@Configuration
@ComponentScan(
    basePackageClasses = SecurityConfiguration.class,
    excludeFilters = @ComponentScan.Filter(value = Configuration.class)
)
@EnableWebSecurity
@EnableWebMvcSecurity
@EnableGlobalMethodSecurity(securedEnabled = false, mode = AdviceMode.ASPECTJ)
public class SecurityConfiguration {
  private static final Logger LOGGER = LogManager.getLogger(SecurityConfiguration.class);

  @Configuration
  public static class RestWebSecurityConfigurationAdapter
      extends WebSecurityConfigurerAdapter {
    @Autowired
    private SSecurityProperties   sSecurity;
    @Autowired
    private SecurityProperties    security;
    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private AjaxHttpFirewall      ajaxHttpFirewall;
    @Autowired
    private TokenService          tokenManager;

    @Bean
    public FilterRegistrationBean tokenAuthenticationFilter() {
      final FilterRegistrationBean frb = new FilterRegistrationBean(
          this.getTokenAuthenticationFilter()
      );

      frb.setMatchAfter(true);
      frb.setOrder(100);
      frb.setUrlPatterns(Arrays.asList(this.getSecureApplicationPaths()));
      frb.setName("tokenAuthenticationFilter");

      return frb;
    }

    private TokenAuthenticationFilter getTokenAuthenticationFilter() {
      final TokenAuthenticationFilter filter = new TokenAuthenticationFilter();

      filter.setAuthenticationService(this.authenticationService);
      this.sSecurity.configureTokenFilter(filter);

      return filter;
    }

    private String[] getSecureApplicationPaths() {
      final List<String> list = Lists.newArrayList();
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

    @Override
    public void configure(final WebSecurity web) throws Exception {
      web.httpFirewall(this.ajaxHttpFirewall);
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
      this.configurePaths(http);
      this.configureAuthorizeMode(http);
      this.configureAjaxAuthentication(http);
      this.configureCSFR(http);
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

    private void configureAjaxAuthentication(final HttpSecurity http) throws Exception {
      final SSecurityProperties.Token token = this.sSecurity.getToken();
      final SSecurityProperties.Paths paths = this.sSecurity.getPaths();

      http.formLogin()
          .loginPage(paths.getLogin())
          .loginProcessingUrl(paths.getProcessing())
          .usernameParameter(token.getHeaderUsername())
          .passwordParameter(token.getHeaderPassword())
          .successHandler((request, response, authentication) -> {
            final UserDetails us = (UserDetails) authentication.getPrincipal();
            try {
              final TokenInfo tokenInfo = this.tokenManager.createNewToken(us);
              final String headerToken = token.getHeaderToken();

              response.setStatus(HttpServletResponse.SC_OK);
              response.setHeader(headerToken, tokenInfo.getToken());
              response.setDateHeader(String.format("%s-Expires", headerToken), tokenInfo.getExpiresAt().getMillis());

            } catch (Exception exp) {
              LOGGER.error(MarkerManager.getMarker("Token"), "Failed to generate new token", exp);
              response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, exp.getMessage());
            }
          })
          .permitAll();
    }

    private void configureCSFR(final HttpSecurity http) throws Exception {
      http.csrf().disable();
    }

    private AuthenticationEntryPoint entryPoint() {
      BasicAuthenticationEntryPoint entryPoint = new BasicAuthenticationEntryPoint();
      entryPoint.setRealmName(this.security.getBasic().getRealm());
      return entryPoint;
    }

  }

}

package org.agatom.springatom;

import org.agatom.springatom.data.DataConfiguration;
import org.agatom.springatom.data.loader.mgr.DataLoaderManager;
import org.agatom.springatom.mvc.MVCConfiguration;
import org.agatom.springatom.security.SecurityConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.guava.GuavaCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;

import javax.annotation.PostConstruct;

@EnableAsync
@EnableCaching
@EnableAutoConfiguration
@EnableConfigurationProperties
@Import({
  SecurityConfiguration.class,
  DataConfiguration.class,
  MVCConfiguration.class
})
public class SpringAtomApplication {
  @Autowired
  private DataLoaderManager dataLoaderManager = null;

  public static void main(String[] args) {
    SpringApplication.run(SpringAtomApplication.class, args);
  }

  @Bean
  public CacheManager cacheManager() {
    final GuavaCacheManager manager = new GuavaCacheManager();
    manager.setAllowNullValues(false);
    return manager;
  }

  @PostConstruct
  private void doPostConstruct() {
    this.dataLoaderManager.doLoad();
  }
}

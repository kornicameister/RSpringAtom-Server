package org.agatom.springatom;

import org.agatom.springatom.data.DataConfiguration;
import org.agatom.springatom.data.loader.DataLoaderConfiguration;
import org.agatom.springatom.data.loader.mgr.DataLoaderManager;
import org.agatom.springatom.data.repo.RepositoryConfiguration;
import org.agatom.springatom.data.service.ServicesConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.guava.GuavaCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;

import javax.annotation.PostConstruct;

@EnableAsync
@EnableCaching
@SpringBootApplication
@EnableConfigurationProperties
@Import(DataConfiguration.class)
public class SpringAtomApplication {
  @Autowired
  private DataLoaderManager dataLoaderManager = null;

  public static void main(String[] args) {
    SpringApplication.run(SpringAtomApplication.class, args);
  }

  @Bean
  public CacheManager cacheManager(){
    return new GuavaCacheManager();
  }

  @PostConstruct
  private void doPostConstruct() {
    this.dataLoaderManager.doLoad();
  }
}

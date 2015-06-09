package org.agatom.springatom.data.loader;

import org.agatom.springatom.data.loader.mgr.DataLoaderManager;
import org.agatom.springatom.data.loader.properties.DataLoaderProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;


@Lazy(false)
@Configuration
@ComponentScan(
  basePackages = {
    "org.agatom.springatom.data.loader.loaders",
    "org.agatom.springatom.data.loader.properties"
  }
)
public class DataLoaderConfiguration {

  @Autowired
  private DataLoaderProperties dataLoaderProperties = null;

  @Bean
  public DataLoaderManager dataLoaderManager() {
    if (this.dataLoaderProperties.isEnabled()) {
      return new ActiveLoaderManager();
    }
    return new NoopLoaderManager();
  }

}

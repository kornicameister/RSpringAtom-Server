package org.agatom.springatom.data;


import org.agatom.springatom.data.listeners.ListenersConfiguration;
import org.agatom.springatom.data.loader.DataLoaderConfiguration;
import org.agatom.springatom.data.repo.RepositoryConfiguration;
import org.agatom.springatom.data.service.ServicesConfiguration;
import org.agatom.springatom.data.vin.VinNumberConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(
  value = {
    RepositoryConfiguration.class,
    ServicesConfiguration.class,
    DataLoaderConfiguration.class,
    ListenersConfiguration.class,
    VinNumberConfiguration.class
  }
)
public class DataConfiguration {
}

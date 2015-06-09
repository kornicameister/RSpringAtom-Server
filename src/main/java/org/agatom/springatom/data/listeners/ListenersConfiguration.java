package org.agatom.springatom.data.listeners;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(
  basePackageClasses = ListenersConfiguration.class,
  excludeFilters = {
    @ComponentScan.Filter(value = Configuration.class)
  }
)
public class ListenersConfiguration {
}

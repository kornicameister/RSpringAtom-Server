package org.agatom.springatom.data.vin;

import org.agatom.springatom.data.vin.validator.VinValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.event.ValidatingRepositoryEventListener;

import javax.annotation.PostConstruct;

@Configuration
@ComponentScan(
  basePackageClasses = VinNumberConfiguration.class
)
public class VinNumberConfiguration {
  @Autowired
  @SuppressWarnings("SpringJavaAutowiringInspection")
  private ValidatingRepositoryEventListener validatingRepositoryEventListener;
  @Autowired
  private VinValidator                      vinValidator;

  @PostConstruct
  private void setUpValidator() {
    this.validatingRepositoryEventListener.addValidator("beforeSave", this.vinValidator);
    this.validatingRepositoryEventListener.addValidator("beforeCreate", this.vinValidator);
  }

}

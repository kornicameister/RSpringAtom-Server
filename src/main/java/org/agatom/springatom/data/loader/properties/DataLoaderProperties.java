package org.agatom.springatom.data.loader.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "sa.data.loader")
public class DataLoaderProperties {
  private boolean enabled;

  public boolean isEnabled() {
    return enabled;
  }

  public DataLoaderProperties setEnabled(final boolean enabled) {
    this.enabled = enabled;
    return this;
  }
}

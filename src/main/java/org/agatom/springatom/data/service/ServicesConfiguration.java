package org.agatom.springatom.data.service;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {
  "org.agatom.springatom.data.service.impl"
})
public class ServicesConfiguration {
}

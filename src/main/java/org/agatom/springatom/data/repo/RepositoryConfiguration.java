package org.agatom.springatom.data.repo;

import org.agatom.springatom.data.repo.core.factory.NRepositoriesFactoryBean;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

@Configuration
@ComponentScan(
  excludeFilters = {
    @ComponentScan.Filter(value = Repository.class),
    @ComponentScan.Filter(value = RepositoryRestResource.class),
    @ComponentScan.Filter(value = RestResource.class),
  }
)
@EnableJpaRepositories(
  basePackages = "org.agatom.springatom.data.repo.repositories",
  considerNestedRepositories = true,
  repositoryFactoryBeanClass = NRepositoriesFactoryBean.class
)
@EnableJpaAuditing(
  modifyOnCreate = true,
  setDates = true,
  auditorAwareRef = "auditDomainService"
)
public class RepositoryConfiguration {
}

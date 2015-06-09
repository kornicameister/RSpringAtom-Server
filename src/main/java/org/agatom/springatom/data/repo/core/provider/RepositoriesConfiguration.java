package org.agatom.springatom.data.repo.core.provider;

import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.*;


@Configuration
public class RepositoriesConfiguration {
  @Autowired
  private ListableBeanFactory beanFactory = null;

  @Bean
  @Role(BeanDefinition.ROLE_SUPPORT)
  @Scope(BeanDefinition.SCOPE_SINGLETON)
  @Description("RepositoryProvider allows to retrieve repositories")
  public RepositoriesHelper repositoriesHelper() {
    return new RepositoriesHelperImpl(this.beanFactory);
  }

}

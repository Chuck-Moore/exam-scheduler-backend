package ca.fraseric.examscheduler.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.core.event.ValidatingRepositoryEventListener;
import org.springframework.data.rest.core.mapping.RepositoryDetectionStrategy.RepositoryDetectionStrategies;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@Configuration
class RepoConfig implements RepositoryRestConfigurer {

  private final LocalValidatorFactoryBean beanValidator;

  public RepoConfig(LocalValidatorFactoryBean beanValidator) {
    this.beanValidator = beanValidator;
  }

  @Override
  public void configureValidatingRepositoryEventListener(
    ValidatingRepositoryEventListener v
  ) {
    v.addValidator("beforeCreate", beanValidator);
    v.addValidator("beforeSave", beanValidator);
    RepositoryRestConfigurer.super.configureValidatingRepositoryEventListener(
      v
    );
  }

  @Bean
  public RepositoryRestConfigurer repositoryRestConfigurer() {
    return new RepositoryRestConfigurer() {
      @Override
      public void configureRepositoryRestConfiguration(
        RepositoryRestConfiguration config,
        CorsRegistry cors
      ) {
        config.setRepositoryDetectionStrategy(
          RepositoryDetectionStrategies.ANNOTATED
        );
      }
    };
  }
}

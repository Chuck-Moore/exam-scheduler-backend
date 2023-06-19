package ca.fraseric.examscheduler.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Configuration applied on all web endpoints defined for this
 * application. Any configuration on specific resources is applied
 * in addition to these global rules.
 */
@Configuration
@EnableWebSecurity
@EnableWebMvc
class SecurityConfig {

  /**
   * Configures basic security handler per HTTP session.
   * <p>
   * <ul>
   * <li>Stateless session (no session kept server-side)</li>
   * <li>CORS set up</li>
   * <li>JWT converted into Spring token</li>
   * </ul>
   *
   * @param http security configuration
   * @throws Exception any error
   */
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

    http.sessionManagement(smc -> {
      smc.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    });

    http.authorizeHttpRequests(authz -> authz
      .requestMatchers("/schedule/**").hasAuthority("SCOPE_schedule:read")
      .requestMatchers("/admin/**").hasRole("ADMIN")
      .anyRequest().authenticated()
    );

    http.oauth2ResourceServer(oauth2 -> oauth2
      .opaqueToken() // TODO: setup oauth2 token auto conversion
    );

    return http.build();
  }

}

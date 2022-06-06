package com.thesi.jwtsec.config;

import com.thesi.jwtsec.filter.CustomAuthenFilter;
import com.thesi.jwtsec.filter.CustomAuthorFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
  private final UserDetailsService userDetailsService;
  private final BCryptPasswordEncoder bCryptPasswordEncoder;

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    CustomAuthenFilter customAuthenFilter = new CustomAuthenFilter(authenticationManagerBean());
    customAuthenFilter.setFilterProcessesUrl("/api/login");
    http.csrf().disable();
    http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    http.authorizeHttpRequests().antMatchers("/api/login/**").permitAll();

    http.authorizeHttpRequests().antMatchers(GET, "/api/users/**").hasAnyAuthority("ROLE_USER");
    http.authorizeHttpRequests().antMatchers(GET, "/api/admin/**").hasAnyAuthority("ROLE_ADMIN");
    http.authorizeHttpRequests().antMatchers(POST, "/api/users/**").hasAnyAuthority("ROLE_ADMIN");

    http.authorizeHttpRequests().anyRequest().authenticated();
    http.addFilter(customAuthenFilter);
    http.addFilterBefore(new CustomAuthorFilter(), UsernamePasswordAuthenticationFilter.class);
  }

  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }
}

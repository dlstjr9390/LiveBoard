package com.example.liveboard.global.config;

import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Override
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

    http.csrf(csrf -> csrf.disable());
    http.oauth2Login()
        .loginPage("/loginPage")
        .defaultSuccessUrl("/index", true);


    return http.build();
  }
}

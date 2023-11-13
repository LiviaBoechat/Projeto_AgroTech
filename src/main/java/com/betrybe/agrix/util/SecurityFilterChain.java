//package com.betrybe.agrix.util;
//
//import javax.ws.rs.HttpMethod;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
//import org.springframework.security.config.http.SessionCreationPolicy;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityFilterChain {
//
//  /**
//   * SecurityConfig.
//   */
//  @Bean
//  public org.springframework.security.web.SecurityFilterChain securityFilterChain(
//      HttpSecurity httpSecurity) throws Exception {
//    return httpSecurity
//        .csrf(AbstractHttpConfigurer::disable)
//        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy
//            .STATELESS))
//        .authorizeHttpRequests(authorize -> authorize
//            .requestMatchers(HttpMethod.POST, "/persons").permitAll()
//            .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
//            .anyRequest().authenticated()
//        )
//        .build();
//  }
//
//}

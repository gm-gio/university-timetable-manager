package com.java.course.universitytimetablemanager.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain userFilterChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests(
                        authorizeRequests -> authorizeRequests
                                .requestMatchers("/user/register", "/user/login", "/error").permitAll()
                                .requestMatchers("/css/**", "/js/**", "/webjars/**", "/styles/**", "/assets/**").permitAll()
                                .requestMatchers("/admin/**")
                                .hasAnyAuthority("ADMIN")

                                .anyRequest().authenticated()
                ).httpBasic(Customizer.withDefaults())

                .formLogin(login -> login.loginPage("/user/login").permitAll())

                .logout(logout -> logout.logoutUrl("/logout").logoutSuccessUrl("/user/login").permitAll()
                        .clearAuthentication(true)
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID"))

                .build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

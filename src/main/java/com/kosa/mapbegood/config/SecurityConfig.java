package com.kosa.mapbegood.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//                .headers().frameOptions().sameOrigin()
//                .and()
//                .csrf().disable()
//                .formLogin()
//                .loginPage("/auths/login-form")
//                .loginProcessingUrl("/login")
//                .failureUrl("/auths/login-form?error")
//                .and()
//                .logout()
//                .logoutUrl("/logout")
//                .logoutSuccessUrl("/")
//                .and()
//                .exceptionHandling().accessDeniedPage("/auths/access-denied")
//                .and()
//                .authorizeHttpRequests(authorize -> authorize
//                        .antMatchers("/orders/**").hasRole("ADMIN")
//                        .antMatchers("/members/my-page").hasRole("USER")
//                        .antMatchers("⁄**").permitAll()
//                );
//                   .authorizeHttpRequests()
//                   .anyRequest()
//                   .permitAll();

//        return http.build();
//    }

}

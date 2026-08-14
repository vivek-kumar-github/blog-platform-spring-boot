package com.blogplatform.simple_blog_platform.config;

import org.springframework.http.HttpStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CsrfException;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**"))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/posts/*/comments").hasAnyRole("USER", "ADMIN")
                        .requestMatchers(HttpMethod.GET, "/", "/posts", "/posts/**").permitAll()
                        .requestMatchers("/register", "/login", "/css/**", "/js/**", "/error", "/error/**", "/h2-console/**").permitAll()
                        .anyRequest().authenticated())
                .formLogin(formLogin -> formLogin.loginPage("/login").permitAll())
                .logout(logout -> logout
                        .permitAll()
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .deleteCookies("JSESSIONID"))
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint((request, response, authException) -> {
                            String contextPath = request.getContextPath();
                            String requestUri = request.getRequestURI();

                            if ("POST".equalsIgnoreCase(request.getMethod()) && requestUri.matches(".*/posts/\\d+/comments")) {
                                response.setStatus(HttpStatus.FORBIDDEN.value());
                                response.sendRedirect(contextPath + "/error/403?expired");
                                return;
                            }

                            response.sendRedirect(contextPath + "/login");
                        })
                        .accessDeniedHandler((request, response, accessDeniedException) -> {
                            String contextPath = request.getContextPath();
                            if (accessDeniedException instanceof CsrfException) {
                                response.setStatus(HttpStatus.FORBIDDEN.value());
                                response.sendRedirect(contextPath + "/error/403?expired");
                            } else {
                                response.setStatus(HttpStatus.FORBIDDEN.value());
                                response.sendRedirect(contextPath + "/error/403");
                            }
                        }))
                .headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()));
        return http.build();
    }
}

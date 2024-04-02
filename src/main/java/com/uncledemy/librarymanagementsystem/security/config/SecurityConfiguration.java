package com.uncledemy.salesmanagementsystem.security.config;


import ch.qos.logback.core.net.SocketConnector;
import com.uncledemy.salesmanagementsystem.security.CsrfCookieFilter;
import com.uncledemy.salesmanagementsystem.security.JwtAuthenticationFilter;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Slf4j
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authProvider;
    private final String[] NO_AUTH_ROUTES = {
            "/api/v1/user/create","/api/v1/auth/login"
    };
    private final String[] CSRF_ROUTES = {
            "/api/v1/user/create","/api/v1/auth/login","/api/v1/product/create",
            "/api/v1/product/update","/api/v1/client/create","/api/v1/client/update",
            "/api/v1/sales/create","/api/v1/sales/update"

    };
    private final String[] AUTH_ROUTES = {
            "/api/v1/product/create","/api/v1/product/product","/api/v1/product/update",
            "/api/v1/product/allProducts","/api/v1/product/delete","/api/v1/client/create",
            "/api/v1/client/client","/api/v1/client/update","/api/v1/client/allClients",
            "/api/v1/client/delete","/api/v1/sales/create","/api/v1/sales/sales",
            "/api/v1/sales/update","/api/v1/sales/allTransactions"
    };
    private final String[] ADMIN_ROUTES = {
            "/api/v1/sales/reports/sales","/api/v1/client/reports/client",
            "/api/v1/product/reports/product"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        CsrfTokenRequestAttributeHandler requestHandler = new CsrfTokenRequestAttributeHandler();
        requestHandler.setCsrfRequestAttributeName("_csrf");
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .cors(corsCustomizer -> corsCustomizer.configurationSource(request -> {
                    CorsConfiguration config = new CorsConfiguration();
                    config.setAllowedOrigins(Collections.singletonList("http://localhost:9090"));
                    config.setAllowedMethods(Collections.singletonList("*"));
                    config.setAllowCredentials(true);
                    config.setAllowedHeaders(Collections.singletonList("*"));
                    config.setExposedHeaders(List.of("Authorization"));
                    config.setMaxAge(3600L);
                    return config;
                })).csrf((csrf) -> csrf.csrfTokenRequestHandler(requestHandler)
                        .ignoringRequestMatchers(CSRF_ROUTES)
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
                .authenticationProvider(authProvider)
                .addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests((requests)->requests
                        .requestMatchers(NO_AUTH_ROUTES).permitAll()
                        .requestMatchers(AUTH_ROUTES).hasAnyAuthority("ADMIN","SELLER")
                        .requestMatchers(ADMIN_ROUTES).hasAuthority("ADMIN"));
        return  http.build();

    }
//    @Bean
//    public WebSecurityCustomizer webSecurityCustomizer(){
//        return (web -> web.ignoring().anyRequest());
//    }

}

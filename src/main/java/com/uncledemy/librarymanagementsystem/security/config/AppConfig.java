package com.uncledemy.salesmanagementsystem.security.config;

import com.uncledemy.salesmanagementsystem.auditing.ApplicationAudit;
import com.uncledemy.salesmanagementsystem.exception.InvalidUsernameException;
import com.uncledemy.salesmanagementsystem.model.User;
import com.uncledemy.salesmanagementsystem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class AppConfig {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    @Bean
    public UserDetailsService userDetailsService(){
        return email -> {
            try{
                User user = userService.loadUser(email);
                return new SecureUser(user);
            } catch (InvalidUsernameException e) {
                throw new RuntimeException(e);
            }
        };
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder);
        return authProvider;
    }

    @Bean
    public AuditorAware<Long> auditorAware(){
        return new ApplicationAudit();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}

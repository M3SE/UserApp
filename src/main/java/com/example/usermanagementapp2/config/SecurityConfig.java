package com.example.usermanagementapp2.config;

import com.example.usermanagementapp2.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;

    public SecurityConfig(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/api/users/register").permitAll()  // Allow registration
                        .requestMatchers("/api/users/me").authenticated()    // Allow authenticated users to view their own details
                        .requestMatchers("/api/users").hasRole("ADMIN")   // Only admins can manage users
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults());  // Use Basic Authentication (updated to remove deprecation warning)
        return http.build();
    }

    @Bean
    public UserDetailsService inMemoryUserDetailsService() {
        // Admin user defined in-memory
        UserDetails admin = User.withUsername("admin")
                .password(passwordEncoder().encode("admin123"))  // Admin's password encoded for security
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(admin);
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(customUserDetailsService);  // CustomUserDetailsService for DB users
        authProvider.setPasswordEncoder(passwordEncoder());  // BCryptPasswordEncoder for both
        return authProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();  // Standard password encoder for both DB and in-memory users
    }
}

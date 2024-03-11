package com.fsd.librarymanagement.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private DataSource dataSource; // Injects the DataSource for database interactions
    private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class); // Logger for logging purposes


    @Bean
    public UserDetailsService userDetailService() {
        logger.info("Configuring UserDetailsService");

        // Creates a UserDetailsManager with JDBC
        JdbcUserDetailsManager manager = new JdbcUserDetailsManager(dataSource);

        // SQL query to fetch user details by username
        manager.setUsersByUsernameQuery(
                "select username, password, enabled from user where username = ?"
        );

        // SQL query to fetch user roles by username
        manager.setAuthoritiesByUsernameQuery(
                "SELECT u.username, r.name FROM user u INNER JOIN user_role ur ON u.id = ur.user_id " +
                        "INNER JOIN role r ON ur.role_id = r.id WHERE u.username = ?");
        return manager;// Returns the configured UserDetailsManager
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // Sets up BCrypt as the password encoder
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        logger.info("Configuring HTTP security");
        http
                .cors(CorsConfigurer::disable)// Disables CORS (Cross-Origin Resource Sharing)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/").hasRole("EMPLOYEE") // Restricts access to root path to EMPLOYEE role
                        .requestMatchers("/leaders/**").hasRole("MANAGER")
                        .requestMatchers("/users/**").hasRole("MANAGER") // Restricts access to /leaders/** path to MANAGER role
                        .requestMatchers("/books/**").hasAnyRole("ADMIN","MANAGER")   // Restricts access to /systems/** path to ADMIN role
                        .anyRequest().authenticated()                        // Ensures all other requests are authenticated
                )
                .formLogin(form -> form
                        .loginPage("/showLoginPage")// Custom login page
                        .loginProcessingUrl("/authenticateTheUser") // URL to process the login
                        .permitAll()// Allows everyone to access the login page
                )
                .logout(LogoutConfigurer::permitAll)// Enables logout for everyone
                .exceptionHandling(auth->
                        auth.accessDeniedPage("/access-denied")); // Custom access denied page
        return http.build();// Builds the security filter chain
    }

}

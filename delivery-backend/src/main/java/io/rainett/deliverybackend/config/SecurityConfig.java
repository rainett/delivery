package io.rainett.deliverybackend.config;

import io.rainett.deliverybackend.config.auth.entrypoint.UserAuthEntryPoint;
import io.rainett.deliverybackend.config.auth.provider.UserAuthProvider;
import io.rainett.deliverybackend.config.filter.JwtAuthFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

/**
 * Configures {@link SecurityFilterChain} providing: exception handling, filter before, csrf,
 * session management, http requests authorization.
 * Requires: {@link UserAuthProvider} and {@link UserAuthEntryPoint}
 */
@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

    private final UserAuthProvider authProvider;
    private final UserAuthEntryPoint authEntryPoint;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .exceptionHandling(c -> c.authenticationEntryPoint(authEntryPoint))
                .addFilterBefore(new JwtAuthFilter(authProvider), BasicAuthenticationFilter.class)
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(c -> c.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(r -> r
                        .requestMatchers(HttpMethod.POST,
                                "/api/v1/auth/login",
                                "/api/v1/auth/register").permitAll()
                        .anyRequest().authenticated());
        return http.build();
    }

}

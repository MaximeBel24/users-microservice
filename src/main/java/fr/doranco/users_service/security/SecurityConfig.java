package fr.doranco.users_service.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Collections;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    AuthenticationManager authMgr;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf(AbstractHttpConfigurer::disable).cors(
                        cors -> cors.configurationSource(request -> {
                            CorsConfiguration cors1 = new CorsConfiguration();

                            cors1.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
                            cors1.setAllowedMethods(Collections.singletonList("*"));
                            cors1.setAllowCredentials(true);
                            cors1.setAllowedHeaders(Collections.singletonList("*"));
                            cors1.setExposedHeaders(Collections.singletonList("Authorization"));
                            cors1.setMaxAge(3600L);

                            return cors1;
                        })
                )
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers("/login","/register/**","/verifEmail/**").permitAll()
                        .requestMatchers("/all").hasAuthority("ADMIN")
                .anyRequest().authenticated())
                .addFilterBefore(new JWTAuthenticationFilter (authMgr),
                        UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new JWTAuthorizationFilter(),
                        UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}

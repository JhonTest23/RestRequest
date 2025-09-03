package co.com.pragmarestreq.api.config;

import co.com.pragmarestreq.api.Jwt.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)   // disable Basic Auth
                .formLogin(ServerHttpSecurity.FormLoginSpec::disable)   // disable form login
                .logout(ServerHttpSecurity.LogoutSpec::disable)         // disable logout
                .authorizeExchange(exchange -> exchange
                        .pathMatchers(HttpMethod.POST,"/api/v1/solicitud").hasAnyAuthority("ROLE_CLIENTE")
                        .pathMatchers(HttpMethod.GET, "/api/v1/solicitud").hasAnyAuthority("ROLE_ASESOR")
                        .pathMatchers(
                                "/swagger-ui.html",
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/webjars/**"
                        ).permitAll()
                        .anyExchange().authenticated()
                )
                .addFilterAt(jwtAuthenticationFilter, SecurityWebFiltersOrder.AUTHENTICATION)
                .build();
    }
}

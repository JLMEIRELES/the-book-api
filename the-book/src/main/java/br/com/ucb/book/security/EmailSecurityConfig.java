package br.com.ucb.book.security;

import br.com.ucb.book.infrastructure.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

@Order(2)
@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
public class EmailSecurityConfig {

    private final JwtService jwtService;

    @Bean
    public SecurityFilterChain emailFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
        http.authorizeHttpRequests(auth ->
                auth.requestMatchers("/api/usuario/confirma-email/**")
                        .authenticated()
        );
        http.oauth2ResourceServer(oauth2 ->
                oauth2.jwt(jwt -> jwt
                        .decoder(emailDecoder()))
        );
        http.securityMatcher("/api/usuario/confirma-email/**");

        http.exceptionHandling(ex -> ex
                .authenticationEntryPoint((request,
                                           response,
                                           authException) -> response
                        .setStatus(HttpStatus.UNAUTHORIZED.value()))
        );
        return http.build();
    }

    @Bean
    public JwtDecoder emailDecoder(){
        return NimbusJwtDecoder.withSecretKey(jwtService.getEmailKey()).build();
    }
}

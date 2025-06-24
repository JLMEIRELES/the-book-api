package br.com.ucb.book.security;

import br.com.ucb.book.infrastructure.adapter.UsuarioJpaAdapter;
import br.com.ucb.book.infrastructure.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;

@Order(1)
@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {

    private final UsuarioJpaAdapter usuarioJpaAdapter;

    private final JwtService jwtService;

    private final PasswordEncoderConfig passwordEncoderConfig;

    private final CorsAccessConfiguration corsConfiguration;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.cors(cors ->
                cors.configurationSource(request -> corsConfiguration.corsConfiguration())
        );

        http.csrf(AbstractHttpConfigurer::disable);
        http.authorizeHttpRequests(auth ->
                auth.requestMatchers("/api/**")
                        .authenticated()
        );
        http.oauth2ResourceServer(oauth2 ->
                oauth2.jwt(jwt -> jwt
                        .decoder(jwtDecoder()))
        );
        http.securityMatcher("/api/**");

        http.exceptionHandling(ex -> ex
                .authenticationEntryPoint((request,
                                           response,
                                           authException) -> response
                        .setStatus(HttpStatus.UNAUTHORIZED.value()))
        );
        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(usuarioJpaAdapter);
        authenticationProvider.setPasswordEncoder(passwordEncoderConfig.passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public JwtDecoder jwtDecoder(){
        return NimbusJwtDecoder.withSecretKey(jwtService.getKey()).build();
    }
}

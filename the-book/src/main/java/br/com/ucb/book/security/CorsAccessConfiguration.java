package br.com.ucb.book.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

@Configuration
public class CorsAccessConfiguration {

    @Bean
    CorsConfiguration corsConfiguration() {
        CorsConfiguration config = new org.springframework.web.cors.CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:4200"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*")); // ou: List.of("Content-Type", "Authorization")
        config.setAllowCredentials(true); // se estiver usando cookies ou Authorization

        return config;

    }
}

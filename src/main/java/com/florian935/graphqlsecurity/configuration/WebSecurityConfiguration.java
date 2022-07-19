package com.florian935.graphqlsecurity.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.server.SecurityWebFilterChain;

import java.util.Map;

@EnableReactiveMethodSecurity
public class WebSecurityConfiguration {

    @Bean
    SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity httpSecurity) {
        return httpSecurity
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(authorizeExchangeSpec -> authorizeExchangeSpec.anyExchange().authenticated())
                .httpBasic(Customizer.withDefaults())
                .build();
    }

    @Bean
    MapReactiveUserDetailsService reactiveUserDetailsService() {
        final var users = Map.of(
                "john", new String[]{"USER"},
                "doe", new String[]{"USER", "ADMIN"})
                .entrySet()
                .stream()
                .map(entry -> User.withDefaultPasswordEncoder()
                        .username(entry.getKey())
                        .password("pw")
                        .roles(entry.getValue()).build())
                .toList();

        return new MapReactiveUserDetailsService(users);
    }
}

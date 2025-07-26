package com.fiap.foodcore.infrastructure.security;

import com.fiap.foodcore.infrastructure.mapper.UserEntityMapper;
import com.fiap.foodcore.infrastructure.web.controller.handler.CustomAccessDeniedHandler;
import com.fiap.foodcore.infrastructure.web.controller.handler.CustomAuthenticationEntryPoint;
import com.fiap.foodcore.domain.User;
import com.fiap.foodcore.infrastructure.gateways.persistence.entity.UserEntity;
import com.fiap.foodcore.infrastructure.gateways.persistence.UserRepository;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private FilterToken filterToken;

    @Bean
    public PasswordEncoder encrypt(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain SecurityFilters(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(req -> {
                    req.requestMatchers(
                            "/v3/api-docs/**",
                            "/swagger-ui/**",
                            "/swagger-ui.html"
                    ).permitAll();
                    req.requestMatchers("/login").permitAll();
                    req.requestMatchers(HttpMethod.POST, "/usuarios").permitAll();
                    req.requestMatchers(HttpMethod.DELETE, "/usuarios/*");
                    req.requestMatchers(HttpMethod.GET, "/restaurantes/*").permitAll();
                    req.requestMatchers(HttpMethod.POST, "/restaurantes/*");
                    req.requestMatchers(HttpMethod.PUT, "/restaurantes/*");
                    req.requestMatchers(HttpMethod.DELETE, "/restaurantes/*");
                    req.anyRequest().authenticated();
                })
                .exceptionHandling(ex -> ex
                        .accessDeniedHandler(customAccessDeniedHandler())
                        .authenticationEntryPoint(customAuthenticationEntryPoint())
                )
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf(csrf -> csrf.disable())
                .addFilterBefore(filterToken, UsernamePasswordAuthenticationFilter.class)
                .build();
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository repository) {
        return username -> {
            UserEntity entity = repository.findByLoginIgnoreCase(username)
                    .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

            User user = UserEntityMapper.toDomain(entity);
            return new UserDetailsAdapter(user);

        };
    }

    @Bean
    public AccessDeniedHandler customAccessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }

    @Bean
    public AuthenticationEntryPoint customAuthenticationEntryPoint() {
        return new CustomAuthenticationEntryPoint();
    }

    @Bean
    public OpenAPI openAPIWithJWT() {
        final String schemeName = "bearerAuth";

        return new OpenAPI()
                .info(new Info()
                        .title("Foodcore")
                        .description("Tech challenge desenvolvido durante as aulas da Pós Tech " +
                                "de Arquitetura e Desenvolvimento Java da FIAP\n" +
                                "\n" +
                                "RM362083 - Caike Rodrigues Queiroz \n" +
                                "\n" +
                                "RM362794 - Carlos Alberto Paulino\n" +
                                "\n" +
                                "RM361370 - Guilherme Marques Ferreira\n" +
                                "\n" +
                                "RM364724 - José Vitor de Oliveira Agatte\n" +
                                "\n" +
                                "RM361913 - Lucas Lopes Da Silva"
                        )
                        .version("v1")
                        .license(new License().name("Github - Foodcore").url("https://github.com/lucasllopes/foodcore")))
                .addSecurityItem(new SecurityRequirement().addList(schemeName))
                .components(new io.swagger.v3.oas.models.Components()
                        .addSecuritySchemes(schemeName,
                                new SecurityScheme()
                                        .name(schemeName)
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")));
    }
}
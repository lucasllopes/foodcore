package com.fiap.g10.g10auth.configuration;

import com.fiap.g10.g10auth.controller.handler.CustomAccessDeniedHandler;
import com.fiap.g10.g10auth.controller.handler.CustomAuthenticationEntryPoint;
import com.fiap.g10.g10auth.domain.model.Usuario;
import com.fiap.g10.g10auth.infrastructure.UsuarioDetailsAdapter;
import com.fiap.g10.g10auth.persistence.entity.TipoUsuario;
import com.fiap.g10.g10auth.persistence.entity.UsuarioEntity;
import com.fiap.g10.g10auth.persistence.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
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
public class SecurityConfig {

    @Autowired
    private FilterToken filterToken;

    @Bean
    public PasswordEncoder criptografar(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filtrosSeguranca(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(req -> {
                    req.requestMatchers("/login").permitAll();
                    req.requestMatchers(HttpMethod.POST, "/usuarios").permitAll();
                    req.requestMatchers(HttpMethod.DELETE, "/usuarios/*").hasRole(TipoUsuario.DONO.name());
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
    public UserDetailsService userDetailsService(UsuarioRepository repository) {
        return username -> {
            UsuarioEntity entity = repository.findByLoginIgnoreCase(username)
                    .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

            Usuario user = Usuario.reconstruirUsuario(entity);
            return new UsuarioDetailsAdapter(user);

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
}
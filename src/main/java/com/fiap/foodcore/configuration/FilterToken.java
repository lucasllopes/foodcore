package com.fiap.foodcore.configuration;

import com.fiap.foodcore.domain.model.User;
import com.fiap.foodcore.exception.TokenJwtException;
import com.fiap.foodcore.infrastructure.UserDetailsAdapter;
import com.fiap.foodcore.persistence.entity.UserEntity;
import com.fiap.foodcore.persistence.repository.UserRepository;
import com.fiap.foodcore.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class FilterToken extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final UserRepository userRepository;

    private FilterToken(TokenService tokenService, UserRepository userRepository) {
        this.tokenService = tokenService;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = extractToken(request);

        try{
            if(token != null){
                String login = tokenService.validateToken(token);

                UserEntity userEntity = userRepository.findByLoginIgnoreCase(login)
                        .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado."));

                User user = User.rebuildUserForTokenAuth(userEntity);

                UserDetailsAdapter userDetailsAdapter = new UserDetailsAdapter(user);

                Authentication authentication = new UsernamePasswordAuthenticationToken(userDetailsAdapter, null, userDetailsAdapter.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

        } catch (TokenJwtException ex){
            logger.warn("Invalid token");
        }


        filterChain.doFilter(request, response);
    }


    private String extractToken(HttpServletRequest request){
        String tokenHeader = request.getHeader("Authorization");

        if(tokenHeader != null){
            tokenHeader = tokenHeader.replace("Bearer ", "");
        }

        return tokenHeader;
    }
}
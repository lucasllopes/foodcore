package com.fiap.g10.g10auth.configuration;

import com.fiap.g10.g10auth.domain.model.Usuario;
import com.fiap.g10.g10auth.infrastructure.UsuarioDetailsAdapter;
import com.fiap.g10.g10auth.persistence.entity.UsuarioEntity;
import com.fiap.g10.g10auth.persistence.repository.UsuarioRepository;
import com.fiap.g10.g10auth.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class FilterToken extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = recuperaToken(request);

        if(token != null){
            String login = tokenService.verificaToken(token);

            UsuarioEntity usuarioEntity = usuarioRepository.findByLoginIgnoreCase(login)
                    .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado."));

            Usuario user = Usuario.reconstruirUsuario(usuarioEntity);

            UsuarioDetailsAdapter usuarioDetailsAdapter = new UsuarioDetailsAdapter(user);

            Authentication authentication = new UsernamePasswordAuthenticationToken(usuarioDetailsAdapter, null, usuarioDetailsAdapter.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }


    private String recuperaToken(HttpServletRequest request){
        String tokenHeader = request.getHeader("Authorization");

        if(tokenHeader != null){
            tokenHeader = tokenHeader.replace("Bearer ", "");
        }

        return tokenHeader;
    }
}
package com.fiap.g10.g10auth.controller.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiap.g10.g10auth.dto.AuthErrorResponseDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {

        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json");

        AuthErrorResponseDTO responseBody = new AuthErrorResponseDTO(
            HttpServletResponse.SC_FORBIDDEN,
            "Forbidden",
            "Você não tem permissão para acessar este recurso."
        );

        ObjectMapper mapper = new ObjectMapper();
        response.getWriter().write(mapper.writeValueAsString(responseBody));
    }
}
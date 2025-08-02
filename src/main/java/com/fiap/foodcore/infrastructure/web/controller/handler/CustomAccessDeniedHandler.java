package com.fiap.foodcore.infrastructure.web.controller.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiap.foodcore.infrastructure.web.controller.dto.AuthErrorResponseDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {

        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json");

        AuthErrorResponseDTO responseBody = new AuthErrorResponseDTO(
            HttpServletResponse.SC_FORBIDDEN,
            "Forbidden",
                getErrorMessage(accessDeniedException)
        );

        ObjectMapper mapper = new ObjectMapper();
        response.getWriter().write(mapper.writeValueAsString(responseBody));
    }
    private String getErrorMessage(AccessDeniedException accessDeniedException) {
        String errorMessage = accessDeniedException.getMessage();
        return errorMessage.equals("Access Denied") ? "Você não tem permissão para acessar este recurso." : accessDeniedException.getMessage();
    }
}
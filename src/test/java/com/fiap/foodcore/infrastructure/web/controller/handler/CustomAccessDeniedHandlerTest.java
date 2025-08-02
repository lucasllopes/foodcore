package com.fiap.foodcore.infrastructure.web.controller.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiap.foodcore.infrastructure.web.controller.dto.AuthErrorResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.access.AccessDeniedException;

import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CustomAccessDeniedHandlerTest {

    private CustomAccessDeniedHandler handler;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private PrintWriter writer;
    private StringWriter stringWriter;

    @BeforeEach
    void setUp() throws Exception {
        handler = new CustomAccessDeniedHandler();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);

        stringWriter = new StringWriter();
        writer = new PrintWriter(stringWriter);

        when(response.getWriter()).thenReturn(writer);
    }

    @Test
    void shouldReturnDefaultMessageWhenAccessDeniedMessageIsDefault() throws Exception {
        AccessDeniedException exception = new AccessDeniedException("Access Denied");

        handler.handle(request, response, exception);

        writer.flush();

        ObjectMapper mapper = new ObjectMapper();
        AuthErrorResponseDTO responseBody = mapper.readValue(stringWriter.toString(), AuthErrorResponseDTO.class);

        assertEquals(403, responseBody.status());
        assertEquals("Forbidden", responseBody.error());
        assertEquals("Você não tem permissão para acessar este recurso.", responseBody.message());

        verify(response).setStatus(HttpServletResponse.SC_FORBIDDEN);
        verify(response).setContentType("application/json");
    }

    @Test
    void shouldReturnCustomMessageWhenAccessDeniedMessageIsCustom() throws Exception {
        String customMessage = "Você só pode atualizar restaurantes que pertencem a você.";
        AccessDeniedException exception = new AccessDeniedException(customMessage);

        handler.handle(request, response, exception);

        writer.flush();

        ObjectMapper mapper = new ObjectMapper();
        AuthErrorResponseDTO responseBody = mapper.readValue(stringWriter.toString(), AuthErrorResponseDTO.class);

        assertEquals(403, responseBody.status());
        assertEquals("Forbidden", responseBody.error());
        assertEquals(customMessage, responseBody.message());

        verify(response).setStatus(HttpServletResponse.SC_FORBIDDEN);
        verify(response).setContentType("application/json");
    }
}
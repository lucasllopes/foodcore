package com.fiap.foodcore.infrastructure.web.controller.dto;

public record AuthErrorResponseDTO(
        int status,
        String error,
        String message
) {}
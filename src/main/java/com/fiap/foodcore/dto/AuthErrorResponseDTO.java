package com.fiap.foodcore.dto;

public record AuthErrorResponseDTO(
        int status,
        String error,
        String message
) {}
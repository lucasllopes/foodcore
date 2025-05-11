package com.fiap.g10.g10auth.dto;

public record AuthErrorResponseDTO(
        int status,
        String error,
        String message
) {}
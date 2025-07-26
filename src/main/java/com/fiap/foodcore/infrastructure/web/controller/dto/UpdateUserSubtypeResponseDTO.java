package com.fiap.foodcore.infrastructure.web.controller.dto;

import java.time.LocalDateTime;

public record UpdateUserSubtypeResponseDTO(Long id, String name, LocalDateTime lastModified) {
}

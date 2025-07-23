package com.fiap.foodcore.application.usecase.output;

import java.time.LocalDateTime;

public record UpdateUserSubtypeOutput(Long id, String name, LocalDateTime lastModified) {
}

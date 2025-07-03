package com.fiap.foodcore.infrastructure.web.controller.dto;

import java.util.List;

public record ValidationErrorDTO(
        List<String> errors,
        int status
) {}
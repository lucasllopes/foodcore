package com.fiap.foodcore.dto;

import java.util.List;

public record ValidationErrorDTO(
        List<String> errors,
        int status
) {}
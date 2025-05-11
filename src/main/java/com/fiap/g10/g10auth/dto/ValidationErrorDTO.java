package com.fiap.g10.g10auth.dto;

import java.util.List;

public record ValidationErrorDTO(
        List<String> errors,
        int status
) {}
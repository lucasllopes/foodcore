package com.fiap.foodcore.application.usecase.input;

import java.util.List;

public record CreateMenuInput(
        String nome,
        String descricao,
        List<CreateItemInput> items) {

}

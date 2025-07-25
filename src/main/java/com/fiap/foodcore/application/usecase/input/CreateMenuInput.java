package com.fiap.foodcore.application.usecase.input;

import java.util.List;

public record CreateMenuInput(
        String nome,
        String descricao,
        Long restaurantId,
        List<CreateItemInput> items) {

}

package com.fiap.foodcore.application.usecase.mapper;

import com.fiap.foodcore.domain.Menu;

public class MenuMapper {

//    public static Menu toDomain(Menu existing, String name, String description) {
//        existing.atualizarInformacoes(name, description);
//        return existing;
//    }

    public static Menu fromCreateInput(String name, String description) {
        return new Menu.Builder()
                .name(name)
                .description(description)
                .build();
    }

}

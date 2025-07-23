package com.fiap.foodcore.application.usecase;


import com.fiap.foodcore.application.usecase.output.CreateUserSubtypeOutput;

import java.util.List;

public interface FindUserSubtypeByNameUseCase {
    List<CreateUserSubtypeOutput> execute(String name);
}

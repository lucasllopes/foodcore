package com.fiap.foodcore.application.usecase;


import com.fiap.foodcore.application.usecase.output.CreateUserTypeOutput;

import java.util.List;

public interface FindUserTypeByNameUseCase {
    List<CreateUserTypeOutput> execute(String name);
}

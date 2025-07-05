package com.fiap.foodcore.application.strategy;


import com.fiap.foodcore.application.usecase.input.CreateUserInput;
import com.fiap.foodcore.application.usecase.output.CreateUserOutput;

public interface CreateUserStrategy {

    CreateUserOutput create(CreateUserInput createUserInput);

}

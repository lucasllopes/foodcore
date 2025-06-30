package com.fiap.foodcore.application.usecase;

import com.fiap.foodcore.core.port.in.DeleteUserUseCase;
import com.fiap.foodcore.core.port.out.UserRepositoryPort;
import com.fiap.foodcore.exception.DataNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class DeleteUserInteractor implements DeleteUserUseCase {

    private final UserRepositoryPort userRepositoryPort;

    public DeleteUserInteractor(UserRepositoryPort userRepositoryPort) {
        this.userRepositoryPort = userRepositoryPort;
    }

    @Override
    public void execute(Long id) {
        var user = userRepositoryPort.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Usuário não encontrado"));
        userRepositoryPort.delete(user);
    }
}

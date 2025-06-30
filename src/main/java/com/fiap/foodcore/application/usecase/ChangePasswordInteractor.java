package com.fiap.foodcore.application.usecase;

import com.fiap.foodcore.core.port.in.ChangePasswordUseCase;
import com.fiap.foodcore.core.port.out.UserRepositoryPort;
import com.fiap.foodcore.dto.ChangePasswordRequestDTO;
import com.fiap.foodcore.exception.DataNotFoundException;
import com.fiap.foodcore.exception.WrongPasswordException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ChangePasswordInteractor implements ChangePasswordUseCase {

    private final UserRepositoryPort userRepositoryPort;
    private final PasswordEncoder passwordEncoder;

    public ChangePasswordInteractor(UserRepositoryPort userGateway,
                                    PasswordEncoder passwordEncoder) {
        this.userRepositoryPort = userGateway;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void execute(Long id, ChangePasswordRequestDTO dto) {
        var entity = userRepositoryPort.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Usuário não encontrado"));
        if (!passwordEncoder.matches(dto.senhaAtual(), entity.getSenha())) {
            throw new WrongPasswordException("Senha atual incorreta");
        }
        entity.changePassword(passwordEncoder.encode(dto.novaSenha()));
        userRepositoryPort.save(entity);
    }
}
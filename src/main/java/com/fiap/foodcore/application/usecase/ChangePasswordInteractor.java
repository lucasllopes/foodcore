package com.fiap.foodcore.application.usecase;

import com.fiap.foodcore.application.gateway.UserGateway;
import com.fiap.foodcore.dto.ChangePasswordRequestDTO;
import com.fiap.foodcore.exception.DataNotFoundException;
import com.fiap.foodcore.exception.WrongPasswordException;
import org.springframework.security.crypto.password.PasswordEncoder;

public class ChangePasswordInteractor {

    private final UserGateway userGateway;
    private final PasswordEncoder passwordEncoder;

    public ChangePasswordInteractor(UserGateway userGateway,
                                    PasswordEncoder passwordEncoder) {
        this.userGateway = userGateway;
        this.passwordEncoder = passwordEncoder;
    }


    public void execute(Long id, ChangePasswordRequestDTO dto) {
        var entity = userGateway.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Usuário não encontrado"));
        if (!passwordEncoder.matches(dto.senhaAtual(), entity.getSenha())) {
            throw new WrongPasswordException("Senha atual incorreta");
        }
        entity.changePassword(passwordEncoder.encode(dto.novaSenha()));
        userGateway.save(entity);
    }
}
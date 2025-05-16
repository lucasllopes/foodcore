package com.fiap.g10.g10auth.service.strategy;


import com.fiap.g10.g10auth.persistence.entity.TipoUsuario;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class CriarUsuarioStrategyFactory {

    private final Map<TipoUsuario, CriarUsuarioStrategy> strategies;

    public CriarUsuarioStrategyFactory(
            @Qualifier("cliente") CriarUsuarioStrategy cliente,
            @Qualifier("dono") CriarUsuarioStrategy dono
    ) {
        this.strategies = Map.of(
                TipoUsuario.CLIENTE, cliente,
                TipoUsuario.DONO, dono
        );
    }

    public CriarUsuarioStrategy getStrategy(TipoUsuario tipoUsuario) {
        return strategies.get(tipoUsuario);
    }
}

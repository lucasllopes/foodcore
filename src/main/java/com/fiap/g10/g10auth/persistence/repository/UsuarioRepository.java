package com.fiap.g10.g10auth.persistence.repository;

import com.fiap.g10.g10auth.domain.model.Usuario;
import com.fiap.g10.g10auth.persistence.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long> {

    Optional<UsuarioEntity> findByLogin(String login);

    Optional<UsuarioEntity> findByEmail(String email);

    Optional<UsuarioEntity> findByLoginIgnoreCase(String login);
}

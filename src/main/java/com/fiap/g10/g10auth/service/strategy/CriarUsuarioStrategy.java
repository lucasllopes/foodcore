package com.fiap.g10.g10auth.service.strategy;


import com.fiap.g10.g10auth.dto.UsuarioCreateRequestDTO;
import com.fiap.g10.g10auth.dto.UsuarioResponseDTO;

public interface CriarUsuarioStrategy {

    UsuarioResponseDTO criar(UsuarioCreateRequestDTO dto);

}

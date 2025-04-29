package com.fiap.g10.g10auth.dto;

import com.fiap.g10.g10auth.persistence.entity.TipoUsuario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UsuarioUpdateRequestDTO(@NotBlank(message = "Nome é obrigatório")
                                      String nome,

                                      @Email(message = "Email inválido")
                                      @NotBlank(message = "Email é obrigatório")
                                      String email,

                                      @NotNull(message = "Tipo de usuário é obrigatório")
                                      TipoUsuario tipo,

                                      @NotBlank(message = "Logradouro é obrigatório")
                                      String logradouro,

                                      @NotBlank(message = "Número é obrigatório")
                                      String numero,

                                      String complemento,

                                      @NotBlank(message = "Bairro é obrigatório")
                                      String bairro,

                                      @NotBlank(message = "Cidade é obrigatória")
                                      String cidade,

                                      @NotBlank(message = "Estado é obrigatório")
                                      String estado,

                                      @NotBlank(message = "CEP é obrigatório")
                                      String cep
) {
}
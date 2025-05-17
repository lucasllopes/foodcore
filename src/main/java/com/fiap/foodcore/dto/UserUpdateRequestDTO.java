package com.fiap.foodcore.dto;

import com.fiap.foodcore.persistence.entity.UserType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record UserUpdateRequestDTO(@NotBlank(message = "Nome é obrigatório")
                                      String nome,

                                   @Email(message = "Email inválido")
                                      @NotBlank(message = "Email é obrigatório")
                                      String email,

                                   @NotNull(message = "Tipo de usuário é obrigatório")
                                      UserType tipo,

                                   List<AddressUpdateRequestDTO> enderecos

) {
}
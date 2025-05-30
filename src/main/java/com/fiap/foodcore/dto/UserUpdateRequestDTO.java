package com.fiap.foodcore.dto;

import com.fiap.foodcore.persistence.entity.UserType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record UserUpdateRequestDTO(@NotBlank(message = "Nome é obrigatório")
                                      String nome,

                                   @Email(message = "Email inválido")
                                      @NotBlank(message = "Email é obrigatório")
                                      String email,

                                   @NotNull(message = "Necessário informar um endereço.")
                                   @NotEmpty(message = "A lista de endereços não pode estar vazia.")
                                   List<AddressUpdateRequestDTO> enderecos

) {
}
package com.fiap.foodcore.infrastructure.presenter;

import com.fiap.foodcore.application.usecase.input.*;
import com.fiap.foodcore.application.usecase.output.CreateUserOutput;
import com.fiap.foodcore.domain.UserTypeDomain;
import com.fiap.foodcore.infrastructure.web.controller.dto.*;

import java.util.List;
import java.util.stream.Collectors;

public class UserPresenter {

    public static UserResponseDTO toDto(CreateUserOutput output) {
        return new UserResponseDTO(
                output.id(),
                output.nome(),
                output.email(),
                output.login(),
                output.tipo(),
                output.enderecos().stream().map(end ->
                        new AddressResponseDTO(
                                end.logradouro(),
                                end.numero(),
                                end.complemento(),
                                end.bairro(),
                                end.cep(),
                                end.estado(),
                                end.cidade()
                        )
                ).collect(Collectors.toList()),
                output.userTypeOutput() != null
                        ? new UserTypeResponseDTO(output.userTypeOutput().id(),output.userTypeOutput().name())
                        : null
        );
    }

    public static List<UserResponseDTO> toDtoList(List<CreateUserOutput> outputs) {
        return outputs.stream().map(UserPresenter::toDto).toList();
    }

    public static CreateUserInput toInputCreate(UserCreateRequestDTO dto) {
        List<CreateAddressInput> addressesInput = dto.enderecos().stream()
                .map(addressDto -> new CreateAddressInput(
                        addressDto.logradouro(),
                        addressDto.numero(),
                        addressDto.complemento(),
                        addressDto.bairro(),
                        addressDto.cidade(),
                        addressDto.cep(),
                        addressDto.estado()
                ))
                .toList();

        return new CreateUserInput(
                dto.nome(),
                dto.email(),
                dto.login(),
                dto.senha(),
                UserTypeDomain.fromString(dto.tipo()),
                addressesInput
        );
    }

    public static UpdateUserInput toInputUpdate(UserUpdateRequestDTO dto) {
        return new UpdateUserInput(
                dto.nome(),
                dto.email(),
                dto.enderecos().stream()
                        .map(addressDto -> new AddressUpdateInput(
                                addressDto.logradouro(),
                                addressDto.numero(),
                                addressDto.complemento(),
                                addressDto.bairro(),
                                addressDto.cidade(),
                                addressDto.cep(),
                                addressDto.estado()
                        ))
                        .toList()
        );
    }

    public static ChangePasswordInput toChangePasswordInput(ChangePasswordRequestDTO dto) {
        return new ChangePasswordInput(dto.senhaAtual(), dto.novaSenha());
    }

    public static AssignUserSubtypeToUserInput toAssignUserTypeToUserInput(AssignUserTypeToUserDTO dto) {
        return new AssignUserSubtypeToUserInput(dto.idUserType());
    }
}

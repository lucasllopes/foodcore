package com.fiap.foodcore.domain.model;

import com.fiap.foodcore.dto.AddressCreateRequestDTO;
import com.fiap.foodcore.dto.UserCreateRequestDTO;
import com.fiap.foodcore.dto.UserUpdateRequestDTO;
import com.fiap.foodcore.persistence.entity.UserType;
import com.fiap.foodcore.persistence.entity.UserEntity;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class User {

    private Long id;
    private String nome;
    private String email;
    private String login;
    private String senha;
    private LocalDateTime dataUltimaAlteracao;
    private UserType tipo;
    private List<Address> address;

    private User() {

    }

    public static User fromCreateRequest(String senhaCodificada, UserType tipo, UserCreateRequestDTO dto) {
        User user = new User();
        user.nome = dto.nome();
        user.email = dto.email();
        user.login = dto.login();
        user.senha = senhaCodificada;
        user.tipo = tipo;

        if (!dto.enderecos().isEmpty()) {

            user.address = dto.enderecos()
                    .stream()
                    .map(Address::fromCreateRequest)
                    .toList();
        }

        user.dataUltimaAlteracao = LocalDateTime.now();
        return user;
    }


    public void changePassword(String novaSenha) {
        this.senha = novaSenha;
        this.dataUltimaAlteracao = LocalDateTime.now();
    }

    public void updateInformation(UserUpdateRequestDTO dto) {
        this.nome = dto.nome();
        this.email = dto.email();

        if (dto.enderecos() != null) {
            if (this.address == null) {
                this.address = new ArrayList<>();
            }
         
            List<Address> updatedAddresses = new ArrayList<>();
      
            for (int i = 0; i < dto.enderecos().size(); i++) {
                if (i < this.address.size()) {
                    Address existingAddress = this.address.get(i);
                    existingAddress.updateFrom(dto.enderecos().get(i));
                    updatedAddresses.add(existingAddress);
                } else {

                    var addressCreateDto = getAddressCreateRequestDTO(dto, i);

                    updatedAddresses.add(Address.fromCreateRequest(addressCreateDto));
                }
            }
            this.address = updatedAddresses;
        }

        this.dataUltimaAlteracao = LocalDateTime.now();
    }

    private static AddressCreateRequestDTO getAddressCreateRequestDTO(UserUpdateRequestDTO dto, int i) {
        var addressUpdateDto = dto.enderecos().get(i);
        return new AddressCreateRequestDTO(
                addressUpdateDto.logradouro(),
                addressUpdateDto.numero(),
                addressUpdateDto.complemento(),
                addressUpdateDto.bairro(),
                addressUpdateDto.cidade(),
                addressUpdateDto.estado(),
                addressUpdateDto.cep()
        );
    }


    public static User rebuildUser(UserEntity entity) {
        User user = new User();
        user.id = entity.getId();
        user.nome = entity.getNome();
        user.email = entity.getEmail();
        user.login = entity.getLogin();
        user.senha = entity.getSenha();
        user.tipo = entity.getTipo();

        user.address = Address.fromEntity(entity.getEnderecos());

        user.dataUltimaAlteracao = entity.getDataUltimaAlteracao();
        return user;
    }

    public static User rebuildUserForTokenAuth(UserEntity entity) {
        User user = new User();
        user.id = entity.getId();
        user.nome = entity.getNome();
        user.email = entity.getEmail();
        user.login = entity.getLogin();
        user.senha = entity.getSenha();
        user.tipo = entity.getTipo();
        user.dataUltimaAlteracao = entity.getDataUltimaAlteracao();
        return user;

    }
}


package com.fiap.g10.g10auth.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "usuario")
public class UsuarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String email;
    private String login;
    private String senha;
    private LocalDateTime dataUltimaAlteracao;

    @Enumerated(EnumType.STRING)
    private TipoUsuario tipo;

    private String logradouro;
    private String numero;
    private String complemento;
    private String bairro;
    private String cep;
    private String estado;
    private String cidade;
}

package com.fiap.g10.g10auth.entity;

import com.fiap.g10.g10auth.dto.UsuarioRequestDTO;
import com.fiap.g10.g10auth.exception.SenhaIncorretaException;
import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

@Entity
@Getter
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String email;
    private String login;
    private String senha;
    private LocalDateTime dataUltimaAlteracao;

    @Embedded
    private Endereco endereco;

    @Enumerated(EnumType.STRING)
    private TipoUsuario tipo;

    public Usuario() {

    }

    public Usuario(String nome, String email, String login, String senha, Endereco endereco, TipoUsuario tipo) {
        this.nome = nome;
        this.email = email;
        this.login = login;
        this.senha = senha;
        this.endereco = endereco;
        this.tipo = tipo;
    }

    public static Usuario novoUsuario(UsuarioRequestDTO dto, PasswordEncoder passwordEncoder) {

        Endereco endereco = new Endereco(
                dto.logradouro(),
                dto.numero(),
                dto.complemento(),
                dto.bairro(),
                dto.cep()
        );

        return new Usuario(
                dto.nome(),
                dto.email(),
                dto.login(),
                passwordEncoder.encode(dto.senha()),
                endereco,
                dto.tipo()
        );
    }

    public void trocarSenha(String senhaAtual, String novaSenha, PasswordEncoder passwordEncoder) {
        if (!passwordEncoder.matches(senhaAtual, this.senha)) {
            throw new SenhaIncorretaException("Senha atual incorreta.");
        }

        this.senha = passwordEncoder.encode(novaSenha);
        this.dataUltimaAlteracao = LocalDateTime.now();
    }

    public void atualizarDados(UsuarioRequestDTO dto) {
        this.nome = dto.nome();
        this.email = dto.email();
        this.login = dto.login();
        this.tipo = dto.tipo();
        this.endereco = new Endereco(
                dto.logradouro(),
                dto.numero(),
                dto.complemento(),
                dto.bairro(),
                dto.cep()
        );
        this.dataUltimaAlteracao = LocalDateTime.now();
    }
}
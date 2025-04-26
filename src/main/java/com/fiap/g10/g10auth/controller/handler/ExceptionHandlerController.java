package com.fiap.g10.g10auth.controller.handler;

import com.fiap.g10.g10auth.dto.MensagemErroDTO;
import com.fiap.g10.g10auth.exception.DadoDuplicadoException;
import com.fiap.g10.g10auth.exception.DadoNaoEncontradoException;
import com.fiap.g10.g10auth.exception.SenhaIncorretaException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(DadoDuplicadoException.class)
    public ResponseEntity<MensagemErroDTO> tratarDadoDuplicado(DadoDuplicadoException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new MensagemErroDTO(ex.getMessage()));
    }

    @ExceptionHandler(DadoNaoEncontradoException.class)
    public ResponseEntity<MensagemErroDTO> tratarDadoNaoEncontrado(DadoNaoEncontradoException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MensagemErroDTO(ex.getMessage()));
    }

    @ExceptionHandler(SenhaIncorretaException.class)
    public ResponseEntity<MensagemErroDTO> tratarSenhaIncorreta(SenhaIncorretaException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new MensagemErroDTO(ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<MensagemErroDTO> tratarValidacao(MethodArgumentNotValidException ex) {
        String mensagem = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(erro -> erro.getField() + ": " + erro.getDefaultMessage())
                .collect(Collectors.joining("; "));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MensagemErroDTO(mensagem));
    }
}

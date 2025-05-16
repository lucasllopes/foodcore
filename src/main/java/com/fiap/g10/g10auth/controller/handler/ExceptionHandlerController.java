package com.fiap.g10.g10auth.controller.handler;

import com.fiap.g10.g10auth.dto.MensagemErroDTO;
import com.fiap.g10.g10auth.dto.ValidationErrorDTO;
import com.fiap.g10.g10auth.exception.DadoDuplicadoException;
import com.fiap.g10.g10auth.exception.DadoNaoEncontradoException;
import com.fiap.g10.g10auth.exception.SenhaIncorretaException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(DadoDuplicadoException.class)
    public ResponseEntity<MensagemErroDTO> tratarDadoDuplicado(DadoDuplicadoException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new MensagemErroDTO(ex.getMessage()));
    }

    @ExceptionHandler(DadoNaoEncontradoException.class)
    public ResponseEntity<MensagemErroDTO> tratarDadoNaoEncontrado(DadoNaoEncontradoException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MensagemErroDTO(ex.getMessage()));
    }

    @ExceptionHandler(SenhaIncorretaException.class)
    public ResponseEntity<MensagemErroDTO> tratarSenhaIncorreta(SenhaIncorretaException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new MensagemErroDTO(ex.getMessage()));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<MensagemErroDTO> trataLoginSenhaIncorreto(BadCredentialsException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new MensagemErroDTO(ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorDTO> tratarValidacao(MethodArgumentNotValidException ex) {

        var status = HttpStatus.BAD_REQUEST;
        List<String> errors = new ArrayList<>();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            errors.add(fieldError.getField() + ": " + fieldError.getDefaultMessage());
        }
        return ResponseEntity.status(status.value()).body(new ValidationErrorDTO(errors, status.value()));
    }
}

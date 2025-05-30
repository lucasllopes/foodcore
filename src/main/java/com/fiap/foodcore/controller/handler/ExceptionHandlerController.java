package com.fiap.foodcore.controller.handler;

import com.fiap.foodcore.dto.MessageErrorDTO;
import com.fiap.foodcore.dto.ValidationErrorDTO;
import com.fiap.foodcore.exception.DuplicatedDataException;
import com.fiap.foodcore.exception.DataNotFoundException;
import com.fiap.foodcore.exception.WrongPasswordException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ExceptionHandlerController {


    @ExceptionHandler(DuplicatedDataException.class)
    public ResponseEntity<MessageErrorDTO> tratarDadoDuplicado(DuplicatedDataException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new MessageErrorDTO(ex.getMessage()));
    }

    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<MessageErrorDTO> tratarDadoNaoEncontrado(DataNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageErrorDTO(ex.getMessage()));
    }

    @ExceptionHandler(WrongPasswordException.class)
    public ResponseEntity<MessageErrorDTO> tratarSenhaIncorreta(WrongPasswordException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new MessageErrorDTO(ex.getMessage()));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<MessageErrorDTO> trataLoginSenhaIncorreto(BadCredentialsException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new MessageErrorDTO(ex.getMessage()));
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

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<MessageErrorDTO> tratarNotFound(NoHandlerFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageErrorDTO("Endpoint não encontrado"));
    }
}

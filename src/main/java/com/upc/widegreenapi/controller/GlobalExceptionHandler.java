package com.upc.widegreenapi.controller;

import com.upc.widegreenapi.exceptions.ErrorMessage;
import com.upc.widegreenapi.exceptions.InvalidEmailException;
import com.upc.widegreenapi.exceptions.UsuarioNotFoundException;
import com.upc.widegreenapi.serviceImpl.UsuarioServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.logging.Logger;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = Logger.getLogger(GlobalExceptionHandler.class.getName());


    @ExceptionHandler(UsuarioNotFoundException.class)
    public ResponseEntity<String> handleUsuarioNotFoundException(UsuarioNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidEmailException.class)
    public ResponseEntity<ErrorMessage> handleInvalidEmailException(InvalidEmailException ex) {
        ErrorMessage error = ErrorMessage.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .exception(ex.getClass().getSimpleName())
                .message(ex.getMessage())
                .description("El email ingresado no tiene un formato válido.")
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorMessage> handleRuntimeException(RuntimeException ex) {
        ErrorMessage error = ErrorMessage.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .exception(ex.getClass().getSimpleName())
                .message(ex.getMessage())
                .description("Error en el registro de usuario.")
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}

package com.elektra.school_students.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.elektra.school_students.response.CustomErrorResponse;

@RestControllerAdvice
public class CustomGlobalExceptionController {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CustomErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex,
            WebRequest request) {
        String message = ex.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);
        int status = HttpStatus.BAD_REQUEST.value();
        String path = request.getDescription(false).replace("uri=", "");

        CustomErrorResponse errors = CustomErrorResponse.builder()
                .message(message)
                .timestamp(timestamp)
                .status(status)
                .path(path)
                .build();

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<CustomErrorResponse> handleInvalidFormatException(InvalidFormatException ex,
            WebRequest request) {
        String fieldName = ex.getPath().stream()
                .map(JsonMappingException.Reference::getFieldName)
                .collect(Collectors.joining("."));

        String message = switch (fieldName) {
            case "name" -> "Tiene que ingresar un nombre valido";
            case "age" -> "Ingrese la edad como un valor entero";
            case "grade" -> "Ingrese el grado como un valor entero";
            case "address" -> "Ingrese una dirección valida";
            case "foreignStudent" -> "Indique si el alumno es foraneo con 'Y' o 'N'";
            default -> "";
        };

        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);
        int status = HttpStatus.BAD_REQUEST.value();
        String path = request.getDescription(false).replace("uri=", "");

        CustomErrorResponse errors = CustomErrorResponse.builder()
                .message(message)
                .timestamp(timestamp)
                .status(status)
                .path(path)
                .build();

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}

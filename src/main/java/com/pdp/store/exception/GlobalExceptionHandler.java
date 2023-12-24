package com.pdp.store.exception;

import com.pdp.store.dto.ErrorResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.util.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> error404(NotFoundException e, HttpServletRequest req) {
        return ResponseEntity
                .status(404)
                .body(ErrorResponseDTO.builder()
                        .errorPath(req.getRequestURI())
                        .errorCode(404)
                        .errorBody(e.getMessage())
                        .build());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> notValidExceptionHandler(MethodArgumentNotValidException e, HttpServletRequest req) {
        Map<String, List<String>> errorBody = new HashMap<>();

        for (FieldError fieldError : e.getFieldErrors()) {
            String field = fieldError.getField();
            String message = fieldError.getDefaultMessage();

            errorBody.compute(field, (s, strings) -> {
                strings = Objects.requireNonNullElse(strings, new ArrayList<>());
                strings.add(message);
                return strings;
            });
        }

        return ResponseEntity
                .status(400)
                .body(ErrorResponseDTO.builder()
                        .errorPath(req.getRequestURI())
                        .errorCode(400)
                        .errorBody(errorBody)
                        .build());
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<ErrorResponseDTO> handleIOException(IOException e, HttpServletRequest req) {
        return ResponseEntity
                .status(500)
                .body(ErrorResponseDTO.builder()
                        .errorPath(req.getRequestURI())
                        .errorCode(500)
                        .errorBody("An internal server error occurred: " + e.getMessage())
                        .build());
    }
}

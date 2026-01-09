package com.library.exception;

import com.library.dto.MessageResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<MessageResponseDTO> handleBookNotFoundException(BookNotFoundException e) {
        MessageResponseDTO errorResponse = MessageResponseDTO.builder()
                                                             .message(e.getMessage())
                                                             .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }
}

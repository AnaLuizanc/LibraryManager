package com.springbootRest.springbootRest.controller;

import com.springbootRest.springbootRest.dto.BookDTO;
import com.springbootRest.springbootRest.dto.MessageResponseDTO;
import com.springbootRest.springbootRest.exception.BookNotFoundException;
import com.springbootRest.springbootRest.service.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/books")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    public ResponseEntity<MessageResponseDTO> create(@RequestBody @Valid BookDTO bookDTO) {
        try {
            MessageResponseDTO response = bookService.create(bookDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            MessageResponseDTO errorResponse = MessageResponseDTO.builder()
                    .message(e.getMessage())
                    .build();
            return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
        }
    }

    @GetMapping()
    public List<BookDTO> listAll() {
        return bookService.listAll();
    }

    @GetMapping("/{id}")
    public BookDTO findById(@PathVariable Long id) throws BookNotFoundException {
        return bookService.findById(id);
    }
}

package com.library.controller;

import com.library.dto.BookDTO;
import com.library.dto.MessageResponseDTO;
import com.library.exception.BookNotFoundException;
import com.library.service.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
public class BookController {

    @GetMapping
    public String index() {
        return "pages/home/home";
    }

    @GetMapping("/home")
    public String home() {
        return "pages/home/home";
    }

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

//    @GetMapping()
//    public List<BookDTO> listAll() {
//        return bookService.listAll();
//    }

    @GetMapping("/{id}")
    public BookDTO findById(@PathVariable Long id) throws BookNotFoundException {
        return bookService.findById(id);
    }
}

package com.springbootRest.springbootRest.service;

import com.springbootRest.springbootRest.dto.BookDTO;
import com.springbootRest.springbootRest.dto.MessageResponseDTO;
import com.springbootRest.springbootRest.entity.Book;
import com.springbootRest.springbootRest.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookService {
    private BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public MessageResponseDTO create(BookDTO bookDTO) {
        Book bookToSave = Book.builder()
                              .name(bookDTO.getName())
                              .pages(bookDTO.getPages())
                              .chapters(bookDTO.getChapters())
                              .author(bookDTO.getAuthor())
                              .build();
        Book savedBook = bookRepository.save(bookDTO);
        return MessageResponseDTO.builder().message("Book created with ID " + savedBook.getId()).build();
    }
}

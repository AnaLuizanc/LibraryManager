package com.springbootRest.springbootRest.service;

import com.springbootRest.springbootRest.dto.BookDTO;
import com.springbootRest.springbootRest.dto.MessageResponseDTO;
import com.springbootRest.springbootRest.entity.Book;
import com.springbootRest.springbootRest.mapper.BookMapper;
import com.springbootRest.springbootRest.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {
    private BookRepository bookRepository;

    private final BookMapper bookMapper = BookMapper.INSTANCE;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public MessageResponseDTO create(BookDTO bookDTO) {
        Book bookToSave = bookMapper.toModel(bookDTO);
        Book savedBook = bookRepository.save(bookToSave);
        return MessageResponseDTO.builder().message("Book created with ID " + savedBook.getId()).build();
    }

    public List<BookDTO> listAll() {
        List<Book> allBooks = bookRepository.findAll();
        return allBooks.stream()
                .map(bookMapper::toDTO)
                .collect(Collectors.toList());
    }
}

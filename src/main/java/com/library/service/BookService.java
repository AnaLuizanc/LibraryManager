package com.library.service;

import com.library.dto.BookDTO;
import com.library.dto.MessageResponseDTO;
import com.library.entity.Author;
import com.library.entity.Book;
import com.library.exception.BookNotFoundException;
import com.library.mapper.BookMapper;
import com.library.repository.AuthorRepository;
import com.library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    private final BookMapper bookMapper = BookMapper.INSTANCE;

    @Autowired
    public BookService(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    @Transactional
    public MessageResponseDTO create(BookDTO bookDTO) {
        // Check if book with same title already exists
        Optional<Book> existingBook = bookRepository.findByTitle(bookDTO.getTitle());
        if (existingBook.isPresent()) {
            throw new IllegalArgumentException("A book with title '" + bookDTO.getTitle() + "' already exists");
        }

        Book bookToSave = bookMapper.toModel(bookDTO);

        // Verificar se o autor já existe pelo nome ou criar um novo
        Author author;
        Optional<Author> existingAuthor = authorRepository.findByName(bookDTO.getAuthor().getName());
        if (existingAuthor.isPresent()) {
            author = existingAuthor.get();
        } else {
            // Criar e salvar novo autor
            author = Author.builder()
                    .name(bookDTO.getAuthor().getName())
                    .age(bookDTO.getAuthor().getAge())
                    .build();
            author = authorRepository.save(author);
        }

        bookToSave.setAuthor(author);

        try {
            Book savedBook = bookRepository.save(bookToSave);
            return MessageResponseDTO.builder().message("Book created with ID " + savedBook.getId()).build();
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("Book with this title or ISBN already exists");
        }
    }

    public List<BookDTO> listAll() {
        List<Book> allBooks = bookRepository.findAll();
        return allBooks.stream()
                .map(bookMapper::toDTO)
                .collect(Collectors.toList());
    }

    public BookDTO findById(final Long id) throws BookNotFoundException {
        Book book = bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException(id));
        return bookMapper.toDTO(book);
    }
}

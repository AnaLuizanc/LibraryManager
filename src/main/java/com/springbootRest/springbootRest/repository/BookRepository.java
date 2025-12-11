package com.springbootRest.springbootRest.repository;

import com.springbootRest.springbootRest.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {}

package com.springbootRest.springbootRest.utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.springbootRest.springbootRest.dto.BookDTO;
import com.springbootRest.springbootRest.entity.Book;
import net.datafaker.Faker;

public class BookUtils {

    private static final Faker faker = new Faker();

    public static BookDTO createFakeBookDTO(){
        return BookDTO.builder()
                .title(generateRandomTitle())
                .isbn(generateRandomISBN())
                .publisherName(generateRandomPublisher())
                .pages(generateRandomPages())
                .chapters(generateRandomChapters())
                .author(AuthorUtils.createFakeAuthorDTO())
                .build();
    }

    public static Book createFakeBook(){
        return Book.builder()
                .title(generateRandomTitle())
                .isbn(generateRandomISBN())
                .publisherName(generateRandomPublisher())
                .pages(generateRandomPages())
                .chapters(generateRandomChapters())
                .author(AuthorUtils.createFakeAuthor())
                .build();
    }

    private static String generateRandomTitle() {
        return faker.book().title();
    }

    private static String generateRandomISBN() {
        // Generate a valid ISBN-10 format that matches the validation pattern: only digits and hyphens
        String isbn10 = faker.code().isbn10();
        // Remove any 'X' characters and replace with random digit, then format properly
        isbn10 = isbn10.replace('X', '0').replaceAll("[^0-9]", "");

        // Ensure we have exactly 10 digits
        while (isbn10.length() < 10) {
            isbn10 += faker.number().digit();
        }
        if (isbn10.length() > 10) {
            isbn10 = isbn10.substring(0, 10);
        }

        // Format as ISBN with hyphens: XXX-X-XXXX-X
        return String.format("%s-%s-%s-%s",
                isbn10.substring(0, 3),
                isbn10.substring(3, 4),
                isbn10.substring(4, 8),
                isbn10.substring(8, 10));
    }

    private static String generateRandomPublisher() {
        return faker.book().publisher();
    }

    private static Integer generateRandomPages() {
        return faker.number().numberBetween(50, 1000);
    }

    private static Integer generateRandomChapters() {
        return faker.number().numberBetween(1, 50);
    }

    public static String asJsonString(BookDTO bookDTO) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
            objectMapper.registerModules(new JavaTimeModule());
            return objectMapper.writeValueAsString(bookDTO);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

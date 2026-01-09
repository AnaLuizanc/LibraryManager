package com.library.utils;

import com.library.dto.AuthorDTO;
import com.library.entity.Author;
import net.datafaker.Faker;

public class AuthorUtils {

    private static final Faker faker = new Faker();

    public static AuthorDTO createFakeAuthorDTO(){
        return AuthorDTO.builder()
                .name(generateRandomName())
                .age(generateRandomAge())
                .build();
    }

    public static Author createFakeAuthor(){
        return Author.builder()
                .name(generateRandomName())
                .age(generateRandomAge())
                .build();
    }

    private static String generateRandomName() {
        return faker.name().fullName();
    }

    private static int generateRandomAge() {
        return faker.number().numberBetween(18, 80);
    }
}

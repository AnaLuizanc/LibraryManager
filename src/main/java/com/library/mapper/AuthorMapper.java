package com.library.mapper;

import com.library.dto.AuthorDTO;
import com.library.entity.Author;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AuthorMapper {
    AuthorMapper INSTANCE = Mappers.getMapper(AuthorMapper.class);

    Author toModel(AuthorDTO authorDTO);
    AuthorDTO toDTO(Author author);
}


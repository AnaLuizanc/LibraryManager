package com.springbootRest.springbootRest.mapper;

import com.springbootRest.springbootRest.dto.AuthorDTO;
import com.springbootRest.springbootRest.entity.Author;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AuthorMapper {
    AuthorMapper INSTANCE = Mappers.getMapper(AuthorMapper.class);

    Author toModel(AuthorDTO authorDTO);
    AuthorDTO toDTO(Author author);
}


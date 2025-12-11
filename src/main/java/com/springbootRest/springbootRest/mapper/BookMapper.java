package com.springbootRest.springbootRest.mapper;

import com.springbootRest.springbootRest.dto.BookDTO;
import com.springbootRest.springbootRest.entity.Book;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BookMapper {

    BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);

    Book toModel(BookDTO bookDTO);

    BookDTO toDTO(Book book);
}

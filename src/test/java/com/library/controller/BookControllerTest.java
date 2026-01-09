package com.library.controller;

import com.library.dto.BookDTO;
import com.library.dto.MessageResponseDTO;
import com.library.service.BookService;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import static com.library.utils.BookUtils.asJsonString;
import static com.library.utils.BookUtils.createFakeBookDTO;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class BookControllerTest {

    private MockMvc mockMvc;

    @Mock
    private BookService bookService;

    @InjectMocks
    private BookController bookController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(bookController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setViewResolvers(((viewName, locale) -> new MappingJackson2JsonView()))
                .build();
    }

    @Test
    void testWhenPOSTisCalledThenABookShoulBeCreated() throws Exception {
        BookDTO bookDTO = createFakeBookDTO();
        MessageResponseDTO expectedResponse = MessageResponseDTO.builder()
                .message("Book created with ID " + bookDTO.getId())
                .build();
        Mockito.when(bookService.create(bookDTO)).thenReturn(expectedResponse);
        mockMvc.perform(post("/api/v1/books")
                        .contentType(APPLICATION_JSON)
                        .content(asJsonString(bookDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", Is.is(expectedResponse.getMessage())));
    }

    @Test
    void testWhenPOSTWithInvalidIsbnIsCalled() throws Exception {
        BookDTO bookDTO = createFakeBookDTO();
        bookDTO.setIsbn("ISBN INVÁLIDO");

        mockMvc.perform(post("/api/v1/books")
                        .contentType(APPLICATION_JSON)
                        .content(asJsonString(bookDTO)))
                .andExpect(status().isBadRequest());
    }
}
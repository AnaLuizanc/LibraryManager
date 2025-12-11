package com.springbootRest.springbootRest.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO {
    private Long Id;

    @NotNull(message = "Nome não pode ser nulo")
    @Size(min = 1, max = 200, message = "Nome deve ter entre 1 e 200 caracteres")
    private String name;

    @NotNull(message = "Número de páginas não pode ser nulo")
    @Min(value = 1, message = "Número de páginas deve ser maior que 0")
    private Integer pages;

    @NotNull(message = "Número de capítulos não pode ser nulo")
    @Min(value = 1, message = "Número de capítulos deve ser maior que 0")
    private Integer chapters;

    @NotNull(message = "ISBN não pode ser nulo")
    @Pattern(regexp = "^(?:ISBN(?:-1[03])?:? )?(?=[0-9X]{10}$|(?=(?:[0-9]+[- ]){3})[- 0-9X]{13}$|97[89][0-9]{10}$|(?=(?:[0-9]+[- ]){4})[- 0-9]{17}$)(?:97[89][- ]?)?[0-9]{1,5}[- ]?[0-9]+[- ]?[0-9]+[- ]?[0-9X]$",
             message = "ISBN deve estar em formato válido")
    private String isbn;

    @NotNull(message = "Nome da editora não pode ser nulo")
    @Size(min = 1, max = 100, message = "Nome da editora deve ter entre 1 e 100 caracteres")
    private String publisherName;

    @NotNull(message = "Autor não pode ser nulo")
    @Valid
    private AuthorDTO author;
}

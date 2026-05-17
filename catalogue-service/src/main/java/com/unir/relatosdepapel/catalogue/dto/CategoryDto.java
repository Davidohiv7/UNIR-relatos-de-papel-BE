package com.unir.relatosdepapel.catalogue.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryDto {
    private Integer id;
    private String name;
    // Aquí NO va la lista de libros. Por eso se corta el bucle infinito.
}
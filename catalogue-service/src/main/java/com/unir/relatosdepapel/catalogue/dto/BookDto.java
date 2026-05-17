package com.unir.relatosdepapel.catalogue.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookDto {
    private Integer id;
    private String title;
    private String author;
    private String description;
    private String language;
    private String format;
    private Integer year;
    private Double price;
    private Integer stock;
    private Double rating;
    private Integer reviewsCount;
    private Integer pages;
    private String isbn;
    private Boolean featured;

    // En lugar de mapear la entidad completa con sus bucles, creas clases limpias para el JSON
    private CategoryDto category;
    private TagDto tag;
}
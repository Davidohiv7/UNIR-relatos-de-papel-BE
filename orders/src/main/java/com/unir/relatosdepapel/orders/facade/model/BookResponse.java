package com.unir.relatosdepapel.orders.facade.model;

import lombok.*;
import java.math.BigDecimal;

// TODO: Este DTO es provisional. Debe adaptarse al modelo real de Book
// que exponga el microservicio catalogue una vez que esté disponible.
// Coordinar con el equipo el contrato exacto de la respuesta de GET /books/{id}
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookResponse {

    private Integer id;
    private String title;
    private String author;
    private BigDecimal price;
    private Integer stock;
    private Boolean visible;
}
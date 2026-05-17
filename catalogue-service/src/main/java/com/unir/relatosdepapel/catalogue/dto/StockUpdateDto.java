package com.unir.relatosdepapel.catalogue.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StockUpdateDto {
    private Integer bookId;
    private Integer quantity;
}
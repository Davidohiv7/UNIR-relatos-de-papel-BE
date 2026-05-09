package com.unir.relatosdepapel.orders.controller.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderItemRequest {

    private Integer idCatalogue;
    private Integer quantity;
}
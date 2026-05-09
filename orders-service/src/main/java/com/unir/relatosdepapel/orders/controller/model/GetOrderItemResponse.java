package com.unir.relatosdepapel.orders.controller.model;

import lombok.*;
import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetOrderItemResponse {

    private Integer idCatalogue;
    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal subTotal;
}
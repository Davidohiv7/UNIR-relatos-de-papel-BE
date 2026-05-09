package com.unir.relatosdepapel.orders.controller.model;

import com.unir.relatosdepapel.orders.repository.model.OrderStatus;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PatchOrderRequest {

    private OrderStatus status;
    private String comment;
}
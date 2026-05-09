package com.unir.relatosdepapel.orders.controller.model;

import lombok.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderRequest {

    private Integer customerId;
    private String comment;
    private List<CreateOrderItemRequest> items;
}
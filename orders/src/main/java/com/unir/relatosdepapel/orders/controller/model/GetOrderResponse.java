package com.unir.relatosdepapel.orders.controller.model;

import com.unir.relatosdepapel.orders.repository.model.OrderStatus;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetOrderResponse {

    private Integer id;
    private LocalDateTime orderDate;
    private BigDecimal total;
    private String comment;
    private OrderStatus status;
    private Integer customerId;
    private LocalDateTime createdAt;
    private List<GetOrderItemResponse> items;
}
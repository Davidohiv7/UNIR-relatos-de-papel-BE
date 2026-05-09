package com.unir.relatosdepapel.orders.service;

import com.unir.relatosdepapel.orders.controller.model.GetOrderItemResponse;
import com.unir.relatosdepapel.orders.controller.model.GetOrderResponse;
import com.unir.relatosdepapel.orders.exception.OrderNotFoundException;
import com.unir.relatosdepapel.orders.repository.OrderRepository;
import com.unir.relatosdepapel.orders.repository.model.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetOrderService {

    private final OrderRepository orderRepository;

    @Transactional(readOnly = true)
    public GetOrderResponse getOrder(Integer orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Orden no encontrada con ID: " + orderId));

        return GetOrderResponse.builder()
                .id(order.getId())
                .orderDate(order.getOrderDate())
                .total(order.getTotal())
                .comment(order.getComment())
                .status(order.getStatus())
                .customerId(order.getCustomerId())
                .createdAt(order.getCreatedAt())
                .items(order.getItems().stream()
                        .map(item -> GetOrderItemResponse.builder()
                                .idCatalogue(item.getIdCatalogue())
                                .quantity(item.getQuantity())
                                .unitPrice(item.getUnitPrice())
                                .subTotal(item.getSubTotal())
                                .build())
                        .toList())
                .build();
    }
}
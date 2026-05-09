package com.unir.relatosdepapel.orders.service;

import com.unir.relatosdepapel.orders.controller.model.GetOrderItemResponse;
import com.unir.relatosdepapel.orders.controller.model.GetOrderResponse;
import com.unir.relatosdepapel.orders.controller.model.PatchOrderRequest;
import com.unir.relatosdepapel.orders.exception.OrderNotFoundException;
import com.unir.relatosdepapel.orders.repository.OrderRepository;
import com.unir.relatosdepapel.orders.repository.model.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UpdateOrderService {

    private final OrderRepository orderRepository;

    @Transactional
    public GetOrderResponse updateOrder(Integer orderId, PatchOrderRequest request) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Orden no encontrada con ID: " + orderId));

        if (request.getStatus() != null) {
            order.setStatus(request.getStatus());
        }
        if (request.getComment() != null) {
            order.setComment(request.getComment());
        }

        Order saved = orderRepository.save(order);

        return GetOrderResponse.builder()
                .id(saved.getId())
                .orderDate(saved.getOrderDate())
                .total(saved.getTotal())
                .comment(saved.getComment())
                .status(saved.getStatus())
                .customerId(saved.getCustomerId())
                .createdAt(saved.getCreatedAt())
                .items(saved.getItems().stream()
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
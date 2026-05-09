package com.unir.relatosdepapel.orders.service;

import com.unir.relatosdepapel.orders.controller.model.GetOrderItemResponse;
import com.unir.relatosdepapel.orders.controller.model.GetOrderResponse;
import com.unir.relatosdepapel.orders.repository.OrderRepository;
import com.unir.relatosdepapel.orders.repository.model.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetOrdersService {

    private final OrderRepository orderRepository;

    @Transactional(readOnly = true)
    public List<GetOrderResponse> getOrders(Integer customerId, Integer pageSize, Integer page) {

        Pageable pageable = PageRequest.of(page, pageSize);

        List<Order> orders = customerId != null
                ? orderRepository.findByCustomerId(customerId, pageable)
                : orderRepository.findAll(pageable).getContent();

        return orders.stream()
                .map(this::mapToResponse)
                .toList();
    }

    private GetOrderResponse mapToResponse(Order order) {
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
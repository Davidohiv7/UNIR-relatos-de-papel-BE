package com.unir.relatosdepapel.orders.service;

import com.unir.relatosdepapel.orders.exception.OrderNotFoundException;
import com.unir.relatosdepapel.orders.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeleteOrderService {

    private final OrderRepository orderRepository;

    @Transactional
    public void deleteOrder(Integer orderId) {
        if (!orderRepository.existsById(orderId)) {
            throw new OrderNotFoundException("Orden no encontrada con ID: " + orderId);
        }
        orderRepository.deleteById(orderId);
    }
}
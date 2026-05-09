package com.unir.relatosdepapel.orders.service;

import com.unir.relatosdepapel.orders.controller.model.CreateOrderRequest;
import com.unir.relatosdepapel.orders.controller.model.GetOrderItemResponse;
import com.unir.relatosdepapel.orders.controller.model.GetOrderResponse;
import com.unir.relatosdepapel.orders.facade.CatalogueFacade;
import com.unir.relatosdepapel.orders.facade.model.BookResponse;
import com.unir.relatosdepapel.orders.repository.OrderRepository;
import com.unir.relatosdepapel.orders.repository.model.Order;
import com.unir.relatosdepapel.orders.repository.model.OrderItem;
import com.unir.relatosdepapel.orders.repository.model.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CreateOrderService {

    private final OrderRepository orderRepository;
    private final CatalogueFacade catalogueFacade;

    @Transactional
    public GetOrderResponse createOrder(CreateOrderRequest request) {

        List<OrderItem> items = request.getItems().stream()
                .map(itemRequest -> {
                    BookResponse book = catalogueFacade.getBook(itemRequest.getIdCatalogue());
                    BigDecimal unitPrice = book.getPrice();
                    BigDecimal subTotal = unitPrice.multiply(BigDecimal.valueOf(itemRequest.getQuantity()));

                    return OrderItem.builder()
                            .idCatalogue(itemRequest.getIdCatalogue())
                            .quantity(itemRequest.getQuantity())
                            .unitPrice(unitPrice)
                            .subTotal(subTotal)
                            .build();
                })
                .toList();

        BigDecimal total = items.stream()
                .map(OrderItem::getSubTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Order order = Order.builder()
                .orderDate(LocalDateTime.now())
                .total(total)
                .comment(request.getComment())
                .status(OrderStatus.EN_PROCESO)
                .customerId(request.getCustomerId())
                .createdAt(LocalDateTime.now())
                .items(items)
                .build();

        // Raro esto tan manual, los frameworks normalmente mapean los ids automaticamente
        items.forEach(item -> item.setOrder(order));

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
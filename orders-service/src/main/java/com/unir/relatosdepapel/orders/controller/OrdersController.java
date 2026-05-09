package com.unir.relatosdepapel.orders.controller;

import com.unir.relatosdepapel.orders.controller.model.CreateOrderRequest;
import com.unir.relatosdepapel.orders.controller.model.ErrorResponse;
import com.unir.relatosdepapel.orders.controller.model.GetOrderResponse;
import com.unir.relatosdepapel.orders.controller.model.PatchOrderRequest;
import com.unir.relatosdepapel.orders.service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Tag(name = "Orders", description = "API de gestion de pedidos de Relatos de Papel")
public class OrdersController {

    private final GetOrdersService getOrdersService;
    private final GetOrderService getOrderService;
    private final CreateOrderService createOrderService;
    private final UpdateOrderService updateOrderService;
    private final DeleteOrderService deleteOrderService;



    @GetMapping("/orders/{orderId}")
    @Operation(
            operationId = "Obtener pedido por ID",
            summary = "Devuelve el detalle de una orden especifica.",
            description = "Devuelve la orden con sus items. Lanza 404 si no existe."
    )
    @ApiResponse(
            responseCode = "200",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = GetOrderResponse.class))
    )
    @ApiResponse(
            responseCode = "404",
            description = "Orden no encontrada.",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
    )
    public ResponseEntity<GetOrderResponse> getOrder(@PathVariable Integer orderId) {
        return ResponseEntity.ok(getOrderService.getOrder(orderId));
    }

    @GetMapping("/orders")
    @Operation(
            operationId = "Obtener pedidos",
            summary = "Devuelve los pedidos de un usuario o todos los pedidos.",
            description = "Si se proporciona customerId devuelve solo los pedidos de ese cliente. Soporta paginacion."
    )
    @ApiResponse(
            responseCode = "200",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = GetOrderResponse.class))
    )
    public ResponseEntity<List<GetOrderResponse>> getOrders(
            @Parameter(description = "ID del cliente") @RequestParam(required = false) Integer customerId,
            @Parameter(description = "Numero de resultados por pagina") @RequestParam(required = false, defaultValue = "10") Integer pageSize,
            @Parameter(description = "Numero de pagina") @RequestParam(required = false, defaultValue = "0") Integer page) {
        return ResponseEntity.ok(getOrdersService.getOrders(customerId, pageSize, page));
    }

    @PostMapping("/orders")
    @Operation(
            operationId = "Crear pedido",
            summary = "Registra una nueva orden de compra.",
            description = "Valida los libros contra el microservicio catalogue, persiste la orden y descuenta el stock.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos del pedido a crear.",
                    required = true,
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CreateOrderRequest.class))
            )
    )
    @ApiResponse(
            responseCode = "201",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = GetOrderResponse.class))
    )
    @ApiResponse(
            responseCode = "400",
            description = "Solicitud invalida — items vacios o stock insuficiente.",
            content = @Content(mediaType = "application/json")
    )
    public ResponseEntity<GetOrderResponse> createOrder(
            @RequestBody CreateOrderRequest request) {
        return ResponseEntity.status(201).body(createOrderService.createOrder(request));
    }


    @PatchMapping("/orders/{orderId}")
    @Operation(
            operationId = "Actualizar pedido",
            summary = "Actualiza parcialmente una orden.",
            description = "Permite cambiar el estado y/o comentario de una orden.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Campos a actualizar.",
                    required = true,
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PatchOrderRequest.class))
            )
    )
    @ApiResponse(
            responseCode = "200",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = GetOrderResponse.class))
    )
    @ApiResponse(
            responseCode = "404",
            description = "Orden no encontrada.",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
    )
    public ResponseEntity<GetOrderResponse> updateOrder(
            @PathVariable Integer orderId,
            @RequestBody PatchOrderRequest request) {
        return ResponseEntity.ok(updateOrderService.updateOrder(orderId, request));
    }

    @DeleteMapping("/orders/{orderId}")
    @Operation(
            operationId = "Eliminar pedido",
            summary = "Elimina una orden por su ID.",
            description = "Elimina fisicamente la orden y sus items. Lanza 404 si no existe."
    )
    @ApiResponse(
            responseCode = "204",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class))
    )
    @ApiResponse(
            responseCode = "404",
            description = "Orden no encontrada.",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
    )
    public ResponseEntity<Void> deleteOrder(@PathVariable Integer orderId) {
        deleteOrderService.deleteOrder(orderId);
        return ResponseEntity.noContent().build();
    }
}
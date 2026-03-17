package com.poojatech.oms.controller;

import com.poojatech.oms.dto.OrderFullResponseDto;
import com.poojatech.oms.dto.OrderRequestDto;
import com.poojatech.oms.dto.OrderResponseDto;
import com.poojatech.oms.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public CompletableFuture<ResponseEntity<OrderResponseDto>> createOrder(
            @Valid @RequestBody OrderRequestDto request) {

        return CompletableFuture.supplyAsync(() ->
                ResponseEntity.status(HttpStatus.CREATED)
                        .body(orderService.createOrder(request)));
    }

    @GetMapping
    public CompletableFuture<ResponseEntity<List<OrderResponseDto>>> getAllOrders() {

        return CompletableFuture.supplyAsync(() ->
                ResponseEntity.ok(orderService.getAllOrders()));
    }

    @GetMapping("/{id}")
    public CompletableFuture<ResponseEntity<OrderResponseDto>> getOrderById(@PathVariable Long id) {

        return CompletableFuture.supplyAsync(() ->
                ResponseEntity.ok(orderService.getOrderById(id)));
    }

    @DeleteMapping("/{id}")
    public CompletableFuture<ResponseEntity<Void>> deleteOrderById(@PathVariable Long id) {

        return CompletableFuture.supplyAsync(() -> {
            orderService.deleteOrderById(id);
            return ResponseEntity.noContent().build();
        });
    }

    @GetMapping("/test-full-order")
    public CompletableFuture<String> testFullOrder() {

        return CompletableFuture.supplyAsync(() -> {
            orderService.createFullOrder();
            return "Order created";
        });
    }

    @GetMapping("/orders/full")
    public CompletableFuture<List<OrderFullResponseDto>> getAllOrdersWithDetails() {

        return CompletableFuture.supplyAsync(() ->
                orderService.getAllOrdersWithDetails());
    }
}
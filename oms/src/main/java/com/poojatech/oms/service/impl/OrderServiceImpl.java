
package com.poojatech.oms.service.impl;

import com.poojatech.oms.dto.OrderRequestDto;
import com.poojatech.oms.dto.OrderResponseDto;
import com.poojatech.oms.model.OrderEntity;
import com.poojatech.oms.model.OrderStatus;
import com.poojatech.oms.repository.OrderRepository;
import com.poojatech.oms.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Override
    public OrderResponseDto createOrder(OrderRequestDto request) {

        OrderEntity order = OrderEntity.builder()
                .productName(request.getProductName())
                .quantity(request.getQuantity())
                .price(request.getPrice())
                .status(OrderStatus.CREATED)
                .createdAt(LocalDateTime.now())
                .build();

        OrderEntity saved = orderRepository.save(order);

        return new OrderResponseDto(
                saved.getId(),
                saved.getProductName(),
                saved.getQuantity(),
                saved.getPrice(),
                saved.getStatus(),
                "Order created successfully"
        );
    }

    @Override
    public List<OrderResponseDto> getAllOrders() {
        return orderRepository.findAll()
                .stream()
                .map(o -> new OrderResponseDto(
                        o.getId(),
                        o.getProductName(),
                        o.getQuantity(),
                        o.getPrice(),
                        o.getStatus(),
                        "Success"
                ))
                .toList();
    }

    @Override
    public OrderResponseDto getOrderById(Long id) {
        OrderEntity order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        return new OrderResponseDto(
                order.getId(),
                order.getProductName(),
                order.getQuantity(),
                order.getPrice(),
                order.getStatus(),
                "Fetched successfully"
        );
    }
}

package com.poojatech.oms.service;

import com.poojatech.oms.dto.OrderRequestDto;
import com.poojatech.oms.dto.OrderResponseDto;
import com.poojatech.oms.model.OrderEntity;
import com.poojatech.oms.model.OrderStatus;
import com.poojatech.oms.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public  class OrderServiceImpl implements OrderService {

    private final OrderRepository repository;

    @Override
    public OrderResponseDto createOrder(OrderRequestDto request) {

        OrderEntity entity = OrderEntity.builder()
                .productName(request.getProductName())
                .quantity(request.getQuantity())
                .price(request.getPrice())
                .status(OrderStatus.CREATED)
                .createdAt(LocalDateTime.now())
                .build();

        OrderEntity saved = repository.save(entity);

        return new OrderResponseDto(saved.getId(),
                saved.getProductName(),
                saved.getQuantity(),
                saved.getPrice(),
                saved.getStatus(),
                "Order created successfully"
        );
    }
}

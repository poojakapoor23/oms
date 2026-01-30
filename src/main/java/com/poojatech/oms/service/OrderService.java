package com.poojatech.oms.service;

import com.poojatech.oms.dto.OrderRequestDto;
import com.poojatech.oms.dto.OrderResponseDto;

import java.util.List;

public interface OrderService {

    OrderResponseDto createOrder(OrderRequestDto request);

    List<OrderResponseDto> getAllOrders();

    OrderResponseDto getOrderById(Long id);

    void deleteOrderById(Long id);
}





package com.poojatech.oms.service;

import com.poojatech.oms.dto.OrderRequestDto;
import com.poojatech.oms.dto.OrderResponseDto;

public interface OrderService {

    OrderResponseDto createOrder(OrderRequestDto request);
}




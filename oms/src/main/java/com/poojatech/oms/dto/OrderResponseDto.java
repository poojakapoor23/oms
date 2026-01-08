package com.poojatech.oms.dto;


import com.poojatech.oms.model.OrderStatus;
import java.math.BigDecimal;
public class OrderResponseDto {
    private Long orderId;
    private String productName;
    private Integer quantity;
    private BigDecimal price;
    private OrderStatus status;
    private String message;

    public OrderResponseDto(Long id, String productName, Integer quantity, BigDecimal price, OrderStatus status, String orderCreatedSuccessfully) {
    }
}
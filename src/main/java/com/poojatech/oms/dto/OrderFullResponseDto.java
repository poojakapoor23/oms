package com.poojatech.oms.dto;

import com.poojatech.oms.model.OrderStatus;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class OrderFullResponseDto {

    private Long id;
    private String productName;
    private Integer quantity;
    private BigDecimal price;
    private OrderStatus status;

    private AddressDto address;
    private List<OrderItemDto> items;
    private List<ProductDto> products;
}
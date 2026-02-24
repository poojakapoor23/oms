package com.poojatech.oms.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemDto {
    private String productName;
    private Integer quantity;
}
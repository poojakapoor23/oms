package com.poojatech.oms.dto;
import com.poojatech.oms.model.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
@AllArgsConstructor
@Getter
@Setter
public class OrderResponseDto {

    private Long orderId;
    private String productName;
    private Integer quantity;
    private BigDecimal price;
    private OrderStatus status;
    private String message;

    public OrderResponseDto(long orderId, String created) {
    }

    public OrderResponseDto() {

    }
}

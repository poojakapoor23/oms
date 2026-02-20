package com.poojatech.oms;
import java.math.BigDecimal;
import java.util.Optional;

import com.poojatech.oms.dto.OrderRequestDto;
import com.poojatech.oms.dto.OrderResponseDto;
import com.poojatech.oms.model.OrderEntity;
import com.poojatech.oms.repository.OrderRepository;
import com.poojatech.oms.service.OrderService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class OmsApplicationTests {

	@Autowired
    private OrderService orderService;

    @MockBean
    private OrderRepository orderRepository;

    @Test
	void shouldGetOrderById () {
        //arrange(given)
        OrderEntity order = new OrderEntity();
        order.setId(1L);
        Mockito.when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        //act(when)
        OrderResponseDto response = orderService.getOrderById(1L);

        //assert(then)
        assertNotNull(response);
	}

    @Test
    void shouldThrowExceptionWhenOrderNotFound() {
        Mockito.when(orderRepository.findById(2L))
                .thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                () -> orderService.getOrderById(2L));
    }
    @Test
    void shouldCreateOrder() {
        OrderEntity savedOrder = new OrderEntity();
        savedOrder.setId(1L);

        Mockito.when(orderRepository.save(Mockito.any(OrderEntity.class)))
                .thenReturn(savedOrder);

        OrderRequestDto request = new OrderRequestDto();
        request.setProductName("Mobile");
        request.setQuantity(1);
        request.setPrice(new BigDecimal("3000"));

        OrderResponseDto response = orderService.createOrder(request);

        assertNotNull(response);
    }


}

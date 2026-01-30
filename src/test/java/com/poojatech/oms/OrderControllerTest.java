package com.poojatech.oms;

import com.poojatech.oms.controller.OrderController;
import com.poojatech.oms.dto.OrderResponseDto;
import com.poojatech.oms.model.OrderStatus;
import com.poojatech.oms.service.OrderService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
@AutoConfigureMockMvc(addFilters = false)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @Test
    void shouldReturnOrderById() throws Exception {

        // Arrange
        OrderResponseDto dto = new OrderResponseDto();
        dto.setOrderId(1L);
        dto.setStatus(OrderStatus.CREATED);
        dto.setMessage("Order fetched successfully");

        Mockito.when(orderService.getOrderById(1L))
                .thenReturn(dto);

        // Act + Assert
        mockMvc.perform(get("/api/orders/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("CREATED"));
    }
}

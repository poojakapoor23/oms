
package com.poojatech.oms.service.impl;

import com.poojatech.oms.dto.*;
import com.poojatech.oms.model.*;
import com.poojatech.oms.repository.OrderRepository;
import com.poojatech.oms.repository.ProductRepository;
import com.poojatech.oms.service.OrderService;
import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
//import java.util.Arrays;
import java.util.List;
//import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {


    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;


    @Transactional
    public void createFullOrder() {

        OrderEntity order = new OrderEntity();
        order.setProductName("Laptop");
        order.setQuantity(1);
        order.setPrice(new BigDecimal("3000"));
        order.setStatus(OrderStatus.CREATED);
        order.setCreatedAt(LocalDateTime.now());

        // Address
        Address address = new Address();
        address.setStreet("MG Road");
        address.setCity("Pune");
        address.setOrder(order);
        order.setAddress(address);

        // Items
        OrderItem item1 = new OrderItem();
        item1.setProductName("Mouse");
        item1.setQuantity(2);
        item1.setOrder(order);

        OrderItem item2 = new OrderItem();
        item2.setProductName("Keyboard");
        item2.setQuantity(1);
        item2.setOrder(order);

        order.setItems(new ArrayList<>(List.of(item1, item2)));

        // Products
        Product p1 = new Product();
        p1.setName("Laptop");

        Product p2 = new Product();
        p2.setName("Accessories");

        // SAVE PRODUCTS FIRST
        productRepository.saveAll(List.of(p1, p2));

        order.setProducts(new ArrayList<>(List.of(p1, p2)));

        // SAVE ORDER
        orderRepository.save(order);
    }


    @Override
    @Transactional
    public OrderResponseDto createOrder(OrderRequestDto request) {

        OrderEntity order = OrderEntity.builder()
                .productName(request.getProductName())
                .quantity(request.getQuantity())
                .price(request.getPrice())
                .status(OrderStatus.CREATED)
                .createdAt(LocalDateTime.now())
                .build();

        OrderEntity saved = orderRepository.save(order);

        //rollback condition
        if (request.getPrice().compareTo(new BigDecimal("5000")) > 0) {
            throw new RuntimeException("Price too high, rolling back!");
        }

        // update status
        saved.setStatus(OrderStatus.CONFIRMED);

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
    public void deleteOrderById(Long id) {

        // Step 1: Check if order exists (reuse get logic)
        OrderEntity order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        // Step 2: Delete the order
        orderRepository.delete(order);
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

    @Transactional
    @Override
    public List<OrderFullResponseDto> getAllOrdersWithDetails() {

        return orderRepository.findAll()
                .stream()
                .map(order -> {

                    OrderFullResponseDto dto = new OrderFullResponseDto();

                    dto.setId(order.getId());
                    dto.setProductName(order.getProductName());
                    dto.setQuantity(order.getQuantity());
                    dto.setPrice(order.getPrice());
                    dto.setStatus(order.getStatus());

                    // Address mapping
                    if (order.getAddress() != null) {
                        AddressDto addressDto = new AddressDto();
                        addressDto.setStreet(order.getAddress().getStreet());
                        addressDto.setCity(order.getAddress().getCity());
                        dto.setAddress(addressDto);
                    }

                    // OrderItems mapping
                    if (order.getItems() != null) {
                        dto.setItems(
                                order.getItems().stream().map(item -> {
                                    OrderItemDto itemDto = new OrderItemDto();
                                    itemDto.setProductName(item.getProductName());
                                    itemDto.setQuantity(item.getQuantity());
                                    return itemDto;
                                }).toList()
                        );
                    }

                    // Products mapping
                    if (order.getProducts() != null) {
                        dto.setProducts(
                                order.getProducts().stream().map(product -> {
                                    ProductDto productDto = new ProductDto();
                                    productDto.setId(product.getId());
                                    productDto.setName(product.getName());
                                    return productDto;
                                }).toList()
                        );
                    }

                    return dto;
                })
                .toList();
    }
}


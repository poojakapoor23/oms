package com.poojatech.oms.service.impl;

import com.poojatech.oms.dto.*;
import com.poojatech.oms.model.*;
import com.poojatech.oms.repository.OrderRepository;
import com.poojatech.oms.repository.ProductRepository;
import com.poojatech.oms.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    @Override
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

        productRepository.saveAll(List.of(p1, p2));

        order.setProducts(new ArrayList<>(List.of(p1, p2)));

        orderRepository.save(order);
    }

    @Override
    @Transactional
    public OrderResponseDto createOrder(OrderRequestDto request) {

        // Validate BEFORE DB call
        if (request.getPrice().compareTo(new BigDecimal("5000")) > 0) {
            throw new RuntimeException("Price too high!");
        }

        OrderEntity order = OrderEntity.builder()
                .productName(request.getProductName())
                .quantity(request.getQuantity())
                .price(request.getPrice())
                .status(OrderStatus.CREATED)
                .createdAt(LocalDateTime.now())
                .build();

        OrderEntity saved = orderRepository.save(order);

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
    @Transactional(readOnly = true)
    public List<OrderResponseDto> getAllOrders() {

        List<OrderEntity> orders = orderRepository.findAll();

        List<OrderResponseDto> response = new ArrayList<>(orders.size());

        for (OrderEntity o : orders) {
            response.add(new OrderResponseDto(
                    o.getId(),
                    o.getProductName(),
                    o.getQuantity(),
                    o.getPrice(),
                    o.getStatus(),
                    "Success"
            ));
        }

        return response;
    }

    @Override
    @Transactional
    public void deleteOrderById(Long id) {

        if (!orderRepository.existsById(id)) {
            throw new RuntimeException("Order not found");
        }

        orderRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
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

    @Override
    @Transactional(readOnly = true)
    public List<OrderFullResponseDto> getAllOrdersWithDetails() {

        List<OrderEntity> orders = orderRepository.findAll();

        List<OrderFullResponseDto> result = new ArrayList<>();

        for (OrderEntity order : orders) {

            OrderFullResponseDto dto = new OrderFullResponseDto();

            dto.setId(order.getId());
            dto.setProductName(order.getProductName());
            dto.setQuantity(order.getQuantity());
            dto.setPrice(order.getPrice());
            dto.setStatus(order.getStatus());

            // Address
            if (order.getAddress() != null) {
                AddressDto addressDto = new AddressDto();
                addressDto.setStreet(order.getAddress().getStreet());
                addressDto.setCity(order.getAddress().getCity());
                dto.setAddress(addressDto);
            }

            // Items
            if (order.getItems() != null) {

                List<OrderItemDto> items = new ArrayList<>();

                for (OrderItem item : order.getItems()) {
                    OrderItemDto itemDto = new OrderItemDto();
                    itemDto.setProductName(item.getProductName());
                    itemDto.setQuantity(item.getQuantity());
                    items.add(itemDto);
                }

                dto.setItems(items);
            }

            // Products
            if (order.getProducts() != null) {

                List<ProductDto> products = new ArrayList<>();

                for (Product product : order.getProducts()) {
                    ProductDto productDto = new ProductDto();
                    productDto.setId(product.getId());
                    productDto.setName(product.getName());
                    products.add(productDto);
                }

                dto.setProducts(products);
            }

            result.add(dto);
        }

        return result;
    }
}
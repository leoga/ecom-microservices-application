package com.leoga.ecom.order.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class CartItemResponse {
    private Long id;
//    private UserResponse user;
//    private ProductResponse product;
    private String userId;
    private String productId;
    private Integer quantity;
    private BigDecimal price;
}

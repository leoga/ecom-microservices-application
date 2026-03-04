package com.leoga.ecom.order.controllers;

import com.leoga.ecom.order.dto.CartItemRequest;
import com.leoga.ecom.order.dto.CartItemResponse;
import com.leoga.ecom.order.services.CartItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cart")
public class CartItemController {

    private final CartItemService cartItemService;

    @PostMapping
    public ResponseEntity<String> addToCart(
            @RequestHeader("X-User-ID") String userId,
            @RequestBody CartItemRequest cartItemRequest) {
        cartItemRequest.setUserId(userId);
        if (!cartItemService.addToCart(cartItemRequest)) {
            return ResponseEntity.badRequest().body("Product Out of Stock or User Not Found or Product Not Found");
        } else {
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
    }

    @DeleteMapping("/items/{productId}")
    public ResponseEntity<String> removeFromCart(
            @RequestHeader("X-User-ID") String userId,
            @PathVariable String productId) {
        boolean deleted = cartItemService.deleteItemFromCart(userId, productId);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<CartItemResponse>> getCartItems(
            @RequestHeader("X-User-ID") String userId) {
        List<CartItemResponse> cartItems = cartItemService.getCartItems(userId);
        return cartItems.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(cartItems);
    }
}

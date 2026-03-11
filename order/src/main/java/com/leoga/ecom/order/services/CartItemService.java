package com.leoga.ecom.order.services;

import com.leoga.ecom.order.clients.ProductServiceClient;
import com.leoga.ecom.order.clients.UserServiceClient;
import com.leoga.ecom.order.dto.CartItemRequest;
import com.leoga.ecom.order.dto.CartItemResponse;
import com.leoga.ecom.order.dto.ProductResponse;
import com.leoga.ecom.order.dto.UserResponse;
import com.leoga.ecom.order.mappers.CartItemMapper;
import com.leoga.ecom.order.model.CartItem;
import com.leoga.ecom.order.repositories.CartItemRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional
@Service
public class CartItemService {

    private final CartItemRepository cartItemRepository;
    private final UserServiceClient userServiceClient;
    private final ProductServiceClient productServiceClient;
    private final CartItemMapper cartItemMapper;

    public boolean addToCart(CartItemRequest cartItemRequest) {

        // Look for a product
        Optional<ProductResponse> productOpt = productServiceClient
                .getProductById(Long.valueOf(cartItemRequest.getProductId()));
        if (productOpt.isEmpty()) return false;

        if (productOpt.get().getStockQuantity() < cartItemRequest.getQuantity()) return false;

        Optional<UserResponse> userOpt = userServiceClient
                .getUserById(cartItemRequest.getUserId());
        if (userOpt.isEmpty()) return false;

        CartItem existingCartItem = cartItemRepository.findByUserIdAndProductId(cartItemRequest.getUserId(), cartItemRequest.getProductId());
        if (null != existingCartItem) {
            // Update the quantity and price
            existingCartItem.setQuantity(existingCartItem.getQuantity() + cartItemRequest.getQuantity());
            existingCartItem.setPrice(productOpt.get().getPrice().multiply(BigDecimal.valueOf(existingCartItem.getQuantity())));
            cartItemRepository.save(existingCartItem);
        } else {
            // Create a new cart item
            CartItem cartItem = cartItemMapper.toEntity(cartItemRequest);
            cartItem.setPrice(productOpt.get().getPrice().multiply(BigDecimal.valueOf(cartItemRequest.getQuantity())));
            cartItemRepository.save(cartItem);
        }

        return true;
    }

    public boolean deleteItemFromCart(String userId, String productId) {


        CartItem existingCartItem = cartItemRepository.findByUserIdAndProductId(userId, productId);
        if (null != existingCartItem) {
            cartItemRepository.delete(existingCartItem);
            return true;
        }

        return false;
    }

    public void clearCart(String userId) {
        cartItemRepository.deleteByUserId(userId);
    }

    public List<CartItemResponse> getCartItems(String userId) {
        return cartItemMapper.toCartItemResponseList(cartItemRepository.findByUserId(userId), userServiceClient, productServiceClient);
    }
}

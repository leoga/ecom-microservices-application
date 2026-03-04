package com.leoga.ecom.order.services;

import com.leoga.ecom.order.dto.CartItemRequest;
import com.leoga.ecom.order.dto.CartItemResponse;
import com.leoga.ecom.order.model.CartItem;
import com.leoga.ecom.order.repositories.CartItemRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class CartItemService {

//    private final UserService userService;
//    private final ProductService productService;
//    private final UserRepository userRepository;
//    private final ProductRepository productRepository;
    private final CartItemRepository cartItemRepository;

    public boolean addToCart(CartItemRequest cartItemRequest) {

        // Look for a product
//        Optional<Product> productOpt = productRepository.findById(cartItemRequest.getProductId());
//        if (productOpt.isEmpty()) return false;
//
//        if (productOpt.get().getStockQuantity() < cartItemRequest.getQuantity()) return false;
//
//        Optional<User> userOpt = userRepository.findById(cartItemRequest.getUserId());
//        if (userOpt.isEmpty()) return false;

        CartItem existingCartItem = cartItemRepository.findByUserIdAndProductId(cartItemRequest.getUserId(), cartItemRequest.getProductId());
        if (null != existingCartItem) {
            // Update the quantity and price
            existingCartItem.setQuantity(existingCartItem.getQuantity() + cartItemRequest.getQuantity());
            existingCartItem.setPrice(existingCartItem.getPrice().multiply(BigDecimal.valueOf(existingCartItem.getQuantity())));
            cartItemRepository.save(existingCartItem);
        } else {
            // Create a new cart item
            CartItem cartItem = new CartItem();
            cartItem.setUserId(cartItemRequest.getUserId());
            cartItem.setProductId(cartItemRequest.getProductId());
            cartItem.setQuantity(cartItemRequest.getQuantity());
            //cartItem.setPrice(productOpt.get().getPrice().multiply(BigDecimal.valueOf(cartItemRequest.getQuantity())));
            cartItem.setPrice(BigDecimal.valueOf(1000));
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
        return cartItemRepository.findByUserId(userId).stream()
                .map(this::mapToCartItemResponse).toList();
    }

    private CartItemResponse mapToCartItemResponse(CartItem cartItem) {
        CartItemResponse cartItemResponse = new CartItemResponse();
        cartItemResponse.setId(cartItem.getId());
        cartItemResponse.setUserId(cartItem.getUserId());
        cartItemResponse.setProductId(cartItem.getProductId());
        cartItemResponse.setQuantity(cartItem.getQuantity());
        cartItemResponse.setPrice(cartItem.getPrice());
        return cartItemResponse;
    }
}

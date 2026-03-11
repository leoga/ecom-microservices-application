package com.leoga.ecom.order.mappers;

import com.leoga.ecom.order.clients.ProductServiceClient;
import com.leoga.ecom.order.clients.UserServiceClient;
import com.leoga.ecom.order.configuration.MapperConfigGlobal;
import com.leoga.ecom.order.dto.CartItemRequest;
import com.leoga.ecom.order.dto.CartItemResponse;
import com.leoga.ecom.order.model.CartItem;
import org.mapstruct.*;

import java.util.List;

@Mapper(config = MapperConfigGlobal.class)
public interface CartItemMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    CartItem toEntity(CartItemRequest cartItemRequest);

    CartItemResponse toCartItemResponse(CartItem cartItem,
                                        @Context UserServiceClient userServiceClient,
                                        @Context ProductServiceClient productServiceClient);

    List<CartItemResponse> toCartItemResponseList(List<CartItem> cartItems,
                                                  @Context UserServiceClient userServiceClient,
                                                  @Context ProductServiceClient productServiceClient);

    @AfterMapping
    default void enrichWithRemoteData(CartItem cartItem,
                                      @MappingTarget CartItemResponse cartItemResponse,
                                      @Context UserServiceClient userServiceClient,
                                      @Context ProductServiceClient productServiceClient) {
        if (null != cartItem.getUserId()) {
            cartItemResponse.setUser(userServiceClient.getUserById(cartItem.getUserId()).orElse(null));
        }
        if (null != cartItem.getProductId()) {
            cartItemResponse.setProduct(productServiceClient.getProductById(Long.valueOf(cartItem.getProductId())).orElse(null));
        }
    }
}

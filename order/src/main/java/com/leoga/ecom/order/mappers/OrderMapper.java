package com.leoga.ecom.order.mappers;

import com.leoga.ecom.order.clients.ProductServiceClient;
import com.leoga.ecom.order.configuration.MapperConfigGlobal;
import com.leoga.ecom.order.dto.OrderItemDTO;
import com.leoga.ecom.order.dto.OrderResponse;
import com.leoga.ecom.order.dto.ProductRequest;
import com.leoga.ecom.order.dto.ProductResponse;
import com.leoga.ecom.order.model.CartItem;
import com.leoga.ecom.order.model.Order;
import com.leoga.ecom.order.model.OrderItem;
import org.mapstruct.*;

import java.util.List;

@Mapper(config = MapperConfigGlobal.class)
public interface OrderMapper {

    OrderItemDTO toOrderItemDTO(OrderItem orderItem);

    @Mapping(target = "items", source = "items")
    OrderResponse toOrderResponse(Order order);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "order", source = "order")
    OrderItem fromCartItemToOrderItem(CartItem cartItem, Order order,
                                      @Context ProductServiceClient productServiceClient);

    List<OrderItemDTO> toOrderItemDTOList(List<OrderItem> orderItems);

    @AfterMapping
    default void setOrderAndUpdateProductQuantity(@MappingTarget OrderItem orderItem,
                                       @Context ProductServiceClient productServiceClient) {
        ProductResponse product = productServiceClient.getProductById(Long.valueOf(orderItem.getProductId())).orElse(null);
        if (null != product) {
            ProductRequest productRequest = new ProductRequest();
            productRequest.setStockQuantity(product.getStockQuantity() - orderItem.getQuantity());
            productServiceClient.updateProduct(Long.valueOf(orderItem.getProductId()), productRequest);
        }
    }
}

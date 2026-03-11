package com.leoga.ecom.product.mappers;

import com.leoga.ecom.product.configuration.MapperConfigGlobal;
import com.leoga.ecom.product.dto.ProductRequest;
import com.leoga.ecom.product.dto.ProductResponse;
import com.leoga.ecom.product.model.Product;
import org.mapstruct.*;

import java.util.List;

@Mapper(config = MapperConfigGlobal.class)
public interface ProductMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Product toEntity(ProductRequest request);

    ProductResponse toProductResponse(Product product);

    List<ProductResponse> toResponseList(List<Product> products);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void patchProduct(ProductRequest request, @MappingTarget Product product);
}

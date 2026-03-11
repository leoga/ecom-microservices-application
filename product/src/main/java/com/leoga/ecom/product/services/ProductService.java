package com.leoga.ecom.product.services;

import com.leoga.ecom.product.dto.ProductRequest;
import com.leoga.ecom.product.dto.ProductResponse;
import com.leoga.ecom.product.mappers.ProductMapper;
import com.leoga.ecom.product.model.Product;
import com.leoga.ecom.product.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public List<ProductResponse> getAllProducts() {
        return productMapper.toResponseList(productRepository.findByActiveTrue());
    }

    public ProductResponse createProduct(ProductRequest productRequest) {
        Product product = productMapper.toEntity(productRequest);

        Product savedProduct = productRepository.save(product);
        return productMapper.toProductResponse(savedProduct);
    }

    public boolean deleteProduct(Long id) {
        return productRepository.findById(id)
                .map(product -> {
                    product.setActive(false);
                    productRepository.save(product);
                    return true;
                }).orElse(false);
    }

    public Optional<ProductResponse> updateProduct(Long id, ProductRequest updatedProduct) {
        return productRepository.findById(id)
                .map(existingProduct -> {
                    productMapper.patchProduct(updatedProduct, existingProduct);
                    Product savedProduct = productRepository.save(existingProduct);
                    return productMapper.toProductResponse(savedProduct);
                });
    }

    public List<ProductResponse> SearchProducts(String keyword) {
        return productMapper.toResponseList(productRepository.searchProducts(keyword));
    }

    public ProductResponse getProductById(Long id) {
        return productRepository.findById(id).map(productMapper::toProductResponse).orElse(null);
    }
}

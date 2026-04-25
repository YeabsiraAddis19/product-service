package com.ctbe.yeabsira.productservice.mapper;

import com.ctbe.yeabsira.productservice.dto.ProductRequest;
import com.ctbe.yeabsira.productservice.dto.ProductResponse;
import com.ctbe.yeabsira.productservice.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public Product toEntity(ProductRequest request) {
        return Product.builder()
                .sku(request.getSku())
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .stockQty(request.getStockQuantity())
                .category(request.getCategory())
                .active(request.getActive() != null ? request.getActive() : true)
                .build();
    }

    public ProductResponse toResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .sku(product.getSku())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .stockQuantity(product.getStockQty())
                .category(product.getCategory())
                .active(product.getActive())
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .build();
    }

    public void updateFromRequest(ProductRequest request, Product product) {
        product.setSku(request.getSku());
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStockQty(request.getStockQuantity());
        product.setCategory(request.getCategory());
        if (request.getActive() != null) {
            product.setActive(request.getActive());
        }
    }
}
package com.ctbe.yeabsira.productservice;

import com.ctbe.yeabsira.productservice.dto.ProductRequest;
import com.ctbe.yeabsira.productservice.service.ProductService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import java.math.BigDecimal;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Product Service API", description = "API for managing products", version = "1.0.0"))
public class ProductServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner init(ProductService productService) {
        return args -> {
            // Seed data
            ProductRequest laptop = ProductRequest.builder()
                    .sku("LAP001")
                    .name("Gaming Laptop")
                    .description("High-performance gaming laptop with RTX graphics")
                    .price(new BigDecimal("1299.99"))
                    .stockQuantity(10)
                    .category("Electronics")
                    .active(true)
                    .build();
            
            ProductRequest mouse = ProductRequest.builder()
                    .sku("MOU001")
                    .name("Wireless Mouse")
                    .description("Ergonomic wireless mouse with long battery life")
                    .price(new BigDecimal("29.99"))
                    .stockQuantity(50)
                    .category("Electronics")
                    .active(true)
                    .build();
            
            ProductRequest shirt = ProductRequest.builder()
                    .sku("SHR001")
                    .name("Cotton T-Shirt")
                    .description("Comfortable 100% cotton t-shirt")
                    .price(new BigDecimal("19.99"))
                    .stockQuantity(100)
                    .category("Clothing")
                    .active(true)
                    .build();
            
            productService.createProduct(laptop);
            productService.createProduct(mouse);
            productService.createProduct(shirt);
        };
    }
}

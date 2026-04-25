package com.ctbe.yeabsira.productservice.service;

import com.ctbe.yeabsira.productservice.dto.ProductRequest;
import com.ctbe.yeabsira.productservice.dto.ProductResponse;
import com.ctbe.yeabsira.productservice.exception.ResourceNotFoundException;
import com.ctbe.yeabsira.productservice.mapper.ProductMapper;
import com.ctbe.yeabsira.productservice.model.Product;
import com.ctbe.yeabsira.productservice.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductServiceImpl productService;

    private Product product;
    private ProductRequest productRequest;
    private ProductResponse productResponse;

    @BeforeEach
    void setUp() {
        product = Product.builder()
                .id(1L)
                .sku("SKU123")
                .name("Test Product")
                .description("Test Description")
                .price(new BigDecimal("29.99"))
                .stockQuantity(100)
                .category("Electronics")
                .active(true)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        productRequest = ProductRequest.builder()
                .sku("SKU123")
                .name("Test Product")
                .description("Test Description")
                .price(new BigDecimal("29.99"))
                .stockQuantity(100)
                .category("Electronics")
                .active(true)
                .build();

        productResponse = ProductResponse.builder()
                .id(1L)
                .sku("SKU123")
                .name("Test Product")
                .description("Test Description")
                .price(new BigDecimal("29.99"))
                .stockQuantity(100)
                .category("Electronics")
                .active(true)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    @Test
    void createProduct_ShouldReturnProductResponse() {
        given(productRepository.findBySku(any(String.class))).willReturn(Optional.empty());
        given(productMapper.toEntity(any(ProductRequest.class))).willReturn(product);
        given(productRepository.save(any(Product.class))).willReturn(product);
        given(productMapper.toResponse(any(Product.class))).willReturn(productResponse);

        ProductResponse result = productService.createProduct(productRequest);

        assertThat(result).isNotNull();
        assertThat(result.getSku()).isEqualTo("SKU123");
        verify(productRepository).save(any(Product.class));
    }

    @Test
    void createProduct_WithExistingSku_ShouldThrowException() {
        given(productRepository.findBySku(any(String.class))).willReturn(Optional.of(product));

        assertThatThrownBy(() -> productService.createProduct(productRequest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("already exists");

        verify(productRepository, never()).save(any(Product.class));
    }

    @Test
    void getProductById_ShouldReturnProductResponse() {
        given(productRepository.findById(anyLong())).willReturn(Optional.of(product));
        given(productMapper.toResponse(any(Product.class))).willReturn(productResponse);

        ProductResponse result = productService.getProductById(1L);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
    }

    @Test
    void getProductById_NotFound_ShouldThrowException() {
        given(productRepository.findById(anyLong())).willReturn(Optional.empty());

        assertThatThrownBy(() -> productService.getProductById(999L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("not found");
    }

    @Test
    void getProductBySku_ShouldReturnProductResponse() {
        given(productRepository.findBySku(any(String.class))).willReturn(Optional.of(product));
        given(productMapper.toResponse(any(Product.class))).willReturn(productResponse);

        ProductResponse result = productService.getProductBySku("SKU123");

        assertThat(result).isNotNull();
        assertThat(result.getSku()).isEqualTo("SKU123");
    }

    @Test
    void getAllProducts_ShouldReturnProductList() {
        given(productRepository.findAll()).willReturn(Arrays.asList(product));
        given(productMapper.toResponse(any(Product.class))).willReturn(productResponse);

        List<ProductResponse> result = productService.getAllProducts();

        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
    }

    @Test
    void getProductsByCategory_ShouldReturnProductList() {
        given(productRepository.findByCategory(any(String.class))).willReturn(Arrays.asList(product));
        given(productMapper.toResponse(any(Product.class))).willReturn(productResponse);

        List<ProductResponse> result = productService.getProductsByCategory("Electronics");

        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
    }

    @Test
    void getActiveProducts_ShouldReturnProductList() {
        given(productRepository.findByActiveTrue()).willReturn(Arrays.asList(product));
        given(productMapper.toResponse(any(Product.class))).willReturn(productResponse);

        List<ProductResponse> result = productService.getActiveProducts();

        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
    }

    @Test
    void searchProductsByName_ShouldReturnProductList() {
        given(productRepository.findByNameContainingIgnoreCase(any(String.class))).willReturn(Arrays.asList(product));
        given(productMapper.toResponse(any(Product.class))).willReturn(productResponse);

        List<ProductResponse> result = productService.searchProductsByName("Test");

        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
    }

    @Test
    void updateProduct_ShouldReturnUpdatedProductResponse() {
        given(productRepository.findById(anyLong())).willReturn(Optional.of(product));
        given(productRepository.findBySku(any(String.class))).willReturn(Optional.empty());
        given(productRepository.save(any(Product.class))).willReturn(product);
        given(productMapper.toResponse(any(Product.class))).willReturn(productResponse);

        ProductResponse result = productService.updateProduct(1L, productRequest);

        assertThat(result).isNotNull();
        assertThat(result.getSku()).isEqualTo("SKU123");
    }

    @Test
    void deleteProduct_ShouldDeleteProduct() {
        given(productRepository.findById(anyLong())).willReturn(Optional.of(product));
        doNothing().when(productRepository).delete(any(Product.class));

        productService.deleteProduct(1L);

        verify(productRepository).delete(any(Product.class));
    }

    @Test
    void deleteProduct_NotFound_ShouldThrowException() {
        given(productRepository.findById(anyLong())).willReturn(Optional.empty());

        assertThatThrownBy(() -> productService.deleteProduct(999L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("not found");
    }
}

package com.productServices.product.service;

import com.productServices.product.Product;
import com.productServices.product.dto.ProductResponseDTO;
import com.productServices.product.mapper.ProductMapper;
import com.productServices.product.repo.ProductRepository;
import com.productServices.product.repo.ProductSearchRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductQueryServiceTest {

    @Mock
    ProductRepository productRepository;
    @Mock
    ProductMapper productMapper;
    @Mock
    ProductSearchRepository productSearchRepository;

    @InjectMocks
    ProductQueryService productQueryService;

    @Test
    void getProducts() {
        System.out.println("Products");
    }

    @Test
    void shouldReturnProductWhenProductExists() {
        System.out.println("shouldReturnProductWhenProductExists");
        UUID id = UUID.randomUUID();
        Product product = new Product();
        ProductResponseDTO dto = Mockito.mock(ProductResponseDTO.class);

        when(productRepository.findById(id)).thenReturn(Optional.of(product));
        when(productMapper.toResponse(product)).thenReturn(dto);

        ProductResponseDTO response = productQueryService.getProduct(id);

        assertNotNull(response);
        assertEquals(dto, response);

        //Mockito check
        //Did service actually call repository?
        verify(productRepository).findById(id);
        //Did mapper get called?
        verify(productMapper).toResponse(product);
    }

    @Test
    void search() {
        System.out.println("Search");
    }

    @Test
    void shouldThrowExceptionWhenProductNotFound() {
        System.out.println("shouldThrowExceptionWhenProductNotFound");

        UUID id = UUID.randomUUID();
        when(productRepository.findById(id)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                        () -> productQueryService.getProduct(id));

        assertEquals("Product not found", ex.getMessage());

        verify(productRepository).findById(id);
        verify(productMapper, never()).toResponse(any());
    }
}
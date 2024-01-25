package dev.giba.labs.acme.store.shoppingcart.service.impl;

import dev.giba.labs.acme.store.shoppingcart.model.ProductEntity;
import dev.giba.labs.acme.store.shoppingcart.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {
    @Mock
    private ProductRepository mockedProductRepository;
    private ProductServiceImpl productServiceImpl;

    @BeforeEach
    void beforeEachTest() {
        reset(this.mockedProductRepository);
        this.productServiceImpl = new ProductServiceImpl(this.mockedProductRepository);
    }

    @Test
    @DisplayName("Deve obter um produto por ID corretamente")
    void shouldGetByIdCorrectly() {
        //Given
        var existentID = 1L;
        var productDescription = "Product 01";
        var productPrice = BigDecimal.ONE;

        var existentProduct = new ProductEntity();
        existentProduct.setId(existentID);
        existentProduct.setDescription(productDescription);
        existentProduct.setPrice(productPrice);

        when(this.mockedProductRepository.findById(existentID)).thenReturn(Optional.of(existentProduct));

        //When
        var optionalProduct = this.productServiceImpl.findById(existentID);

        //Then
        verify(this.mockedProductRepository, times(1)).findById(existentID);

        assertTrue(optionalProduct.isPresent());
        assertEquals(existentID, optionalProduct.get().getId());
        assertEquals(productDescription, optionalProduct.get().getDescription());
        assertEquals(productPrice, optionalProduct.get().getPrice());

    }

    @Test
    @DisplayName("Deve retornar vazio quando não existir Id")
    void shouldReturnEmptyWhenGivenNonexistentId() {
        //Given
        var nonexistentId = 1000L;

        when(this.mockedProductRepository.findById(nonexistentId)).thenReturn(Optional.empty());

        //When
        var optionalProduct = this.productServiceImpl.findById(nonexistentId);

        //Then
        verify(this.mockedProductRepository, times(1)).findById(nonexistentId);

        assertFalse(optionalProduct.isPresent());

    }

}

package dev.giba.labs.acme.store.shoppingcart.controller;

import dev.giba.labs.acme.store.shoppingcart.model.Item;
import dev.giba.labs.acme.store.shoppingcart.model.ProductEntity;
import dev.giba.labs.acme.store.shoppingcart.model.ShoppingCart;
import dev.giba.labs.acme.store.shoppingcart.service.ProductService;
import dev.giba.labs.acme.store.shoppingcart.service.ShoppingCartService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ShoppingCartControllerTest {
    private static final long PRODUCT_1_ID = 1L;
    private static final String PRODUCT_1_DESCRIPTION = "Product 1";
    private static final int PRODUCT_1_QUANTITY = 1;
    private static final BigDecimal PRODUCT_1_PRICE = BigDecimal.ONE;
    @Mock
    private ShoppingCartService mockedShoppingCartService;
    @Mock
    private ProductService mockedProductService;
    @Captor
    private ArgumentCaptor<Item> itemArgumentCaptor;

    private ShoppingCartController shoppingCartController;

    @BeforeEach
    void beforeEachTest() {
        reset(this.mockedShoppingCartService, this.mockedProductService);
        this.shoppingCartController = new ShoppingCartController(this.mockedShoppingCartService,
                this.mockedProductService);
    }

    @Test
    @DisplayName("Deve retornar todos os itens corretamente")
    void shouldReturnAllItemsCorrectly() {
        //Given
        var itemProduct1 = new Item(PRODUCT_1_ID, PRODUCT_1_DESCRIPTION, PRODUCT_1_QUANTITY, PRODUCT_1_PRICE);
        var totalItemProduct1 = PRODUCT_1_PRICE.multiply(BigDecimal.valueOf(PRODUCT_1_QUANTITY));
        var expectedResponse = new ShoppingCart(Set.of(itemProduct1), totalItemProduct1);

        when(this.mockedShoppingCartService.getAll()).thenReturn(Set.of(itemProduct1));
        when(this.mockedShoppingCartService.getSubTotal()).thenReturn(totalItemProduct1);

        //When
        var response = this.shoppingCartController.allItems();

        //Then
        verify(this.mockedShoppingCartService, times(1)).getAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());

    }

    @Test
    @DisplayName("Deve adicionar um item corretamente")
    void shouldAddItemCorrectly() {
        //Given
        var existentProduct = new ProductEntity();
        existentProduct.setId(PRODUCT_1_ID);
        existentProduct.setDescription(PRODUCT_1_DESCRIPTION);
        existentProduct.setPrice(PRODUCT_1_PRICE);

        when(this.mockedProductService.findById(PRODUCT_1_ID)).thenReturn(Optional.of(existentProduct));
        doNothing().when(this.mockedShoppingCartService).add(any(Item.class));

        //When
        var response = this.shoppingCartController.addItem(PRODUCT_1_ID, PRODUCT_1_QUANTITY);

        //Then
        verify(this.mockedProductService, times(1)).findById(PRODUCT_1_ID);
        verify(this.mockedShoppingCartService, times(1)).add(this.itemArgumentCaptor.capture());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(PRODUCT_1_ID, this.itemArgumentCaptor.getValue().productId());
        assertEquals(PRODUCT_1_DESCRIPTION, this.itemArgumentCaptor.getValue().description());
        assertEquals(PRODUCT_1_QUANTITY, this.itemArgumentCaptor.getValue().quantity());
        assertEquals(PRODUCT_1_PRICE, this.itemArgumentCaptor.getValue().price());

    }

    @Test
    @DisplayName("Deve retornar um erro quando tentar adicionar um produto inexistente")
    void shouldReturnErrorWhenGivenNonExistentProduct() {
        //Given
        var expectedErrorMessage = "Produto não encontrado ID: 1";

        when(this.mockedProductService.findById(PRODUCT_1_ID)).thenReturn(Optional.empty());

        //When
        var response = this.shoppingCartController.addItem(PRODUCT_1_ID, PRODUCT_1_QUANTITY);

        //Then
        verify(this.mockedProductService, times(1)).findById(PRODUCT_1_ID);
        verify(this.mockedShoppingCartService, never()).add(any(Item.class));

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(expectedErrorMessage, response.getBody());

    }

    @Test
    @DisplayName("Deve incrementar um item corretamente")
    void shouldIncrementAnItemCorrectly() {
        //Given
        doNothing().when(this.mockedShoppingCartService).increase(PRODUCT_1_ID);

        //When
        var response = this.shoppingCartController.increaseItem(PRODUCT_1_ID);

        //Then
        verify(this.mockedShoppingCartService, times(1)).increase(PRODUCT_1_ID);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @DisplayName("Deve decrementar um item corretamente")
    void shouldDecreaseAnItemCorrectly() {
        //Given
        doNothing().when(this.mockedShoppingCartService).decrease(PRODUCT_1_ID);

        //When
        var response = this.shoppingCartController.decreaseItem(PRODUCT_1_ID);

        //Then
        verify(this.mockedShoppingCartService, times(1)).decrease(PRODUCT_1_ID);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @DisplayName("Deve remover um item corretamente")
    void shouldRemoveAnItemCorrectly() {
        //Given
        doNothing().when(this.mockedShoppingCartService).remove(PRODUCT_1_ID);

        //When
        var response = this.shoppingCartController.removeItem(PRODUCT_1_ID);

        //Then
        verify(this.mockedShoppingCartService, times(1)).remove(PRODUCT_1_ID);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @DisplayName("Deve tratar a exceção IllegalArgumentException corretamente")
    void shouldHandleIllegalArgumentExceptionCorrectly() {
        //Given
        var expectedErrorMessage = "Illegal argument exception";

        //When
        var response = this.shoppingCartController.handleIllegalArgumentException(
                new IllegalArgumentException(expectedErrorMessage));

        //Then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(expectedErrorMessage, response.getBody());

    }

}

package dev.giba.labs.acme.store.shoppingcart.service.impl;

import dev.giba.labs.acme.store.shoppingcart.model.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ShoppingCartServiceImplTest {

    private static final long PRODUCT_1_ID = 1L;
    private static final String PRODUCT_1_DESCRIPTION = "Product 1";
    private static final int PRODUCT_1_QUANTITY = 1;
    private static final BigDecimal PRODUCT_1_PRICE = BigDecimal.ONE;
    private static final long PRODUCT_2_ID = 2L;
    private static final String PRODUCT_2_DESCRIPTION = "Product 2";
    private static final int PRODUCT_2_QUANTITY = 2;
    private static final BigDecimal PRODUCT_2_PRICE = BigDecimal.TEN;
    private ShoppingCartServiceImpl shoppingCartServiceImpl;

    @BeforeEach
    void beforeEachTest() {
        this.shoppingCartServiceImpl = new ShoppingCartServiceImpl();
    }


    @Test
    @DisplayName("Deve retornar vazio quando não houver itens adicionados")
    void shouldReturnEmptyWhenThereAreNoItems() {
        //Given

        //When
        var items = this.shoppingCartServiceImpl.getAll();

        //Then
        assertTrue(items.isEmpty());
    }

    @Test
    @DisplayName("Deve adicionar item corretamente")
    void shouldAddItemCorrectly() {
        //Given
        var itemProduct1 = new Item(PRODUCT_1_ID, PRODUCT_1_DESCRIPTION, PRODUCT_1_QUANTITY, PRODUCT_1_PRICE);

        //When
        this.shoppingCartServiceImpl.add(itemProduct1);

        //Then
        assertEquals(Set.of(itemProduct1), this.shoppingCartServiceImpl.getAll());
    }

    @Test
    @DisplayName("Deve retornar os itens corretamente")
    void shouldReturnItemsCorrectly(){
        //Given
        var itemProduct1 = new Item(PRODUCT_1_ID, PRODUCT_1_DESCRIPTION, PRODUCT_1_QUANTITY, PRODUCT_1_PRICE);
        var itemProduct2 = new Item(PRODUCT_2_ID, PRODUCT_2_DESCRIPTION, PRODUCT_2_QUANTITY, PRODUCT_2_PRICE);

        this.shoppingCartServiceImpl.add(itemProduct1);
        this.shoppingCartServiceImpl.add(itemProduct2);

        //When
        var items = this.shoppingCartServiceImpl.getAll();

        //Then
        assertEquals(Set.of(itemProduct1, itemProduct2), items);
    }

    @Test
    @DisplayName("Deve remover um item corretamente")
    void shouldRemoveItemCorrectly() {
        //Given
        var itemProduct1 = new Item(PRODUCT_1_ID, PRODUCT_1_DESCRIPTION, PRODUCT_1_QUANTITY, PRODUCT_1_PRICE);
        var itemProduct2 = new Item(PRODUCT_2_ID, PRODUCT_2_DESCRIPTION, PRODUCT_2_QUANTITY, PRODUCT_2_PRICE);

        this.shoppingCartServiceImpl.add(itemProduct1);
        this.shoppingCartServiceImpl.add(itemProduct2);

        //When
        this.shoppingCartServiceImpl.remove(itemProduct1.productId());

        //Then
        assertEquals(Set.of(itemProduct2), this.shoppingCartServiceImpl.getAll());
    }

    @Test
    @DisplayName("Deve incrementar a quantidade do item corretamente")
    void shouldIncreaseItemQuantityCorrectly() {
        //Given
        var expectedQuantity = 2;
        var itemProduct1 = new Item(PRODUCT_1_ID, PRODUCT_1_DESCRIPTION, PRODUCT_1_QUANTITY, PRODUCT_1_PRICE);
        var itemProduct2 = new Item(PRODUCT_2_ID, PRODUCT_2_DESCRIPTION, PRODUCT_2_QUANTITY, PRODUCT_2_PRICE);

        this.shoppingCartServiceImpl.add(itemProduct1);
        this.shoppingCartServiceImpl.add(itemProduct2);

        //When
        this.shoppingCartServiceImpl.increase(PRODUCT_1_ID);

        //Then
        assertTrue(this.shoppingCartServiceImpl.getAll().stream().anyMatch(item->item.productId() == PRODUCT_1_ID
                && item.quantity() == expectedQuantity));
        assertTrue(this.shoppingCartServiceImpl.getAll().stream().anyMatch(item->item.productId() == PRODUCT_2_ID
                && item.quantity() == PRODUCT_2_QUANTITY));

    }

    @Test
    @DisplayName("Deve decrementar a quantidade do item corretamente")
    void shouldDecreaseItemQuantityCorrectly() {
        //Given
        var expectedQuantity = 1;
        var itemProduct1 = new Item(PRODUCT_1_ID, PRODUCT_1_DESCRIPTION, PRODUCT_1_QUANTITY, PRODUCT_1_PRICE);
        var itemProduct2 = new Item(PRODUCT_2_ID, PRODUCT_2_DESCRIPTION, PRODUCT_2_QUANTITY, PRODUCT_2_PRICE);

        this.shoppingCartServiceImpl.add(itemProduct1);
        this.shoppingCartServiceImpl.add(itemProduct2);

        //When
        this.shoppingCartServiceImpl.decrease(PRODUCT_2_ID);

        //Then
        assertTrue(this.shoppingCartServiceImpl.getAll().stream().anyMatch(item->item.productId() == PRODUCT_1_ID
                && item.quantity() == PRODUCT_1_QUANTITY));
        assertTrue(this.shoppingCartServiceImpl.getAll().stream().anyMatch(item->item.productId() == PRODUCT_2_ID
                && item.quantity() == expectedQuantity));

    }

    @Test
    @DisplayName("Deve remover o item quando a quantidade for menor ou igual a zero corretamente")
    void shouldRemoveItemWhenQuantityIsLowerOrEqualsThanZeroCorrectly() {
        //Given
        var itemProduct1 = new Item(PRODUCT_1_ID, PRODUCT_1_DESCRIPTION, PRODUCT_1_QUANTITY, PRODUCT_1_PRICE);
        var itemProduct2 = new Item(PRODUCT_2_ID, PRODUCT_2_DESCRIPTION, PRODUCT_2_QUANTITY, PRODUCT_2_PRICE);

        this.shoppingCartServiceImpl.add(itemProduct1);
        this.shoppingCartServiceImpl.add(itemProduct2);

        //When
        this.shoppingCartServiceImpl.decrease(PRODUCT_1_ID);

        //Then
        assertEquals(Set.of(itemProduct2), this.shoppingCartServiceImpl.getAll());

    }

    @Test
    @DisplayName("Deve calcular o subtotal corretamente")
    void shouldCalculateSubtotalCorrectly() {
        //Given
        var itemProduct1 = new Item(PRODUCT_1_ID, PRODUCT_1_DESCRIPTION, 2, new BigDecimal("1.50"));
        var itemProduct2 = new Item(PRODUCT_2_ID, PRODUCT_2_DESCRIPTION, 1, new BigDecimal("6.01"));

        this.shoppingCartServiceImpl.add(itemProduct1);
        this.shoppingCartServiceImpl.add(itemProduct2);

        //When
        var subTotal = this.shoppingCartServiceImpl.getSubTotal();

        //Then
        assertEquals(new BigDecimal("9.01"), subTotal);
    }
}

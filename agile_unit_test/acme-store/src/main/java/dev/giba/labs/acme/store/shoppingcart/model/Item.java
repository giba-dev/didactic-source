package dev.giba.labs.acme.store.shoppingcart.model;

import java.math.BigDecimal;

public record Item(long productId, String description, int quantity, BigDecimal price) {

    public Item withQuantity(int quantity) {
        return new Item(productId, description, quantity, price);
    }

}

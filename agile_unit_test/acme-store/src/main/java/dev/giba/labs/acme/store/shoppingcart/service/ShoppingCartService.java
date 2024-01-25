package dev.giba.labs.acme.store.shoppingcart.service;

import dev.giba.labs.acme.store.shoppingcart.model.Item;

import java.math.BigDecimal;

import java.util.Set;

public interface ShoppingCartService {
    Set<Item> getAll();

    void add(final Item item);

    void remove(long productId);

    void increase(long productId);

    void decrease(long productId);

    BigDecimal getSubTotal();
}

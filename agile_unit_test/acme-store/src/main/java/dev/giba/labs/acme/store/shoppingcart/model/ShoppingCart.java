package dev.giba.labs.acme.store.shoppingcart.model;

import java.math.BigDecimal;
import java.util.Set;

public record ShoppingCart(Set<Item> items, BigDecimal subTotal) {
}

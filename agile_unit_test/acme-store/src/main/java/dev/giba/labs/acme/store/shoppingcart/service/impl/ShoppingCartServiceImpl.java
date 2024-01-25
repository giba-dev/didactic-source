package dev.giba.labs.acme.store.shoppingcart.service.impl;

import dev.giba.labs.acme.store.shoppingcart.model.Item;
import dev.giba.labs.acme.store.shoppingcart.service.ShoppingCartService;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

@Service
@SessionScope
class ShoppingCartServiceImpl implements ShoppingCartService {
    private final Set<Item> items = new HashSet<>();

    @Override
    public Set<Item> getAll() {
        return Collections.unmodifiableSet(this.items);
    }

    @Override
    public void add(final Item item) {
        this.items.add(item);
    }

    @Override
    public void remove(long productId) {
        this.items.removeIf(item -> item.productId() == productId);
    }

    @Override
    public void increase(long productId) {
        Predicate<Item> itemByProductId = item -> item.productId() == productId;
        var optionalItem = this.items.stream().filter(itemByProductId).findFirst();

        if (optionalItem.isPresent()) {
            this.items.removeIf(itemByProductId);
            this.items.add(optionalItem.get().withQuantity(optionalItem.get().quantity() + 1));
        }

    }

    @Override
    public void decrease(long productId) {
        Predicate<Item> itemByProductId = item -> item.productId() == productId;
        var optionalItem = this.items.stream().filter(itemByProductId).findFirst();

        if (optionalItem.isPresent()) {
            var currentQuantity = optionalItem.get().quantity();
            var newQuantity = currentQuantity - 1;

            this.items.removeIf(itemByProductId);

            if (newQuantity > 0) {
                this.items.add(optionalItem.get().withQuantity(newQuantity));
            }
        }
    }

    @Override
    public BigDecimal getSubTotal() {
        return this.items.stream()
                .map(item -> item.price().multiply(BigDecimal.valueOf(item.quantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}

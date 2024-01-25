package dev.giba.labs.acme.store.shoppingcart.service;

import dev.giba.labs.acme.store.shoppingcart.model.ProductEntity;

import java.util.Optional;

public interface ProductService {
    Optional<ProductEntity> findById(long id);
}

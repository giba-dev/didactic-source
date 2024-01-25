package dev.giba.labs.acme.store.shoppingcart.repository;

import dev.giba.labs.acme.store.shoppingcart.model.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
}

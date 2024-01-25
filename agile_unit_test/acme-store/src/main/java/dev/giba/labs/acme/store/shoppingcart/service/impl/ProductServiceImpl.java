package dev.giba.labs.acme.store.shoppingcart.service.impl;

import dev.giba.labs.acme.store.shoppingcart.model.ProductEntity;
import dev.giba.labs.acme.store.shoppingcart.repository.ProductRepository;
import dev.giba.labs.acme.store.shoppingcart.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    public ProductServiceImpl(final ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Optional<ProductEntity> findById(long id) {
        return this.productRepository.findById(id);
    }
}

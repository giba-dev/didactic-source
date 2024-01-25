package dev.giba.labs.acme.store.shoppingcart.controller;

import dev.giba.labs.acme.store.shoppingcart.model.ShoppingCart;
import dev.giba.labs.acme.store.shoppingcart.model.Item;
import dev.giba.labs.acme.store.shoppingcart.service.ProductService;
import dev.giba.labs.acme.store.shoppingcart.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/carts")
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;
    private final ProductService productService;

    @Autowired
    public ShoppingCartController(final ShoppingCartService shoppingCartService, final ProductService productService) {
        this.shoppingCartService = shoppingCartService;
        this.productService = productService;
    }

    @GetMapping(value = "/my", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ShoppingCart> allItems() {
        return ResponseEntity.ok(new ShoppingCart(this.shoppingCartService.getAll(),
                this.shoppingCartService.getSubTotal()));
    }

    @PutMapping(value = "/my/items", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> addItem(@RequestParam(name = "productId") long productId,
                          @RequestParam(name = "quantity") int quantity){
        var optionalProduct = this.productService.findById(productId);

        if (optionalProduct.isPresent()) {
            var item = new Item(optionalProduct.get().getId(), optionalProduct.get().getDescription(), quantity,
                    optionalProduct.get().getPrice());
            this.shoppingCartService.add(item);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().body("Produto não encontrado ID: " + productId);
        }
    }

    @PatchMapping(value = "/my/items/{productId}/increase", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> increaseItem(@PathVariable("productId") long productId){
        this.shoppingCartService.increase(productId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping(value = "/my/items/{productId}/decrease", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> decreaseItem(@PathVariable("productId") long productId){
        this.shoppingCartService.decrease(productId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/my/items/{productId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> removeItem(@PathVariable("productId") long productId) {
        this.shoppingCartService.remove(productId);
        return ResponseEntity.ok().build();
    }

    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<Object> handleIllegalArgumentException(final IllegalArgumentException exception) {
        return ResponseEntity.badRequest().body(exception.getMessage());
    }
}

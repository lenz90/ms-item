package pe.com.producs.lenz.msitem.service;

import pe.com.producs.lenz.msitem.models.Item;
import pe.com.producs.lenz.msitem.models.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ItemService {
    Flux<Item> findAll();

    Mono<Item> findById(Long id, Integer quantity);
    Mono<Product> save(Product product);
    Mono<Product> update(Product product, Long id);
    Mono<Product> delete(Long id);
}

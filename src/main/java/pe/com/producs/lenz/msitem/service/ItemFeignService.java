package pe.com.producs.lenz.msitem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.com.producs.lenz.msitem.clientes.ClientProductRest;
import pe.com.producs.lenz.msitem.models.Item;
import pe.com.producs.lenz.msitem.models.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service("feignService")
public class ItemFeignService implements ItemService {

    @Autowired
    private ClientProductRest clientProductRest;

    @Override
    public Flux<Item> findAll() {
        return Flux.fromIterable(clientProductRest.findAll()).map(x -> new Item(x, 1));
    }

    @Override
    public Mono<Item> findById(Long id, Integer quantity) {
        return Mono.justOrEmpty(clientProductRest.findById(id)).map(x -> new Item(x, quantity));
    }

    @Override
    public Mono<Product> save(Product product) {
        return Mono.justOrEmpty(clientProductRest.create(product));
    }

    @Override
    public Mono<Product> update(Product product, Long id) {
        return Mono.justOrEmpty(clientProductRest.update(product, id));
    }

    @Override
    public Mono<Product> delete(Long id) {
        return Mono.justOrEmpty(clientProductRest.delete(id));
    }
}
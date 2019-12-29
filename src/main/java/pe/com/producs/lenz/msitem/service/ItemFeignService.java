package pe.com.producs.lenz.msitem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.com.producs.lenz.msitem.clientes.ClientProductRest;
import pe.com.producs.lenz.msitem.models.Item;
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
}
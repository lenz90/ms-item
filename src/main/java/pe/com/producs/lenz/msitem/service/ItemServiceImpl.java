package pe.com.producs.lenz.msitem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pe.com.producs.lenz.msitem.models.Item;
import pe.com.producs.lenz.msitem.models.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.*;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private RestTemplate clientRest;

    @Override
    public Flux<Item> findAll() {
        List<Product> products = Arrays.asList(clientRest
                .getForObject("http://localhost:8001/products", Product[].class));
        return Flux.fromIterable(products).map(x -> new Item(x, 1));
    }

    @Override
    public Mono<Item> findById(Long id, Integer quantity) {
        Map<String, String> pathVariables = new HashMap<String, String>() {{
            put("id", id.toString());
        }};
        Product product = clientRest
                .getForObject("http://localhost:8001/products/{id}"
                        , Product.class, pathVariables);
        return Mono.justOrEmpty(Optional.ofNullable(product)).map(x -> new Item(x, quantity));
    }
}

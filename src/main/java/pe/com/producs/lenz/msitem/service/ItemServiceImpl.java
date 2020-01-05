package pe.com.producs.lenz.msitem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pe.com.producs.lenz.msitem.models.Item;
import pe.com.producs.lenz.msitem.models.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.*;

@Service("itemService")
public class ItemServiceImpl implements ItemService {

    @Autowired
    private RestTemplate clientRest;

    @Override
    public Flux<Item> findAll() {
        List<Product> products = Arrays.asList(clientRest
                .getForObject("http://service-products/", Product[].class));
        return Flux.fromIterable(products).map(x -> new Item(x, 1));
    }

    @Override
    public Mono<Item> findById(Long id, Integer quantity) {
        Map<String, String> pathVariables = new HashMap<String, String>() {{
            put("id", id.toString());
        }};
        Product product = clientRest
                .getForObject("http://service-products/{id}"
                        , Product.class, pathVariables);
        return Mono.justOrEmpty(Optional.ofNullable(product)).map(x -> new Item(x, quantity));
    }

    @Override
    public Mono<Product> save(Product product) {
        HttpEntity<Product> body = new HttpEntity<Product>(product);
        return Mono.just(clientRest.exchange("http://service-products/", HttpMethod.POST, body,
                Product.class).getBody());
    }

    @Override
    public Mono<Product> update(Product product, Long id) {
        Map<String, String> pathVariables = new HashMap<String, String>() {{
            put("id", id.toString());
        }};
        HttpEntity<Product> body = new HttpEntity<Product>(product);
        return Mono.just(clientRest.exchange("http://service-products/{id}", HttpMethod.PUT, body,
                Product.class, pathVariables).getBody());
    }

    @Override
    public Mono<Product> delete(Long id) {
        Map<String, String> pathVariables = new HashMap<String, String>() {{
            put("id", id.toString());
        }};
        return Mono.just(clientRest.exchange("http://service-products/{id}", HttpMethod.DELETE, null,
                Product.class, pathVariables).getBody());
    }
}

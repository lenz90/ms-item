package pe.com.producs.lenz.msitem.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import pe.com.producs.lenz.msitem.models.Item;
import pe.com.producs.lenz.msitem.models.Product;
import pe.com.producs.lenz.msitem.service.ItemService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@RestController
public class ItemController {
    @Autowired
    @Qualifier("feignService")
    private ItemService itemService;

    @GetMapping("/items")
    public Flux<Item> findAll() {
        return itemService.findAll();
    }

    @HystrixCommand(fallbackMethod = "metodoAlternativo")
    @GetMapping("/items/{id}/quantity/{quantity}")
    public Mono<Item> findById(@PathVariable Long id, @PathVariable Integer quantity) {
        return itemService.findById(id, quantity);
    }

    public Mono<Item> metodoAlternativo(Long id, Integer quantity) {
        Item item = new Item();
        Product product = new Product();

        item.setQuantity(quantity);
        product.setId(id);
        product.setName("Camara InfraRojos");
        product.setPrice(400.0);
        product.setCreateAt(LocalDate.now());
        item.setProduct(product);

        return Mono.just(item);
    }
}

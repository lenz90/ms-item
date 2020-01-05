package pe.com.producs.lenz.msitem.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.com.producs.lenz.msitem.models.Item;
import pe.com.producs.lenz.msitem.models.Product;
import pe.com.producs.lenz.msitem.service.ItemService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@RefreshScope
@Slf4j
@RestController
public class ItemController {
    @Autowired
    @Qualifier("feignService")
    private ItemService itemService;

    @Value("${configuration.text:Hola}")
    private String text;

    @Autowired
    private Environment env;

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

    @GetMapping("/config")
    public ResponseEntity<?> getConfiguration() {
        log.info(text);

        Map<String, String> json = new HashMap<>();
        json.put("text", text);

        if (env.getActiveProfiles().length > 0 && env.getActiveProfiles()[0].equals("dev")) {
            json.put("autor.nombre", env.getProperty("configuration.author.name"));
            json.put("autor.email", env.getProperty("configuration.author.email"));
        }


        return new ResponseEntity<Map<String, String>>(json, HttpStatus.OK);
    }

    @PostMapping("/products/")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Product> create(@RequestBody Product product) {
        return itemService.save(product);
    }

    @PutMapping("/products/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Product> update(@RequestBody Product product, @PathVariable Long id) {
        return itemService.update(product, id);
    }

    @DeleteMapping("/products/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Product> delete(@PathVariable Long id) {
        return itemService.delete(id);
    }

}

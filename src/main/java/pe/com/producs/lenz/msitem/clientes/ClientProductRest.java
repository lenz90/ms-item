package pe.com.producs.lenz.msitem.clientes;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pe.com.producs.lenz.msitem.models.Product;

import java.util.List;
import java.util.Optional;

@FeignClient(name = "service-products")
public interface ClientProductRest {

    @GetMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
    List<Product> findAll();

    @GetMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    Optional<Product> findById(@PathVariable(value = "id") Long id);

    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
    Optional<Product> create(@RequestBody Product product);

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    Optional<Product> update(@RequestBody Product product, @PathVariable(value = "id") Long id);

    @DeleteMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    Optional<Product> delete(@PathVariable(value = "id") Long id);


}

package pe.com.producs.lenz.msitem.clientes;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pe.com.producs.lenz.msitem.models.Product;

import java.util.List;
import java.util.Optional;

@FeignClient(name = "service-products")
public interface ClientProductRest {

    @GetMapping(value = "/products", consumes = MediaType.APPLICATION_JSON_VALUE)
    List<Product> findAll();

    @GetMapping(value = "/products/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    Optional<Product> findById(@PathVariable(value = "id") Long id);

}

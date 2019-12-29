package pe.com.producs.lenz.msitem.models;



import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
public class Product implements Serializable {

    private static final long serialVersionUID = -9016330720209861930L;

    private Long id;
    private String name;
    private Double price;
    private LocalDate createAt;

}

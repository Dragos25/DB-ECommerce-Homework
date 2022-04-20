package com.example.market.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity(name="products")
public class Product {
    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    private Float price;
    private Integer stock;
    @OneToMany
    @JsonIgnore
    List<CartProduct> cartProductList;

}

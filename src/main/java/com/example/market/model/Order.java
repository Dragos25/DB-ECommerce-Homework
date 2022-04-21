package com.example.market.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import javax.persistence.*;
import java.util.List;

@Entity(name = "orders_history")
@Data
public class Order {
    @Id
    @GeneratedValue
    private Integer id;
    private Long timestamp;
    @OneToMany
    private List<CartProduct> productList;
    @ManyToOne
    @JsonIgnore
    private User user;
}

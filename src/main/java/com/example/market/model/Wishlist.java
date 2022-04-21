package com.example.market.model;


import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import javax.persistence.*;
import java.util.List;

@Entity(name="wishlists")
@Data
public class Wishlist {
    @Id
    @GeneratedValue
    private Integer id;
    @OneToMany
    private List<Product> productList;
    @OneToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;
}

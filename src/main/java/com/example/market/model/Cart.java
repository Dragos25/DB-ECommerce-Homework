package com.example.market.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import javax.persistence.*;
import java.util.List;

@Entity(name="carts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cart implements Comparable<Cart>{

    @Id
    @GeneratedValue
    private Integer id;

    @OneToMany(cascade = CascadeType.ALL)
    private List<CartProduct> productList;
    @OneToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    public Integer quantity(){
        Integer quantity = 0;
        for(CartProduct product : productList){
            quantity+=product.getQuantity();
        }
        return quantity;
    }

    @Override
    public int compareTo(Cart o) {
        return this.quantity()-o.quantity();
    }
}

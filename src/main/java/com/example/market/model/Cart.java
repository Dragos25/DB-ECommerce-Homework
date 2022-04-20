package com.example.market.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.Reference;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity(name="carts")
@Data
//@IdClass(Cart.CartPK.class)
public class Cart {
//    @Data
//    @AllArgsConstructor
//    @NoArgsConstructor
//    public static class CartPK implements Serializable {
//        protected Integer userId;
//        protected Integer productId;
//    }
//    @Id
//    @JoinColumn(name = "id")
//    private Integer userId;
//    @Id
//    @JoinColumn(name="id")
    @Id
    @GeneratedValue
    private Integer id;

    @OneToMany(cascade = CascadeType.ALL)
    private List<CartProduct> productList;
    @OneToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

}

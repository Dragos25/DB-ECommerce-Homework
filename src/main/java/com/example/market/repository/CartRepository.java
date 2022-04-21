package com.example.market.repository;

import com.example.market.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CartRepository extends JpaRepository<Cart, Integer> {
    Cart findByUserId(Integer userId);

}

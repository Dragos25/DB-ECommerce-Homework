package com.example.market.repository;

import com.example.market.model.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishlistRepository extends JpaRepository<Wishlist, Integer> {
    Wishlist findByUserId(Integer userId);
}

package com.example.market.controller;

import com.example.market.model.Wishlist;
import com.example.market.service.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.PermitAll;

@RestController
@RequestMapping("wishlist")
@RequiredArgsConstructor
public class WishlistController {
    private final WishlistService wishlistService;

    @GetMapping("/{userId}")
    public ResponseEntity<Wishlist> getByUserId(@PathVariable("userId") Integer userId){
        Wishlist wishlist = wishlistService.getWishlist(userId);
        if(wishlist==null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(wishlist);
    }

    @PostMapping("/add/{userId}/{productId}")
    public Wishlist addProduct(@PathVariable("userId") Integer userId, @PathVariable("productId") Integer productId){
        return wishlistService.addProduct(productId,userId);
    }

    @DeleteMapping("/delete/{userId}/{productId}")
    public Wishlist removeProduct(@PathVariable("userId") Integer userId,
                                  @PathVariable("productId") Integer productId){
        return wishlistService.removeProduct(productId, userId);
    }
}

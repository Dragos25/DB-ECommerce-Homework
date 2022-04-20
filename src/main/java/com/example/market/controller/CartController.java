package com.example.market.controller;

import com.example.market.model.Cart;
import com.example.market.model.CartProduct;
import com.example.market.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @GetMapping("/get/{id}")
    public ResponseEntity<Cart> getById(@PathVariable("id") Integer id){
        Cart cart = cartService.getCart(id);
        if(cart==null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(cart);
    }

    @PostMapping("/addProduct/{userId}/{productId}/{quantity}")
    public Cart addProductToCart(@PathVariable("productId") Integer productId,
                                 @PathVariable("userId") Integer userId,
                                 @PathVariable("quantity") Integer quantity){
        return cartService.addProduct(productId, userId, quantity);
    }

    @DeleteMapping("/removeProduct/{userId}/{productId}")
    public Cart deleteProductFromCart(@PathVariable("productId") Integer productId,
                                      @PathVariable("userId") Integer userId){
        return cartService.removeProduct(productId, userId);
    }
}

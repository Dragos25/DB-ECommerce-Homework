package com.example.market.controller;

import com.example.market.exception.QuantityTooHighException;
import com.example.market.model.Cart;
import com.example.market.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    //get cart by id
    @GetMapping("/get/{id}")
    public ResponseEntity<Cart> getById(@PathVariable("id") Integer id){
        Cart cart = cartService.getCart(id);
        if(cart==null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(cart);
    }

    //add a quantity of product(from productId) to an user's(userId) cart
    @PostMapping("/addProduct/{userId}/{productId}/{quantity}")
    public Cart addProductToCart(@PathVariable("productId") Integer productId,
                                 @PathVariable("userId") Integer userId,
                                 @PathVariable("quantity") Integer quantity) throws QuantityTooHighException {
         return cartService.addProduct(productId, userId, quantity);
    }

    //remove a product(productId) from an user's(userId) cart
    @DeleteMapping("/removeProduct/{userId}/{productId}")
    public Cart deleteProductFromCart(@PathVariable("productId") Integer productId,
                                      @PathVariable("userId") Integer userId){
        return cartService.removeProduct(productId, userId);
    }

    //Clear the products from a cart
    @DeleteMapping("/clear/{id}")
    public Cart clear(@PathVariable("id") Integer id){
        return cartService.clear(id);
    }

    //an endpoint where i can get all the carts from all the users,
    //sorted by the total of the products (a product must have a name, price and a quantity)
    @GetMapping("/getOrderedByQuantity")
    public List<Cart> getOrderedByQuantity(){
        return cartService.getOrderedByQuantity();
    }
}

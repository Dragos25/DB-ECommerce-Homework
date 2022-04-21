package com.example.market.service;

import com.example.market.model.Product;
import com.example.market.model.User;
import com.example.market.model.Wishlist;
import com.example.market.repository.ProductRepository;
import com.example.market.repository.UserRepository;
import com.example.market.repository.WishlistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WishlistService {
    private final WishlistRepository wishlistRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public Wishlist getWishlist(Integer userId){
        if(!wishlistRepository.findById(userId).isPresent()) return null;
         return wishlistRepository.findByUserId(userId);
    }

    public Wishlist addProduct(Integer productId, Integer userId){
        Optional<Product> p = productRepository.findById(productId);
        Optional<User> u =userRepository.findById(userId);
        if(!p.isPresent() || !u.isPresent())
            return null;
        Product product = p.get();
        Wishlist wishlist = getWishlist(userId);
        for(Product prod : wishlist.getProductList()){
            if(prod.getId()==productId) return wishlist;
        }
        wishlist.getProductList().add(product);
        return wishlistRepository.save(wishlist);
    }

    public Wishlist removeProduct(Integer productId, Integer userId){
        Optional<Product> prod = productRepository.findById(productId);
        Optional<User> user =userRepository.findById(userId);
        if(!prod.isPresent() || !user.isPresent())
            return null;

        Wishlist wishlist = getWishlist(userId);
        List<Product> products = wishlist.getProductList();
        products = products.stream().filter(p ->p.getId().equals(p.getId())).collect(Collectors.toList());
        System.out.println(products.size());
        wishlist.setProductList(products);
        return wishlistRepository.save(wishlist);
    }
}

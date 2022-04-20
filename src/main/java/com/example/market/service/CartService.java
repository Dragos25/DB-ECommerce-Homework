package com.example.market.service;

import com.example.market.model.Cart;
import com.example.market.model.CartProduct;
import com.example.market.model.Product;
import com.example.market.model.User;
import com.example.market.repository.CartRepository;
import com.example.market.repository.ProductRepository;
import com.example.market.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
//    public Cart getByUserId(Integer userId){
//        User u = userRepository.getById(userId);
//        if(u.getCart()==null)
//            u.setCart(new Cart());
//        cartRepository.save(u.getCart());
//        userRepository.save(u);
//        return u.getCart();
//    }

//    public Cart addProductToCart(CartProduct product, Integer userId){
//        Optional<Product> prod = productRepository.findById(product.getId());
//        Optional<User> user = userRepository.findById(userId);
//        if(!prod.isPresent() || !user.isPresent()) return null;
//        User u = user.get();
//        u.getCart().getProductList().add(product);
//        return cartRepository.save(u.getCart());
//    }

    public Cart getCart(Integer userId){
        return cartRepository.findByUserId(userId);
    }

    public Cart addProduct(Integer productId, Integer userId, Integer quantity){
        Product product = productRepository.findById(productId).get();
        Cart cart = getCart(userId);
        CartProduct cartProduct = new CartProduct();
        cartProduct.setCart(cart);
        cartProduct.setProduct(product);
        cartProduct.setQuantity(quantity);
        cart.getProductList().add(cartProduct);
        return cartRepository.save(cart);

    }

    public Cart removeProduct(Integer productId, Integer userId){
        Optional<Product> product = productRepository.findById(productId);
        Optional<User> user = userRepository.findById(userId);
        if(!product.isPresent() || !user.isPresent()){
            return null;
        }
        Cart cart = getCart(userId);
        List<CartProduct> products = cart.getProductList();
        products = products.stream().filter(p ->p.getProduct()==product.get()).collect(Collectors.toList());
        cart.setProductList(products);
        return cartRepository.save(cart);
    }
}

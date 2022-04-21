package com.example.market.service;

import com.example.market.exception.QuantityTooHighException;
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

    public Cart getCart(Integer userId) {
        if (!userRepository.findById(userId).isPresent()) return null;
        return cartRepository.findByUserId(userId);
    }

    public Cart addProduct(Integer productId, Integer userId, Integer quantity) throws QuantityTooHighException {
        Optional<Product> prodOptional = productRepository.findById(productId);
        if (!prodOptional.isPresent())
            return null;
        Product product = prodOptional.get();
        Cart cart = getCart(userId);
        if (cart == null)
            return null;
        if (product.getStock() - quantity < 0) throw new QuantityTooHighException("Not enough stock");
        product.setStock(product.getStock() - quantity);
        productRepository.save(product);
        List<CartProduct> productList = cart.getProductList();
        for (int i = 0; i < productList.size(); ++i) {
            if (productList.get(i).getProduct().getId() == productId) {
                productList.get(i).setQuantity(productList.get(i).getQuantity() + quantity);
                return cartRepository.save(cart);
            }
        }
        CartProduct cartProduct = new CartProduct();
        cartProduct.setCart(cart);
        cartProduct.setProduct(product);
        cartProduct.setQuantity(quantity);
        cart.getProductList().add(cartProduct);
        return cartRepository.save(cart);

    }

    public Cart removeProduct(Integer productId, Integer userId) {
        Optional<Product> product = productRepository.findById(productId);
        Optional<User> user = userRepository.findById(userId);
        if (!product.isPresent() || !user.isPresent()) {
            return null;
        }
        Cart cart = getCart(userId);
        List<CartProduct> products = cart.getProductList();
        for (CartProduct p : products) {
            if (p.getProduct().getId() == productId) {
                p.getProduct().setStock(p.getProduct().getStock() + p.getQuantity());
                break;
            }
        }
        products = products.stream().filter(p -> p.getProduct().getId() != (product.get().getId())).collect(Collectors.toList());
        cart.setProductList(products);
        return cartRepository.save(cart);
    }

    public List<Cart> getOrderedByQuantity(){
        return cartRepository.findAll();
    }

}

package com.example.market.unit.service;

import com.example.market.model.*;
import com.example.market.repository.CartRepository;
import com.example.market.repository.OrderRepository;
import com.example.market.repository.UserRepository;
import com.example.market.service.OrderService;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Optional;

@SpringBootTest
public class OrderServiceTest {
    private OrderService orderService;
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private CartRepository cartRepository;
    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void init(){
        MockitoAnnotations.openMocks(this);
        orderService = new OrderService(orderRepository, cartRepository, userRepository);
    }
    @Test
    void givenUserId_whenPlaceOrder_addOrderToUserHistory(){
        User user = new User();
        user.setId(1);
        Cart cart = new Cart();
        Product product = new Product();
        CartProduct cartProduct = new CartProduct();
        product.setId(1);
        product.setName("testProduct");
        cartProduct.setProduct(product);
        cartProduct.setId(1);
        ArrayList<CartProduct> cartProducts = new ArrayList<>();
        cartProducts.add(cartProduct);
        cart.setProductList(cartProducts);
        user.setCart(cart);
        cart.setUser(user);
        user.setOrderHistory(new ArrayList<>());
        Mockito.when(cartRepository.findById(1)).thenReturn(Optional.ofNullable(cart));
        Order order = orderService.placeOrder(1);
        assert "testProduct".equals(order.getProductList().get(0).getProduct().getName());
    }
}

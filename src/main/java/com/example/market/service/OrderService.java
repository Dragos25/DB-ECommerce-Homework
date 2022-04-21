package com.example.market.service;

import com.example.market.model.Cart;
import com.example.market.model.Order;
import com.example.market.model.User;
import com.example.market.repository.CartRepository;
import com.example.market.repository.OrderRepository;
import com.example.market.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final UserRepository userRepository;

    public List<Order> findAllByUser(Integer userId){
        return orderRepository.findAllByUserId(userId);
    }

    //method to implement order history
    public Order placeOrder(Integer userId){
        Optional<Cart> cartOpt = cartRepository.findById(userId);
        if(!cartOpt.isPresent()){
            return null;
        }
        Cart cart = cartOpt.get();
        if(cart.getProductList().size()==0)
            return null;
        //when an user places an order
        //all contents from his cart move to a list from the order
        //the order also has a timestamp when it was placed
        Order order = new Order();
        User user = cart.getUser();
        order.setProductList(cart.getProductList());
        order.setTimestamp(System.currentTimeMillis());
        order.setUser(user);
        //empty the cart
        cart.setProductList(new ArrayList<>());
        user.getOrderHistory().add(order);

        orderRepository.save(order);
        cartRepository.save(cart);
        userRepository.save(user);
        return order;
    }


}

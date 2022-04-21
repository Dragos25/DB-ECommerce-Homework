package com.example.market.unit;


import com.example.market.model.Cart;
import com.example.market.model.Product;
import com.example.market.model.User;
import com.example.market.repository.ProductRepository;
import com.example.market.repository.UserRepository;
import com.example.market.service.ProductService;
import com.example.market.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
public class UserServiceTests {

    @Mock
    private UserRepository userRepository;
    private UserService userService;
    @BeforeEach
    void init(){
        MockitoAnnotations.openMocks(this);
        userService = new UserService(userRepository);
    }

    @Test
    void givenUserId_whenGetById_thenSucceed(){
        User user = new User(1,"Gigel", "Marcel","marcel@mail.com",null, null, null);
        Mockito.when(userRepository.findById(1)).thenReturn(java.util.Optional.of(user));
        User user2 = userService.findById(1);
        assert user.equals(user2);

    }

    @Test
    void givenUserId_whenGetById_thenReturnNull(){
        Mockito.when(userRepository.findById(1)).thenReturn(Optional.ofNullable(null));
        User u = userService.findById(1);
        assert u==null;
    }

    @Test
    void givenUser_whenAdd_thenReturnUser(){
        User user = new User(1,"Gigel", "Marcel","marcel@mail.com",null, null, null);
        Mockito.when(userRepository.save(user)).thenReturn(user);
        User user2 = userService.add(user);
        assert user.equals(user2);

    }

    @Test
    void givenUserId_whenUserExistsAndDelete_thenReturnTrue(){
        User user = new User(1,"Gigel", "Marcel","marcel@mail.com",null, null, null);
        Mockito.when(userRepository.findById(1)).thenReturn(Optional.of(user));
        assert userService.delete(user.getId());
    }

    @Test
    void givenUserId_whenUserNotExistsAndDelete_thenReturnFalse(){
        Mockito.when(userRepository.findById(1)).thenReturn(Optional.ofNullable(null));
        assert !userService.delete(1);
    }


}

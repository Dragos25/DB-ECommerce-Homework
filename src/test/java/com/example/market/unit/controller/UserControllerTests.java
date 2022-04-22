package com.example.market.unit.controller;

import com.example.market.controller.UserController;
import com.example.market.model.User;
import com.example.market.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@SpringBootTest
public class UserControllerTests {
    private UserController userController;
    @Mock
    UserService userService;
    List<User> users;

    @BeforeEach
    void init(){
        MockitoAnnotations.openMocks(this);
        userController = new UserController(userService);
        users = new ArrayList<>();
        users.add(new User(1,"Gigel", "Marcel","marcel@mail.com",null, null, null));
        users.add(new User(2,"Andrei", "Pop","apop@mail.com",null, null, null));
        users.add(new User(3,"Marian", "Tudor","tudorm@mail.com",null, null, null));
    }

    @Test
    void given_whenGetAllRequest_thenReturnAllUsers(){
        Mockito.when(userService.findAll()).thenReturn(users);
        assert users.equals(userController.getAll());
    }

    @Test
    void givenUserId_whenGetUserById_thenSucceed(){
        Mockito.when(userService.findById(1)).thenReturn(users.get(1));
        assert userController.getById(1).getStatusCode().equals(HttpStatus.OK);
    }

    @Test
    void givenUserId_whenGetUserById_thenFail(){
        Mockito.when(userService.findById(99)).thenReturn(null);
        assert userController.getById(99).getStatusCode().equals(HttpStatus.NOT_FOUND);
    }

    @Test
    void givenUser_whenAdd_thenReturnUser(){
        Mockito.when(userService.add(users.get(0))).thenReturn(users.get(0));
        assert userController.add(users.get(0)).equals(users.get(0));
    }

    @Test
    void givenUserId_whenDelete_thenSucceed(){
        Mockito.when(userService.delete(1)).thenReturn(true);
        assert userController.delete(1).getStatusCode().equals(HttpStatus.OK);
    }

    @Test
    void givenUserId_whenDelete_thenNotFound(){
        Mockito.when(userService.delete(99)).thenReturn(false);
        assert userController.delete(99).getStatusCode().equals(HttpStatus.NOT_FOUND);
    }
}

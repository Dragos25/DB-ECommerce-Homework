package com.example.market.controller;

import com.example.market.model.User;
import com.example.market.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    //get all users
    @GetMapping("/getAll")
    public List<User> getAll(){
        return userService.findAll();
    }

    //get users by id
    @GetMapping("{id}")
    public ResponseEntity<User> getById(@PathVariable("id") Integer id){
        User user = userService.findById(id);
        if(user==null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    //add user
    @PostMapping("/new")
    public User add(@RequestBody User user){
        return userService.add(user);
    }

    //delete user by id
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Integer id){
        boolean deleted = userService.delete(id);
        if(deleted) return ResponseEntity.status(HttpStatus.OK).body("User deleted");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
    }

    //update user with a partial user
    @PutMapping("/update")
    public ResponseEntity<User> update(@RequestBody User user) throws IllegalAccessException, InstantiationException {
        User updated = userService.update(user);
        if(updated==null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        return ResponseEntity.status(HttpStatus.OK).body(updated);
    }

    //an endpoint where i can get all the users sorted by their number of orders
    //(users will also have order history, make sure to implement it)
    @GetMapping("/getSortedByOrders")
    public List<User> getSortedByOrders(){
        return userService.sortByOrders();
    }
}

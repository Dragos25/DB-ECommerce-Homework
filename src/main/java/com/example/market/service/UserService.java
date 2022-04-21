package com.example.market.service;

import com.example.market.model.Cart;
import com.example.market.model.User;
import com.example.market.model.Wishlist;
import com.example.market.repository.UserRepository;
import com.example.market.util.ClassMerger;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public List<User> findAll(){
        return userRepository.findAll();
    }

    public User findById(Integer id){
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()){
            return user.get();
        }
        return null;
    }

    public User add(User user){
        user.setCart(new Cart());
        user.setWishlist(new Wishlist());
        return userRepository.save(user);
    }

    public boolean delete(Integer userId){
        Optional<User> user = userRepository.findById(userId);
        if(!user.isPresent())
            return false;
        userRepository.deleteById(userId);
        return true;
    }

    public User update(User partialUser) throws InstantiationException, IllegalAccessException {
        Optional<User> userOpt= userRepository.findById(partialUser.getId());
        if(userOpt.isPresent()){
            User merged = ClassMerger.mergeObjects(partialUser, userOpt.get());
            return userRepository.save(merged);
        }
        return null;
    }


}

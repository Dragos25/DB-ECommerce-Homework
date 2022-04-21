package com.example.market.unit;

import com.example.market.model.Wishlist;
import com.example.market.repository.ProductRepository;
import com.example.market.repository.UserRepository;
import com.example.market.repository.WishlistRepository;
import com.example.market.service.WishlistService;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
public class WishlistServiceTests {

    private WishlistService wishlistService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private WishlistRepository wishlistRepository;
    @Mock
    private ProductRepository productRepository;
    @BeforeEach
    void init(){
        MockitoAnnotations.openMocks(this);
        wishlistService = new WishlistService(wishlistRepository, productRepository, userRepository);
    }
    @Test
    void givenUserId_whenGetWishlist_thenSucceed(){
        Wishlist wishlist = new Wishlist();
        wishlist.setId(1);
        Mockito.when(wishlistRepository.findByUserId(1)).thenReturn(wishlist);
        Mockito.when(wishlistRepository.findById(1)).thenReturn(Optional.ofNullable(wishlist));
        assert wishlist.getId().equals(wishlistService.getWishlist(1).getId());
    }

}

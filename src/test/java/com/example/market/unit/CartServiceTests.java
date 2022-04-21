package com.example.market.unit;

import com.example.market.exception.QuantityTooHighException;
import com.example.market.model.Cart;
import com.example.market.model.CartProduct;
import com.example.market.model.Product;
import com.example.market.model.User;
import com.example.market.repository.CartRepository;
import com.example.market.repository.ProductRepository;
import com.example.market.repository.UserRepository;
import com.example.market.service.CartService;
import com.example.market.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class CartServiceTests {
    @Mock
    private UserRepository userRepository;
    @Mock
    private CartRepository cartRepository;
    @Mock
    private ProductRepository productRepository;
    private CartService cartService;
    private User user;
    private Cart cart;
    private Product product;
    private CartProduct cartProduct;
    @BeforeEach
    void init(){
        MockitoAnnotations.openMocks(this);
        cartService = new CartService(cartRepository, productRepository, userRepository);
        user = new User(1,"Gigel", "Marcel","marcel@mail.com",null, null, null);
        cart = new Cart(1,new ArrayList<>(), user);
        product = new Product(1,"paine",30f,10, null);
        cartProduct = new CartProduct(1,2,product,cart, null);
        cart.getProductList().add(cartProduct);
        user.setCart(cart);

    }

    @Test
    void givenUserId_whenGetById_thenSucceed(){
        Mockito.when(userRepository.findById(1)).thenReturn(java.util.Optional.ofNullable(user));
        Mockito.when(cartRepository.findByUserId(1)).thenReturn(user.getCart());
        assert cartService.getCart(1).getId().equals(cart.getId());
    }

    @Test
    void givenUserId_whenGetById_thenReturnNull(){
        Mockito.when(userRepository.findById(1)).thenReturn(Optional.ofNullable(null));
        Cart c = cartService.getCart(1);
        assert c == null;
    }

    @Test
    void givenProduct_whenAddProduct_thenReturnCartWithNewProduct(){
        Product product2 = new Product(2,"testProduct", 10f, 100, null);
        Cart testCart = new Cart();
        testCart.setProductList(new ArrayList<>());
        CartProduct toAdd = new CartProduct(2, 10, product2, testCart, null);
        testCart.getProductList().add(cartProduct);
        testCart.getProductList().add(toAdd);
        Mockito.when(cartRepository.save(cart)).thenReturn(testCart);
        assert testCart.getProductList().get(1).equals(toAdd);
    }

    @Test
    void givenProductThatExistsInCart_whenAddProduct_thenUpdateCartQuantity() throws QuantityTooHighException {
        Cart testCart = new Cart();
        testCart.setProductList(new ArrayList<>());
        testCart.getProductList().add(cartProduct);
        testCart.getProductList().get(0).setQuantity(4);
        Mockito.when(cartRepository.save(cart)).thenReturn(testCart);
        Mockito.when(productRepository.findById(1)).thenReturn(Optional.ofNullable(product));
        Mockito.when(userRepository.findById(1)).thenReturn(Optional.ofNullable(user));
        Mockito.when(cartRepository.findByUserId(1)).thenReturn(cart);
        Cart fromService = cartService.addProduct(1,1,2);
        assert fromService.getProductList().get(0).getQuantity() ==6;
    }

    @Test
    void givenProductThatExistsInCart_whenRemoveProduct_thenUpdateCart(){
        Cart newCart = new Cart();
        newCart.setProductList(new ArrayList<>());
        Mockito.when(userRepository.findById(1)).thenReturn(Optional.ofNullable(user));
        Mockito.when(productRepository.findById(1)).thenReturn(Optional.ofNullable(product));
        Mockito.when(cartRepository.findByUserId(1)).thenReturn(cart);
        Mockito.when(cartRepository.save(cart)).thenReturn(null);

        assert cartService.removeProduct(1,1) == null;

    }
    @Test
    void givenCart_whenClear_thenReturnEmptyCart(){
        Mockito.when(cartRepository.save(cart)).thenReturn(cart);
        Mockito.when(userRepository.findById(1)).thenReturn(Optional.ofNullable(user));
        Mockito.when(cartRepository.findByUserId(1)).thenReturn(cart);
        assert cartService.clear(1).getProductList().size() == 0;
    }

    @Test
    void givenListOfCarts_whenSortByQuantity_thenReturnSortedList(){
        List<CartProduct> list1= new ArrayList<>();
        List<CartProduct> list2= new ArrayList<>();
        CartProduct cartProduct1 = new CartProduct();
        cartProduct1.setQuantity(20);
        CartProduct cartProduct2 = new CartProduct();
        cartProduct2.setQuantity(10);
        list1.add(cartProduct1);
        list2.add(cartProduct2);
        Cart cart1 = new Cart();
        Cart cart2 = new Cart();
        cart1.setProductList(list1);
        cart2.setProductList(list2);
        List<Cart> carts = new ArrayList<>();
        carts.add(cart1);
        carts.add(cart2);
        Mockito.when(cartRepository.findAll()).thenReturn(carts);
        ArrayList<Cart> orderedCarts = (ArrayList<Cart>) cartService.getOrderedByQuantity();
        assert orderedCarts.get(0).getProductList().get(0).getQuantity() == 10;
    }
}

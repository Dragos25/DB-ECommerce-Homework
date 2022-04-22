package com.example.market.unit.service;

import com.example.market.model.Product;
import com.example.market.repository.ProductRepository;
import com.example.market.service.ProductService;
import com.example.market.service.UserService;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;


@SpringBootTest

public class ProductServiceTests {

    @Mock
    ProductRepository productRepository;
    ProductService productService;
    @BeforeEach
    void init(){
        MockitoAnnotations.openMocks(this);
        productService = new ProductService(productRepository);
    }

    @Test
    void givenProductId_whenGetById_thenSucceed(){
        Product product = new Product(1,"paine",30f,10, null);
        Mockito.when(productRepository.findById(1)).thenReturn(java.util.Optional.of(product));
        Product product2 = productService.getById(1);
        assert product.equals(product2);

    }

    @Test
    void givenProductId_whenGetById_thenReturnNull(){
        Mockito.when(productRepository.findById(1)).thenReturn(Optional.ofNullable(null));
        Product p = productService.getById(1);
        assert p==null;
    }

    @Test
    void givenProduct_whenAdd_thenReturnProduct(){
        Product product = new Product(1,"paine",30f,10, null);
        Mockito.when(productRepository.save(product)).thenReturn(product);
        assert(product.equals(productService.add(product)));
    }

    @Test
    void given_WhenGetAll_thenReturnListOfProducts(){
        ArrayList<Product> products = new ArrayList<>();
        products.add(new Product(1,"paine",30f,10, null));
        products.add(new Product(2,"apa", 15f, 20,null));
        Mockito.when(productRepository.findAll()).thenReturn(products);
        ArrayList<Product> products2 = (ArrayList<Product>) productService.getAll();
        assert products.equals(products2);
    }
}

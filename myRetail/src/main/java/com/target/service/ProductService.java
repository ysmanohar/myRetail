package com.target.service;

import java.util.List;

import com.target.model.Product;

public interface ProductService {

    List<Product> getAll();

    Product findById(int id);

    Product findByName(String name);

    void create(Product user);

    void update(Product user);

    void delete(int id);

    boolean exists(Product user);
}

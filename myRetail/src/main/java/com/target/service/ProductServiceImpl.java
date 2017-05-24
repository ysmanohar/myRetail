package com.target.service;

import org.springframework.stereotype.Service;

import com.target.model.Product;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class ProductServiceImpl implements ProductService {

    
    static List<Product> products = new ArrayList<Product>(
            Arrays.asList(
                    new Product(1, "Daenerys Targaryen",15.49,"USD"),
                    new Product(2, "John Snow",17.49,"USD"),
                    new Product(3, "Arya Stark",198.49,"USD"),
                    new Product(4, "Cersei Baratheon",113.49,"USD")));

    @Override
    public List<Product> getAll() {
        return products;
    }

    @Override
    public Product findById(int id) {
        for (Product product : products){
            if (product.getId() == id){
                return product;
            }
        }
        return null;
    }

    @Override
    public Product findByName(String productname) {
        for (Product product : products){
            if (product.getProductname().equals(productname)){
                return product;
            }
        }
        return null;
    }

    @Override
    public void create(Product product) {
    	products.add(product);
    }

    @Override
    public void update(Product product) {
        int index = products.indexOf(product);
        products.set(index, product);
    }

    @Override
    public void delete(int id) {
        Product product = findById(id);
        products.remove(product);
    }

    @Override
    public boolean exists(Product product) {
        return findByName(product.getProductname()) != null;
    }
}

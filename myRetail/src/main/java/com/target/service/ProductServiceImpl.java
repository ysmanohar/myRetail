package com.target.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.target.controller.UserController;
import com.target.model.Price;
import com.target.model.Product;
import com.target.repository.ProductRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Service
public class ProductServiceImpl implements ProductService {
	
	private final Logger LOG = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private ProductRepository repository;
	
	 
 
    
    static List<Product> products = new ArrayList<Product>(
            Arrays.asList(
                    new Product(1, "Daenerys Targaryen",new Price(15.49,"USD")),
                    new Product(2, "John Snow",new Price(17.49,"USD")),
                    new Product(3, "Arya Stark",new Price(198.49,"USD")),
                    new Product(4, "Cersei Baratheon",new Price(113.49,"USD"))));

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
        return findById(product.getId()) != null;
    }

	@Override
	public List<Product> findByNameandId(String name, int id) {
		List<Product> outputproduct= new ArrayList<Product>();
		List<Product> firebaseproducts=repository.getdata();
		for (Product product : firebaseproducts){
			LOG.info(product.getProductname()+"given name"+name.substring(1,name.length()-1)+"givenid"+ product.getId() +" "+id);
            if (product.getProductname().equals(name.substring(1,name.length()-1))&&product.getId()==id){
            	outputproduct.add(product);
            }
        } 
		return outputproduct;
	}

	@Override
	public String getproductname() {
		String productname=repository.getproductname();
		return productname;
	}
}

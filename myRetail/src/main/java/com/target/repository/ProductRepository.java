package com.target.repository;

import java.util.List;

import com.target.model.Product;

public interface ProductRepository {

	List<Product> getdata();
	String getproductname();
}

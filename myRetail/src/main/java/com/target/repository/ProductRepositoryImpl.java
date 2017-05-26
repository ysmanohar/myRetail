package com.target.repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.json.JSONException;

import org.json.JSONTokener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.target.controller.UserController;
import com.target.model.Product;


import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


@Repository
public class ProductRepositoryImpl implements ProductRepository {
	
	private final Logger LOG = LoggerFactory.getLogger(UserController.class);

	@Override
	public List<Product> getdata() {
		RestTemplate restTemplate = new RestTemplate();
	    ResponseEntity<Product[]> responseEntity  = restTemplate.getForEntity("https://myretail-808b8.firebaseio.com/.json", Product[].class);
	    Product[] firebaseproduct=responseEntity.getBody();
	    LOG.info(firebaseproduct.toString());
	    List<Product> products = new ArrayList<Product>(Arrays.asList(firebaseproduct));
	    LOG.info(products.get(0).getProductname());
		return products;
	}

	@Override
	public String getproductname() {
		RestTemplate restTemplate = new RestTemplate();
	    Object responseEntity  = restTemplate.getForObject("http://redsky.target.com/v1/pdp/tcin/13860428?excludes=taxonomy,price,promotion,bulk_ship,rating_and_review_reviews,rating_and_review_statistics,question_answer_statistics", Object.class);
	    ObjectMapper mapper = new ObjectMapper();
	    String jsonInString;
		try {
			jsonInString = mapper.writeValueAsString(responseEntity);
			//LOG.info(jsonInString.indexOf("bullet_description")+"hi");
			String productname=jsonInString.substring(jsonInString.indexOf("title")+8,jsonInString.indexOf("bullet_description")-3);
			return productname;
			
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    
	    
		return null;
	}

	
}

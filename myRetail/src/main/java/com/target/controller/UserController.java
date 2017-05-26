package com.target.controller;

import com.target.model.Product;
import com.target.service.ProductService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/products")
public class UserController {

    private final Logger LOG = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private ProductService productService;

    // =========================================== Get All Products ==========================================


    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Product>> getAll(@RequestParam(value="id",defaultValue = "0") int id,
    		@RequestParam(value="productname",defaultValue = "null") String productname) {
        LOG.info("getting pricing information with product-id: {}, and product-name: {}", id, productname);
        if(id!=0&&productname!=null){
        	List<Product> product=productService.findByNameandId(productname, id);
        	return new ResponseEntity<List<Product>>(product, HttpStatus.OK);
        }
        List<Product> products = productService.getAll();

        if (products == null || products.isEmpty()){
            LOG.info("no users found");
            return new ResponseEntity<List<Product>>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<List<Product>>(products, HttpStatus.OK);
    }

    // =========================================== Get Product By ID =========================================

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public ResponseEntity<Product> get(@PathVariable("id") int id){
        LOG.info("getting user with id: {}", id);
        Product product = productService.findById(id);

        if (product == null){
            LOG.info("user with id {} not found", id);
            return new ResponseEntity<Product>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Product>(product, HttpStatus.OK);
    }
    
 // =========================================== Get Product Name =========================================

    @RequestMapping(value = "/getproductname", method = RequestMethod.GET)
    public String get(){
        
        String productname = productService.getproductname();

        return productname;
    }
    
    // =========================================== Create New Product ========================================

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> create(@RequestBody Product product, UriComponentsBuilder ucBuilder){
        LOG.info("creating new product: {}", product);

        if (productService.exists(product)){
            LOG.info("a user with id " + product.getId() + " already exists");
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }

        productService.create(product);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/user/{id}").buildAndExpand(product.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }

    // =========================================== Update Existing Product ===================================

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public ResponseEntity<Product> update(@PathVariable int id, @RequestBody Product product){
        LOG.info("updating user: {}", product);
        Product currentproduct = productService.findById(id);
        //LOG.info(currentproduct.getProductname());

        if (currentproduct == null){
            LOG.info("User with id {} not found", id);
            return new ResponseEntity<Product>(HttpStatus.NOT_FOUND);
        }

        currentproduct.setProductname(product.getProductname());
        currentproduct.setPrice(product.getPrice());

        productService.update(currentproduct);
        return new ResponseEntity<Product>(currentproduct, HttpStatus.OK);
    }

    // =========================================== Delete Product ============================================

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> delete(@PathVariable("id") int id){
        LOG.info("deleting user with id: {}", id);
        Product product = productService.findById(id);

        if (product == null){
            LOG.info("Unable to delete. User with id {} not found", id);
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }

        productService.delete(id);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
}

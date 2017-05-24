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
    private ProductService userService;

    // =========================================== Get All Users ==========================================


    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Product>> getAll(@RequestParam(value = "offset", defaultValue = "0") int offset,
                                             @RequestParam(value = "count", defaultValue = "10") int count) {
        LOG.info("getting all users with offset: {}, and count: {}", offset, count);
        List<Product> products = userService.getAll();

        if (products == null || products.isEmpty()){
            LOG.info("no users found");
            return new ResponseEntity<List<Product>>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<List<Product>>(products, HttpStatus.OK);
    }

    // =========================================== Get User By ID =========================================

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public ResponseEntity<Product> get(@PathVariable("id") int id){
        LOG.info("getting user with id: {}", id);
        Product product = userService.findById(id);

        if (product == null){
            LOG.info("user with id {} not found", id);
            return new ResponseEntity<Product>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Product>(product, HttpStatus.OK);
    }

    // =========================================== Create New User ========================================

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> create(@RequestBody Product product, UriComponentsBuilder ucBuilder){
        LOG.info("creating new product: {}", product);

        if (userService.exists(product)){
            LOG.info("a user with name " + product.getProductname() + " already exists");
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }

        userService.create(product);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/user/{id}").buildAndExpand(product.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }

    // =========================================== Update Existing User ===================================

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public ResponseEntity<Product> update(@PathVariable int id, @RequestBody Product product){
        LOG.info("updating user: {}", product);
        Product currentproduct = userService.findById(id);

        if (currentproduct == null){
            LOG.info("User with id {} not found", id);
            return new ResponseEntity<Product>(HttpStatus.NOT_FOUND);
        }

        currentproduct.setId(product.getId());
        currentproduct.setProductname(product.getProductname());

        userService.update(product);
        return new ResponseEntity<Product>(currentproduct, HttpStatus.OK);
    }

    // =========================================== Delete User ============================================

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> delete(@PathVariable("id") int id){
        LOG.info("deleting user with id: {}", id);
        Product product = userService.findById(id);

        if (product == null){
            LOG.info("Unable to delete. User with id {} not found", id);
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }

        userService.delete(id);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
}

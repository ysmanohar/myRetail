package com.target.model;

import java.io.Serializable;

public class Product implements Serializable {

    private int id;
    private String productname;
    private Price price;

    public Product() {
    }

    public Product(String productname) {
        this.productname = productname;
    }

    public Product(int id, String productname,Price price) {
        this.id = id;
        this.productname = productname;
        this.price=price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }
    
    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }
    

    }
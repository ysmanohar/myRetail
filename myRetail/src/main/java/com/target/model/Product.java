package com.target.model;

import java.io.Serializable;

public class Product implements Serializable {

    private int id;
    private String productname;
    private Double current_price;
    private String currency_code;

    public Product() {
    }

    public Product(String productname) {
        this.productname = productname;
    }

    public Product(int id, String productname,double current_price,String currency_code) {
        this.id = id;
        this.productname = productname;
        this.current_price=current_price;
        this.currency_code=currency_code;
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
    
    public String getcurrency_code() {
        return currency_code;
    }

    public void setcurrency_code(String currency_code) {
        this.currency_code = currency_code;
    }
    
    public Double getcurrent_price() {
        return current_price;
    }

    public void setcurrent_price(double current_price) {
        this.current_price = current_price;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + productname + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product user = (Product) o;

        if (id != user.id) return false;
        if (productname != null ? !productname.equals(user.productname) : user.productname != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (productname != null ? productname.hashCode() : 0);
        return result;
    }
}
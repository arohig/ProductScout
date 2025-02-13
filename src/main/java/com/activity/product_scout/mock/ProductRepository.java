package com.activity.product_scout.mock;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;
import com.activity.product_scout.models.Product;

@Repository
public class ProductRepository {
    private List<Product> products = new ArrayList<>();

    public boolean add(Product product) { // returns true if the product was added successfully
        // check if product already exists
        for (Product p : this.products){
            if (p.getTitle().equals(product.getTitle())) {
                return false;
            }
        }
        this.products.add(product);
        return true;
    }

    public Product getProduct(int name){
        return this.products.get(name);
    }
    
    public List<Product> getAllProducts(){
        return this.products;
    }

    public int getLengthRecords(){
        return this.products.size();
    }
}
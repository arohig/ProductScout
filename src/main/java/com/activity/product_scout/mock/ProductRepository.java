package com.activity.product_scout.mock;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;
import com.activity.product_scout.models.Product;

@Repository
public class ProductRepository {
    private List<Product> products = new ArrayList<>();

    public void add(Product product){
        // check if product already exists
        for (Product p : this.products){
            if (p.getName() == product.getTitle()){
                return;
            }
        }
        this.products.add(product);
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
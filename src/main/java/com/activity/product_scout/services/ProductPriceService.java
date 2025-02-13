package com.activity.product_scout.services;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.web.client.RestClient;

// import com.activity.product_scout.mock.ProductRepository;
import com.activity.product_scout.models.Product;

public class ProductPriceService {
    // private final RestClient restClient;

    // // Inject ProductRepository
    // @Autowired
    // public ProductPriceService(ProductRepository productRepository) {
    //     this.restClient = RestClient.create();  // RestClient instance
    //     this.productRepository = productRepository;
    // }

    // // Get product price from the API
    // public Map<String, BigDecimal> getPrices(String product) {
    //     String apiUrl = "https://api.example.com/products?name=" + product;
        
    //     // Make the API call and get prices
    //     Map<String, BigDecimal> priceMapping = restClient.get()
    //     .uri(apiUrl)
    //     .retrieve()
    //     .body(Map.class);

    //     return priceMapping;
    // }

    // // Store the prices in PostgreSQL
    // public void storePrices(Map<String, BigDecimal> priceMapping) {
    //     priceMapping.forEach((productName, price) -> {
    //         Product product = new Product();
    //         product.setName(productName);
    //         product.setPrice(price);
    //         productRepository.save(product);
    //     });
    // }

}
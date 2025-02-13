package com.activity.product_scout.services;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.activity.product_scout.components.MessageConsumer;
import com.activity.product_scout.models.Product;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class MessageProcessorService {
    private static final System.Logger logger = System.getLogger(MessageConsumer.class.getName());
    private static final HttpClient httpClient = HttpClient.newHttpClient();
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${rest.url}")
    private String restUrl;

    /* Create product object from message */
    public void processMessage(String message) {
        try {
            String[] productDetails = ProductPriceFinder.getProductDetails(message); // Get product details

            Product product = new Product();
            product.setName(message);
            product.setTitle(productDetails[0]);
            product.setPrice(Double.parseDouble(productDetails[1].replace("$", "")));
            product.setUrl(productDetails[2]);

            putProduct(product);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    /* Send product to REST API */
    public void putProduct(Product product) {
        try {
            String jsonProduct = objectMapper.writeValueAsString(product); // convert the Product object to JSON

            HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(restUrl))
            .header("Content-Type", "application/json")
            .PUT(HttpRequest.BodyPublishers.ofString(jsonProduct, StandardCharsets.UTF_8))
            .build();

            httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return;
        }
    }
}

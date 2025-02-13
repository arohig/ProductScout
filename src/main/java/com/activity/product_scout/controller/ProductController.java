package com.activity.product_scout.controller;

import com.activity.product_scout.components.MessageConsumer;
import com.activity.product_scout.mock.ProductRepository;
import com.activity.product_scout.models.Product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")  // allow React app to communicate with backend
@RestController
@RequestMapping(value = {"/products"})
public class ProductController {
    private static final System.Logger logger = System.getLogger(MessageConsumer.class.getName());

    @Autowired
    private ProductRepository productRepository;

    // PUT endpoint to receive and save products
    @PutMapping(value = "/add", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {
        logger.log(System.Logger.Level.INFO, "Product {0} has been added to the website", product.getName());
        productRepository.add(product);
        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }

    // GET endpoint to retrieve all products
    @GetMapping(value = "/list")
    public ResponseEntity<List<Product>> getProducts() {
        List<Product> products = productRepository.getAllProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    // @GetMapping(value = "/getPrice")
    // public ResponseEntity<Product> getProduct(@RequestBody ) {
    //     Product product = this.productRepo.getProduct(id);
    //     return new ResponseEntity<>(product, HttpStatus.FOUND);
    // }
    // @GetMapping(value = "/getPrice")
    // public ResponseEntity<Product> getProduct(@RequestBody String product) {
    //     Map<String, BigDecimal> priceMapping = this.productPriceService.getPrices(product);

    //     // TODO: store fetched price in PostgreSQL

    //     return new ResponseEntity<>(product, HttpStatus.FOUND);
    // }

    // @GetMapping(value = "/all")
    // public ResponseEntity<List<Product>> getProducts() {
    //     return new ResponseEntity<>(product, HttpStatus.FOUND);
    // }

    // @PostMapping(value = "/add", consumes = "application/json", produces = "application/json")
    // public ResponseEntity<Product> add(@RequestBody Product product) {
    //     return new ResponseEntity<>(product, HttpStatus.CREATED);
    // }
}
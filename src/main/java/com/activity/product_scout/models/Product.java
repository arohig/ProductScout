package com.activity.product_scout.models;

import com.fasterxml.jackson.annotation.JsonProperty;

// import jakarta.persistence.Entity;
// import jakarta.persistence.GeneratedValue;
// import jakarta.persistence.GenerationType;
// import jakarta.persistence.Id;
// import jakarta.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
// @Entity
// @Table(name = "products")
public class Product {
    // @Id
    // @GeneratedValue(strategy = GenerationType.IDENTITY) // auto-generate id
    // private Long id;

    @JsonProperty("Name")
    private String name;

    @JsonProperty("Title")
    private String title;

    @JsonProperty("Price")
    private Double price;

    @JsonProperty("URL")
    private String url;
}
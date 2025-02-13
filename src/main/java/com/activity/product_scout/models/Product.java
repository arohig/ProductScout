package com.activity.product_scout.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Product {
    @JsonProperty("Name")
    private String name;

    @JsonProperty("Title")
    private String title;

    @JsonProperty("Price")
    private Double price;

    @JsonProperty("URL")
    private String url;
}
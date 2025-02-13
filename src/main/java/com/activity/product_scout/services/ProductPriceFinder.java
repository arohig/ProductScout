package com.activity.product_scout.services;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class ProductPriceFinder {
    private static final System.Logger logger = System.getLogger(ProductPriceFinder.class.getName());

    // Get updated product details from a specific store
    public static String[] getProductDetails(String product) throws IOException, InterruptedException {
        String searchUrl = getUrl(product);

        // Fetch the HTML content using Jsoup
        Document doc = Jsoup.connect(searchUrl)
        .header("Cookie", "cookieConsent=true")
        .get();

        // Parse the HTML and extract the price
        String[] productDetails = extractFirstPrice(doc.html());

        return productDetails; // title, price, URL
    }

    // Get the search URL
    public static String getUrl(String product) {
        try {
            product = URLEncoder.encode(product, "UTF-8"); // encode product to UTF-8 (remove spaces)
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return "https://www.foodbasics.ca/search?filter=" + product;
    }

    // Extract first price from HTML page
    public static String[] extractFirstPrice(String html) {
        // Parse the HTML using Jsoup
        Document doc = Jsoup.parse(html);

        // Extract first price based on store-specific HTML structure
        Element priceElement = doc.selectFirst("span.price-update:containsOwn($)");

        // Extract brand and product name
        Element brandName = doc.selectFirst("span.head__brand");
        Element productName = doc.selectFirst("div.head__title");

        // Extract product link
        String baseUrl = "https://www.foodbasics.ca";
        Element productLink = doc.selectFirst("a.product-details-link");

        if (priceElement == null) {
            return null;
        } if (brandName == null) {
            return new String[] {productName.text(), priceElement.text(), baseUrl + productLink.attr("href")}; // return title, price, URL
        }

        return new String[] {brandName.text() + " " + productName.text(), priceElement.text(), baseUrl + productLink.attr("href")}; // return title, price, URL
    }
}

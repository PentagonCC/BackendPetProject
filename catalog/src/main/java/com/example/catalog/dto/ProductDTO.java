package com.example.catalog.dto;

import com.example.catalog.model.Product;

import java.math.BigDecimal;
import java.util.Objects;

public class ProductDTO {
    private String productName;
    private String productDescription;
    private String category;
    private BigDecimal price;

    public ProductDTO() {
    }

    public ProductDTO(String productName, String productDescription, String category, BigDecimal price) {
        this.productName = productName;
        this.productDescription = productDescription;
        this.category = category;
        this.price = price;
    }

    public ProductDTO convertToDTO(Product product){
       return new ProductDTO(product.getProductName(), product.getProductDescription(),
                product.getCategory().getName(), product.getPrice());
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ProductDTO that = (ProductDTO) o;
        return Objects.equals(productName, that.productName) && Objects.equals(productDescription, that.productDescription) && Objects.equals(category, that.category) && Objects.equals(price, that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productName, productDescription, category, price);
    }

    @Override
    public String toString() {
        return "ProductDTO{" +
                "productName='" + productName + '\'' +
                ", productDescription='" + productDescription + '\'' +
                ", category='" + category + '\'' +
                ", price=" + price +
                '}';
    }
}

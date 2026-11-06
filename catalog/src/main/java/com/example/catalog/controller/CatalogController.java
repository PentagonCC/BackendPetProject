package com.example.catalog.controller;

import com.example.catalog.dto.ProductDTO;
import com.example.catalog.model.Product;
import com.example.catalog.service.CatalogService;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class CatalogController {
    private final CatalogService catalogService;

    @Autowired
    public CatalogController(CatalogService catalogService) {
        this.catalogService = catalogService;
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id){
        Optional<Product> product = catalogService.getProductByID(id);
        ResponseEntity<ProductDTO> response;
        if(product.isPresent()) {
            Product productEntity = product.get();
            ProductDTO productDTO = new ProductDTO().convertToDTO(productEntity);
                        response = ResponseEntity.ok().body(productDTO);
        } else {
            response = ResponseEntity.notFound().build();
        }
        return response;
    }

    @GetMapping("/products/")
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        List<ProductDTO> productList = new ArrayList<>();
        ResponseEntity<List<ProductDTO>> response;
        List<Product> products = catalogService.getAllProducts();
        if(!products.isEmpty()) {
            for(Product product : products) {
                ProductDTO productDTO = new ProductDTO().convertToDTO(product);
                productList.add(productDTO);
            }
            response = ResponseEntity.ok().body(productList);
        } else {
            response = ResponseEntity.notFound().build();
        }
        return response;
    }
}

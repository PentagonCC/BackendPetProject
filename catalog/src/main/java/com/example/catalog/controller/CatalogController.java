package com.example.catalog.controller;

import com.example.catalog.dto.ProductDTO;
import com.example.catalog.model.Product;
import com.example.catalog.service.CatalogService;
import com.example.catalog.specification.ProductSpecification;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
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
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id) {
        Optional<Product> product = catalogService.getProductByID(id);
        ResponseEntity<ProductDTO> response;
        if (product.isPresent()) {
            Product productEntity = product.get();
            ProductDTO productDTO = new ProductDTO().convertToDTO(productEntity);
            response = ResponseEntity.ok().body(productDTO);
        } else {
            response = ResponseEntity.notFound().build();
        }
        return response;
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductDTO>> getAllProducts(@RequestParam(required = false) String category,
                                                           @RequestParam(required = false) @Min(0) BigDecimal minPrice,
                                                           @RequestParam(required = false) @Min(1) BigDecimal maxPrice,
                                                           @RequestParam(defaultValue = "0") @Min(0) int pageNumber,
                                                           @RequestParam(defaultValue = "5") @Min(1) @Max(100) int pageSize,
                                                           @RequestParam(defaultValue = "id") String sortValue,
                                                           @RequestParam(defaultValue = "ASC") String directionSort) {
        List<ProductDTO> productList = new ArrayList<>();
        ResponseEntity<List<ProductDTO>> response;
        Specification<Product> productSpecification = Specification.unrestricted();

        if (category != null)
            productSpecification = productSpecification.and(ProductSpecification.hasCategory(category));
        if (minPrice != null && maxPrice != null) productSpecification = productSpecification.
                and(ProductSpecification.hasPrice(minPrice, maxPrice));

        Sort sortMethod = directionSort.equalsIgnoreCase("DESC")
                ? Sort.by(sortValue).descending() : Sort.by(sortValue).ascending();

        Pageable currentPage = PageRequest.of(pageNumber, pageSize, sortMethod);

        Page<Product> products = catalogService.getAllProducts(productSpecification, currentPage);
        if (!products.isEmpty()) {
            for (Product product : products) {
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

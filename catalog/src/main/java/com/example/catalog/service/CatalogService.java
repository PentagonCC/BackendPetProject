package com.example.catalog.service;

import com.example.catalog.model.Product;
import com.example.catalog.repository.CatalogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CatalogService {
    private final CatalogRepository catalogRepository;

    @Autowired
    public CatalogService(CatalogRepository catalogRepository) {
        this.catalogRepository = catalogRepository;
    }

    public Optional<Product> getProductByID(Long id) {
        return catalogRepository.findById(id);
    }

    public List<Product> getAllProducts() {
        return catalogRepository.findAll();
    }
}

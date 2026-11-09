package com.example.catalog.specification;

import com.example.catalog.model.Product;
import jakarta.persistence.criteria.Expression;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public class ProductSpecification {

    public static Specification<Product> hasCategory(String category) {
        return (root, query, criteriaBuilder) -> {
            Expression<String> nonRegisterCategory = criteriaBuilder.lower(root.get("category").get("name"));
            return criteriaBuilder.equal(nonRegisterCategory, category.toLowerCase());
        };
    }

    public static Specification<Product> hasPrice(BigDecimal minPrice, BigDecimal maxPrice) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.between(root.get("price"), minPrice, maxPrice);
    }
}

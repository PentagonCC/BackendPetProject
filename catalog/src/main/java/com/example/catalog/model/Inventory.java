package com.example.catalog.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.util.Objects;

@Entity
@Table(name = "inventory")
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inventory_id")
    private Long id;
    @OneToOne(optional = false)
    @JoinColumn(name = "product_id", unique = true)
    private Product product;
    @Min(0)
    @Column(nullable = false)
    private int count;

    public Inventory() {
    }

    public Inventory(Product product, int count) {
        this.product = product;
        this.count = count;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Inventory inventory = (Inventory) o;
        return count == inventory.count && Objects.equals(id, inventory.id) && Objects.equals(product, inventory.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, product, count);
    }
}

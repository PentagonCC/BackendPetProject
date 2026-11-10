package com.example.catalog.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
@Sql(scripts = "/test_products.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class CatalogControllerTests {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:17");

    @Autowired
    private MockMvc mockMvc;

    @Test
    void categoryFilter() throws Exception {
        mockMvc.perform(get("/products")
                        .param("category", "Книги"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(3)))
                .andExpect(jsonPath("$[*].productName", containsInAnyOrder(
                        "Мастер и Маргарита", "Чистый код", "Война и мир")));
    }

    @Test
    void priceFilter() throws Exception {
        mockMvc.perform(get("/products")
                        .param("minPrice", "1000")
                        .param("maxPrice", "1500"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(2)))
                .andExpect(jsonPath("$[*].productName", containsInAnyOrder(
                        "Игровое поле-коврик 60x90", "Чистый код")));
    }

    @Test
    void priceAndCategoryFilter() throws Exception {
        mockMvc.perform(get("/products")
                        .param("category", "Аксессуары")
                        .param("minPrice", "1300")
                        .param("maxPrice", "1550"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(1)))
                .andExpect(jsonPath("$[0].productName", is(
                        "Игровое поле-коврик 60x90")));
    }

    @Test
    void incorrectPrice() throws Exception {
        mockMvc.perform(get("/products")
                        .param("minPrice", "-233")
                        .param("maxPrice", "2344"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void undefindedProduct() throws Exception {
        mockMvc.perform(get("/product/{id}", 55555))
                .andExpect(status().isNotFound());
    }

}

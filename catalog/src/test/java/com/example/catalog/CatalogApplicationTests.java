package com.example.catalog;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.context.ImportTestcontainers;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest
@AutoConfigureMockMvc
@ImportTestcontainers
@Sql(scripts = "/test_products.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class CatalogApplicationTests {

	@Test
	void contextLoads() {
	}

}

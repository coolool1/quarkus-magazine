package org.test.products;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;



@QuarkusTest
public class ProductResourceTest {

    public Long getCreatedProductId() {
        Product newProduct = new Product("New Product", "New Description", 20.0, 10);
        Long id = given()
                .contentType(ContentType.JSON)
                .body(newProduct)
                .when()
                .post("/products")
                .then()
                .log().all().extract().as(Long.class);
        return id;
    }

    // Тест для получения всех товаров
    @Test
    public void testGetAllProducts() {
        given()
            .contentType(ContentType.JSON)
        .when()
            .get("/products")
            .then().log().all();
    }

    // Тест для получения товара по ID
    @Test
    public void testGetProductById() {
        Long id = getCreatedProductId();

        given()
            .contentType(ContentType.JSON)
            .when()
            .get("/products/{id}", id)
            .then()
            .log()
            .all();
    }

    // Тест для создания товара
    @Test
    public void testCreateProduct() {
        Long id = getCreatedProductId();
    }

    // Тест для создания нескольких товаров
    @Test
    public void testCreateProducts() {
        List<Product> products = List.of(
            new Product("Product 1", "Description 1", 15.0, 3),
            new Product("Product 2", "Description 2", 25.0, 7)
        );        
        given()
            .contentType(ContentType.JSON)
            .body(products)
        .when()
            .post("/products/addProducts")
        .then()
            .log().all();
    }

    //Тест для обновления товавра
    @Test
    public void testUpdateProduct() {
        long id = getCreatedProductId();
        Product updatedProduct = new Product("Updated Product", "Updated Description", 35.0, 2);

        given()
            .contentType(ContentType.JSON)
            .body(updatedProduct)
        .when()
            .put("/products/{id}", id)
        .then()
            .log().all();
    }

    //Тест для удаления товара
    @Test
    public void testDeleteProduct() {
        long id = getCreatedProductId();

        given()
            .contentType(ContentType.JSON)
        .when()
            .delete("/products/{id}", id)
        .then()
            .log().all();
    }
}
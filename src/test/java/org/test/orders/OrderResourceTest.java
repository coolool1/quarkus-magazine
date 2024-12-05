package org.test.orders;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;

import org.junit.jupiter.api.Test;
import org.test.products.Product;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;


@QuarkusTest
public class OrderResourceTest {
    public Product getCreatedProduct() {
        Product newProduct = new Product("New Product", "New Description", 20.0, 10);
        Long id = given()
                .contentType(ContentType.JSON)
                .body(newProduct)
                .when()
                .post("/products")
                .then()
                .log().all().extract().as(Long.class);
        return Product.findById(id);
    }

    public Long getCreatedOrderId() {
        List<Product> products = new ArrayList<>();
        products.add(getCreatedProduct());
        products.add(getCreatedProduct());
        products.add(getCreatedProduct());
        Long id = given()
                .contentType(ContentType.JSON)
                .body(products)
                .when()
                .post("/orders")
                .then()
                .log().all().extract().as(Long.class);
        return id;
    }

    // Тест для получения заказа по ID
    @Test
    public void testCreateOrder() {
       System.out.println(getCreatedOrderId());
    }

    //Тест для удаления товара
    @Test
    public void testDeleteOrder() {
        long id = getCreatedOrderId();

        given()
            .contentType(ContentType.JSON)
        .when()
            .delete("/orders/{id}", id)
        .then()
            .log().all();
    }

    // Тест для получения заказа по ID
    @Inject
    OrderService OrderResource;

    @Test
    public void testGetOrderById() {
        Long id = getCreatedOrderId();

        Order order = OrderResource.getOrderById(id);
        System.out.println(order.total);
        System.out.println(order.items.size());
        for (OrderItem item : order.items) {
            System.out.println(item.product.name);
            System.out.println(item.product.description);
            System.out.println(item.product.id);
        }
    }
}

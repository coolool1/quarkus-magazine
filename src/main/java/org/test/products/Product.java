package org.test.products;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

@Entity
@Table(name="products")
public class Product extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @NotNull
    @Size(max = 100)
    public String name;

    public String description;

    @NotNull
    @Positive
    public Double price;

    @NotNull
    @Min(0)
    public Integer quantity;

    public Product() {};

    public Product(String name, String description, Double price, Integer quantity) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
    }
}

package org.test.orders;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import java.util.List;

@Entity
@Table(name="orders")
public class Order extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @NotNull
    @Positive
    public Double total;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public List<OrderItem> items;

    Order() {}

    public Order(Double total) {
        this.total = total;
    }

    public Long getId() {
        return id;
    }
}

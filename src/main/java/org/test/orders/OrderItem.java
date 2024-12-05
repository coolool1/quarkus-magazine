package org.test.orders;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Positive;

import org.test.products.Product;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.smallrye.common.constraint.NotNull;

@Entity
@Table(name = "order_items")
public class OrderItem extends PanacheEntityBase {
    @EmbeddedId
    private OrderItemId id;

    @NotNull
    @Positive
    @Column(name = "quantity_product")
    private Integer quantityProduct;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idProduct")
    @JoinColumn(name = "id_product", insertable = false, updatable = false)
    public Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idOrder")
    @JoinColumn(name = "id_order", insertable = false, updatable = false)
    public Order order;

    public OrderItem() {}

    public OrderItem(Product product, Order order) {
        this.id = new OrderItemId(product.id, order.id); // Создаем составной ключ
        this.quantityProduct = product.quantity;
        this.product = product; // Устанавливаем связь с продуктом
        this.order = order; // Устанавливаем связь с заказом
    }
}

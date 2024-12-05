package org.test.orders;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class OrderItemId implements Serializable {
    private Long idProduct;
    private Long idOrder;

    public OrderItemId(Long idProduct, Long idOrder) {
        this.idProduct = idProduct;
        this.idOrder = idOrder;
    }

    public OrderItemId() {}

    public Long getIdProduct() {
        return idProduct;
    }

    public Long getIdOrder() {
        return idOrder;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderItemId)) return false;
        OrderItemId that = (OrderItemId) o;
        return Objects.equals(idProduct, that.idProduct) && Objects.equals(idOrder, that.idOrder);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idProduct, idOrder); // Генерация хэш-кода на основе idProduct и idOrder
    }
}

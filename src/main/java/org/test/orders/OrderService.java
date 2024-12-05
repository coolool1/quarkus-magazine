package org.test.orders;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;
import java.util.List;

import org.test.products.Product;
import org.test.products.ProductResource;
import org.test.products.ProductService;

@ApplicationScoped
public class OrderService {
    private static final Logger LOG = LoggerFactory.getLogger(ProductService.class);
    
    @Inject
    ProductResource productResource;
    // REST-вызов с данными продуктов в формате JSON
    public boolean restRequest(List<Product> products) {
        return productResource.checkPositions(products);
    }

    @Transactional
    public Long createOrder(List<Product> products) {
        try {
            if (products == null || products.isEmpty() || !restRequest(products)) {
                LOG.warn("Invalid product list provided for order creation");
                return null;
            }
    
            Double total = 0.0;
    
            Order order = new Order(); 
            List<OrderItem> orderItems = new ArrayList<>();
    
            for (Product product : products) {
                Product managedProduct = Product.findById(product.id);
                if (managedProduct == null || managedProduct.quantity < product.quantity) {
                    LOG.warn("Error: Not correct product for order", product.id);
                    return null;
                }
                OrderItem orderItem = new OrderItem(managedProduct, order); 
                orderItems.add(orderItem); 
                total += managedProduct.price * product.quantity;

                managedProduct.quantity -= product.quantity;
            }
    
            order.total = total;
            order.items = orderItems;

            order.persist();
    
            return order.id;
        } catch (Exception e) {
            LOG.error("Error creating order: ", e);
            return null;
        }
    }

    @Transactional
    public void deleteOrder(Long id) {
        try {
            Order order = Order.findById(id);
            if (order != null) {
                order.delete();
                LOG.info("Deleting order id: "+ id);
            } else {
                LOG.warn("Error deleting order, not found id: "+ id);
            }
        } catch (Exception e) {
            LOG.warn("Error deleting order id: "+ id + " " + e.getMessage());
            throw e;
        }
    }

    public Order getOrderById(Long id) {
        try {
            Order order = Order.findById(id);
            if (order != null) {
                LOG.info("Getting order by id: "+ id);
                order.items.size();
            } else {
                LOG.warn("Error getting order, not found id: "+ id);
            }
            LOG.info(order.toString());
            return order;
        } catch (Exception e) {
            LOG.warn("Error getting order by id: "+ id + " " + e.getMessage());
            return null;
        }
    }
}

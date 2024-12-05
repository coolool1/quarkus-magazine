package org.test.orders;

import org.test.products.Product;

// import io.quarkus.cache.CacheKey;
// import io.quarkus.cache.CacheResult;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("/orders")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class OrderResource {
    @Inject
    OrderService orderService;

    @POST
    public Long createOrder(List<Product> products) {
        Long id = orderService.createOrder(products);
        return id;
    }

    @DELETE
    @Path("/{id}")
    public void deleteOrder(@PathParam("id") Long id) {
        orderService.deleteOrder(id);
    }

    @GET
    @Path("/{id}")
    
    @Transactional
    public Order getOrderById(Long id) {
        return orderService.getOrderById(id);
    }
}

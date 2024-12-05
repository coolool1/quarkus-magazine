package org.test.products;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.util.List;

import io.quarkus.cache.CacheInvalidate;
import io.quarkus.cache.CacheKey;
import io.quarkus.cache.CacheResult;

@Path("/products")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)

@RequestScoped
public class ProductResource {
    @Inject
    ProductService productService;

    @GET
    @CacheResult(cacheName = "allProductsCache")
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GET
    @Path("/{id}")
    @CacheResult(cacheName = "productCache")
    public Product getProductById(@CacheKey @PathParam("id") Long id) {
        return productService.getProductById(id);
    }
    
    @POST
    @CacheInvalidate(cacheName = "allProductsCache")
    @Transactional
    public long createProduct(Product product) {
        return productService.createProduct(product);
    }

    @POST
    @CacheInvalidate(cacheName = "allProductsCache")
    @Path("/addProducts")
    @Transactional
    public void createProducts(List<Product> products) {
        productService.createProducts(products);
    }

    @PUT
    @CacheInvalidate(cacheName = "allProductsCache")
    @CacheInvalidate(cacheName = "productCache")
    @Path("/{id}")
    @Transactional
    public void updateProduct(@CacheKey @PathParam("id") Long id, Product product) {
        productService.updateProduct(id, product);
    }

    @DELETE
    @CacheInvalidate(cacheName = "allProductsCache")
    @CacheInvalidate(cacheName = "productCache")
    @Path("/{id}")
    @Transactional
    public void deleteProduct(@CacheKey @PathParam("id") Long id) {
        productService.deleteProduct(id);
    }

    @POST
    @Path("/checkPositions")
    public boolean checkPositions(List<Product> products) {
        return productService.checkPositions(products);
    }
 }

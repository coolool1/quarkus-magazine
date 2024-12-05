package org.test.products;

import java.util.List;

import jakarta.transaction.Transactional;
import jakarta.enterprise.context.ApplicationScoped;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class ProductService {
    private static final Logger LOG = LoggerFactory.getLogger(ProductService.class);

    // Получение всех товаров
    public List<Product> getAllProducts() {
        try {
            List<Product> products = Product.listAll();
            LOG.info("Getting all products");
            return products;
        } catch (Exception e) {
            LOG.warn("Error getting products"+ e.getMessage());
            return null;
        }
    }

    // Получение товара по ID
    public Product getProductById(Long id) {
        try {
            Product product = Product.findById(id);
            if (product != null) {
                LOG.info("Getting product by id: "+ id);
            } else {
                LOG.warn("Error getting product, not found id: "+ id);
            }
            return product;
        } catch (Exception e) {
            LOG.warn("Error getting product by id: "+ id + " " + e.getMessage());
            return null;
        }
    }

    // Создание одного товара
    @Transactional
    public Long createProduct(Product product) {
        try {
            product.persist();
            LOG.info("Creating product: " + product.name);
            return product.id;
        } catch (Exception e) {
            LOG.warn("Error creating product: "+ e.getMessage());
            throw e;
        }
    }

    // Создание списка товаров
    @Transactional
    public void createProducts(List<Product> products) {
        try {
            for (Product product : products) {
                product.persist();
            }
            LOG.info("Creating list of products "+ products.size());
        } catch (Exception e) {
            LOG.warn("Error creating list of products: "+ e.getMessage());
            throw e;
        }
    }

    // Обновление товара
    @Transactional
    public void updateProduct(Long id, Product _product) {
        try {
            LOG.info(""+id);
            Product product = Product.findById(id);
            if (product != null) {
                product.name = _product.name;
                product.description = _product.description;
                product.price = _product.price;
                product.quantity = _product.quantity;
                LOG.info("Updating product id: "+ id);
            } else {
                LOG.warn("Error updating product, not found id: " + id);
                throw new RuntimeException("Product not found for id: " + id);
            }
        } catch (Exception e) {
            LOG.warn("Error updating product id: " + id + " " + e.getMessage());
            throw e;
        }
    }

    // Удаление товара
    @Transactional
    public void deleteProduct(Long id) {
        try {
            Product product = Product.findById(id);
            if (product != null) {
                product.delete();
                LOG.info("Deleting product id: "+ id);
            } else {
                LOG.warn("Error deleting product, not found id: "+ id);
            }
        } catch (Exception e) {
            LOG.warn("Error deleting product id: "+ id + " " + e.getMessage());
            throw e;
        }
    }

    public boolean checkPositions(List<Product> products) {
        try {
            if (products == null || products.isEmpty()) {
                LOG.warn("Not correct products list");
                return false;
            }
    
            for (Product product : products) {
                Product existProduct = getProductById(product.id);
                if (product == null || product.id == null ||  existProduct == null || existProduct.quantity<product.quantity) {
                    LOG.warn("Not correct product for Order: "+product.id);
                    return false;
                }
            }
            LOG.info("Checked products list as correct");
            return true; 
        } catch(Exception e) {
            LOG.warn("Error on checking products list: "+e);
            return false;
        }
    }
}

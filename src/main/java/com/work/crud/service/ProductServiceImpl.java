package com.work.crud.service;

import com.work.crud.dto.ProductDTO;
import com.work.crud.util.Keys;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.IntStream;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 *
 * @author linux
 */
@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

    @Autowired
    private CacheManager cacheManager;

    private static final Map<Integer, ProductDTO> products = new ConcurrentHashMap<>();

    public ProductServiceImpl() {
        createProducts();
    }

    private void createProducts() {
        IntStream.rangeClosed(1, 10)
                .forEach(i -> products.put(i, new ProductDTO(i, "Sample" + i, 10.0)));
    }

    @Override
    @Cacheable(value = "products", key = "#id")
    public Optional<ProductDTO> getProductById(Long id) {
        simulateSlowService();
        if (products.containsKey(id)) {
            return Optional.of(products.get(id));
        }
        return Optional.empty();
    }

    @CachePut(value = "products", key = "#product.id")
    @Override
    public ProductDTO saveOrUpdateProduct(ProductDTO product) {
        // Simula una actualización de producto
        log.info("Actualizando el producto en la base de datos...");
        simulateSlowService();
        if (products.containsKey(product.getId())) {
            products.put(product.getId(), product);
        } else {
            Optional<ProductDTO> maxProduct = products.values().stream().max(Comparator.comparingLong(ProductDTO::getId));
            var id = maxProduct.isPresent() ? maxProduct.get().getId() : 0;
            product.setId(id);
            products.put(id, product);
        }
        return product;
    }

    @CacheEvict(value = "products", key = "#id")
    @Override
    public void deleteProduct(Long id) {
        // Simula la eliminación de un producto       
        if (products.containsKey(id)) {
            log.info("Eliminando el producto de la base de datos...");
            products.remove(id);
            refresh(Keys.PRODUCTS);
        }
    }

    @CacheEvict(value = "products", allEntries = true)
    public void clearCache() {
        log.info("Limpiando toda la caché de productos.");
    }

    private void simulateSlowService() {
        try {
            Thread.sleep(3000L);
        } catch (InterruptedException e) {
            log.error("Error: ", e);
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public void refresh(Keys cache) {
        for (String name : cacheManager.getCacheNames()) {
            log.info("Cache name = {}", name);
        }
        cacheManager.getCache(cache.getClave()).clear();
    }

    @Override
    public void refresh() {
        cacheManager.getCacheNames().stream().forEach((var cacheName) -> {
            log.info("Cache name = {}", cacheName);
            cacheManager.getCache(cacheName).clear();
        });
    }

    @Override
    @Cacheable("products")
    public List<ProductDTO> getProducts() {
        try {
            simulateSlowService();
            List<ProductDTO> list = new ArrayList<>();
            for (Map.Entry<Integer, ProductDTO> input : products.entrySet()) {
                list.add(input.getValue());
            }
            log.info("Size: {}", list.size());
            return list;
        } catch (Exception ex) {
            log.error("Error: ", ex);
            return Collections.emptyList();
        }
    }
}

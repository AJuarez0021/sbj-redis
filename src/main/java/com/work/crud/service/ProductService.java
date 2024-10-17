package com.work.crud.service;

import com.work.crud.dto.ProductDTO;
import com.work.crud.util.Keys;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author linux
 */
public interface ProductService {

    Optional<ProductDTO> getProductById(Long id);
    
    List<ProductDTO> getProducts();
    
    void deleteProduct(Long id);
    
    ProductDTO saveOrUpdateProduct(ProductDTO product);
    
    void refresh();
    
    void refresh(Keys cache);
    
    void clearCache();
}

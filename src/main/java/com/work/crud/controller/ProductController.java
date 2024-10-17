package com.work.crud.controller;

import com.work.crud.dto.ProductDTO;
import com.work.crud.service.ProductService;
import com.work.crud.util.Keys;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author linux
 */
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/v1/products")
@Slf4j
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity<List<ProductDTO>> getProducts() {
        List<ProductDTO> response = productService.getProducts();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(path = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity<ProductDTO> getProduct(@PathVariable("id") Long id) {
        Optional<ProductDTO> response = productService.getProductById(id);
        if (!response.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(response.get(), HttpStatus.OK);
    }

    @GetMapping(value = "/refresh/{cache}", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity<Void> refresh(@PathVariable("cache") Keys cache) {
        log.info("Refrescar catalogo={}", cache);
        productService.refresh(cache);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(path = "/refresh", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity<Void> refreshAll() {
        productService.clearCache();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
         Optional<ProductDTO> response =  productService.getProductById(id);
        if (!response.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        productService.deleteProduct(id);        
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
    
    @PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity<ProductDTO> save(@RequestBody ProductDTO product) {
        ProductDTO response = productService.saveOrUpdateProduct(product);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}

package org.flow.controllers;

import org.flow.models.Product;
import org.flow.repositories.ProductRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path = "/products")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    //get all products
    @GetMapping
    public @ResponseBody ResponseEntity findAllProducts () {
        return ResponseEntity.ok(productRepository.findAll());
    }

    // get product by ID
    @GetMapping(path="/{id}")
    public @ResponseBody ResponseEntity getProductById (@PathVariable("id") Long id) {
        Optional<Product> product = productRepository.findById(id);
        return ResponseEntity.ok(product);
    }

     //create new product
    @PostMapping
    public @ResponseBody ResponseEntity addNewProduct (@RequestBody String product) {
        JSONObject jsonObject = new JSONObject(product);
        Product newProduct = new Product();
        newProduct.setName(jsonObject.getString("name"));
        newProduct.setCategory(Product.CategoryType.valueOf(jsonObject.getString("category")));
        newProduct.setPrice(jsonObject.getInt("price"));
        newProduct.setXpValue(jsonObject.getInt("xpValue"));
        productRepository.save(newProduct);
        return ResponseEntity.ok(newProduct);
    }

    // update product
    @PutMapping(path="/{id}")
    public @ResponseBody ResponseEntity updateProduct (@PathVariable("id") Long id, @RequestBody String product) {
        JSONObject jsonObject = new JSONObject(product);
        Product updatedProduct = productRepository.findById(id).get();
        updatedProduct.setName(jsonObject.getString("name"));
        updatedProduct.setCategory(Product.CategoryType.valueOf(jsonObject.getString("category")));
        updatedProduct.setPrice(jsonObject.getInt("price"));
        updatedProduct.setXpValue(jsonObject.getInt("xpValue"));
        productRepository.save(updatedProduct);
        return ResponseEntity.ok(updatedProduct);
    }

    //delete product by ID
    @DeleteMapping(path="/{id}")
    public @ResponseBody ResponseEntity deleteProduct (@PathVariable("id") Long id) {
        productRepository.deleteById(id);
        return ResponseEntity.ok(productRepository.findAll());
    }
}
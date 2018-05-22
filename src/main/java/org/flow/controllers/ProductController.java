package org.flow.controllers;

import org.flow.models.Product;
import org.flow.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path = "/products")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    //get all products
    @GetMapping(path="/")
    public @ResponseBody Iterable<Product> findAllProducts () {
        return productRepository.findAll();
    }

        // get product by ID
    @GetMapping(path="/{id}")
    public @ResponseBody Product getProductById (@PathVariable("id") Long id) {
        Optional<Product> product = productRepository.findById(id);
        return product.get();
    }

     //create new product
    @PostMapping(path="/")
    public @ResponseBody Product addNewProduct (@RequestParam String name, @RequestParam String category, @RequestParam int price, @RequestParam int xp_value) {
        Product newProduct = new Product();
        newProduct.setName(name);
        newProduct.setCategory(category);
        newProduct.setPrice(price);
        newProduct.setXpValue(xp_value);
        productRepository.save(newProduct);
        return newProduct;
    }

    // update product
    @PutMapping(path="/{id}")
    public @ResponseBody Product updateProduct (@PathVariable("id") Long id, @RequestParam String name, @RequestParam String category, @RequestParam int price, @RequestParam int xp_value) {
        Product updatedProduct = productRepository.findById(id).get();
        updatedProduct.setName(name);
        updatedProduct.setCategory(category);
        updatedProduct.setPrice(price);
        updatedProduct.setXpValue(xp_value);
        productRepository.save(updatedProduct);
        return updatedProduct;
    }

    //delete product by ID
    @DeleteMapping(path="/{id}")
    public @ResponseBody Iterable<Product> deleteProduct (@PathVariable("id") Long id) {
        productRepository.deleteById(id);
        return productRepository.findAll();
    }
}
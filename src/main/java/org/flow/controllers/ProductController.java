package org.flow.controllers;

import org.flow.models.Product;
import org.flow.models.User;
import org.flow.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Optional;

@Controller
@RequestMapping(path = "/products")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    //get all products
    @GetMapping(path="/")
    public String findAllProducts (Model model) {
        model.addAttribute("product", productRepository.findAll());
        return "product/product_list";
    }

    // get product by ID
    @GetMapping(path="/{id}")
    public String getUserById(@PathVariable("id") Long id, Model model) {
        model.addAttribute("product", productRepository.findById(id).get());
        return "product/product";
    }

    // get product create form
    @GetMapping(path="/new")
    public String newProductForm(Model model) {
        model.addAttribute("product", new Product());
        return "product/new_product";
    }

    //create new product
    @PostMapping(path="/")
    public String addNewProduct (@ModelAttribute Product product, Model model) {
        Product newProduct = new Product();
        newProduct.setName(product.getName());
        newProduct.setCategory(product.getCategory());
        newProduct.setPrice(product.getPrice());
        newProduct.setXpValue(product.getXpValue());
        productRepository.save(newProduct);
        model.addAttribute("product", productRepository.findAll());
        return "product/product_list";
    }


    // get product edit form
    @GetMapping(path="/{id}/edit")
    public String editProduct(@PathVariable("id") Long id, Model model) {
        model.addAttribute("product", productRepository.findById(id).get());
        return "product/product_edit";
    }

    // update product
    @PostMapping(path="/{id}/")
    public String updateProduct(@PathVariable("id") Long id, @ModelAttribute Product product,
                             Model model) {
        Product updatedProduct = productRepository.findById(id).get();
        updatedProduct.setName(product.getName());
        updatedProduct.setCategory(product.getCategory());
        updatedProduct.setPrice(product.getPrice());
        updatedProduct.setXpValue(product.getXpValue());
        model.addAttribute("product", productRepository.findAll());
        return "product/product_list";
    }


    //delete product by ID
    @DeleteMapping(path = "/{id}")
    public String deleteProduct(@PathVariable("id") Long id, Model model) {
        productRepository.deleteById(id);
        model.addAttribute("product", productRepository.findAll());
        return "product/product_list";
    }
}

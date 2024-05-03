package com.example.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

@Controller
public class ProductController {
//    Call model step 2
    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/")
    public String products(Model model) {
        List<Product> products = productRepository.findAll();
//        step 3
        model.addAttribute("products", products);
        return "product-list";
    }
    @GetMapping("/product/new")
    public String newProduct(Model model) {
        model.addAttribute("product", new Product());
        return "product-form";
    }
    @PostMapping("/product/save")
    public String saveProduct(@ModelAttribute("product") Product product) {
        productRepository.save(product);
        return "redirect:/";
    }
    @GetMapping("/product/edit/{id}")
    public String editProduct(@PathVariable("id") long id, Model model) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()){
            model.addAttribute("product", product.get());
            return "product-form";
        }else {
            return "redirect:/";
        }
    }
    @GetMapping("/product/delete/{id}")
    public String deleteProduct(@PathVariable("id") Long id) {
        productRepository.deleteById(id);
        return "redirect:/";
    }
}

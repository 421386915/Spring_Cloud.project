package com.wangjingran.mall.order.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wangjingran.mall.order.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/list")
    public Page<Product> listProducts(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String keyword) {
        return productService.listProducts(pageNum, pageSize, keyword);
    }

    @GetMapping("/category/{categoryId}")
    public Page<Product> listProductsByCategory(
            @PathVariable Long categoryId,
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        return productService.listProductsByCategory(categoryId, pageNum, pageSize);
    }

    @PostMapping
    public boolean saveProduct(@RequestBody Product product) {
        return productService.save(product);
    }

    @PutMapping
    public boolean updateProduct(@RequestBody Product product) {
        return productService.updateById(product);
    }

    @DeleteMapping("/{id}")
    public boolean deleteProduct(@PathVariable Long id) {
        return productService.removeById(id);
    }

    @PutMapping("/stock")
    public boolean updateStock(
            @RequestParam Long productId,
            @RequestParam Integer stock) {
        return productService.updateStock(productId, stock);
    }

    @PutMapping("/status")
    public boolean updateStatus(
            @RequestParam Long productId,
            @RequestParam Integer status) {
        return productService.updateStatus(productId, status);
    }

    @GetMapping("/{id}")
    public Product getProduct(@PathVariable Long id) {
        return productService.getById(id);
    }
}
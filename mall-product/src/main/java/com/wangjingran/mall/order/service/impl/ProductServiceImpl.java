package com.wangjingran.mall.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wangjingran.mall.order.entity.Product;
import com.wangjingran.mall.order.mapper.ProductMapper;
import com.wangjingran.mall.order.repository.ProductRepository;
import com.wangjingran.mall.order.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {

    @Override
    public Page<Product> listProducts(Integer pageNum, Integer pageSize, String keyword) {
        Page<Product> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Product> wrapper = new LambdaQueryWrapper<>();
        
        if (StringUtils.hasText(keyword)) {
            wrapper.like(Product::getName, keyword)
                   .or()
                   .like(Product::getSubtitle, keyword);
        }
        
        wrapper.orderByDesc(Product::getCreateTime);
        return page(page, wrapper);
    }

    @Override
    public Page<Product> listProductsByCategory(Long categoryId, Integer pageNum, Integer pageSize) {
        Page<Product> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Product> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Product::getCategoryId, categoryId)
               .eq(Product::getStatus, 1) // 只查询上架商品
               .orderByDesc(Product::getCreateTime);
        return page(page, wrapper);
    }

    @Override
    @Transactional
    public boolean updateStock(Long productId, Integer stock) {
        Product product = getById(productId);
        if (product == null) {
            return false;
        }
        product.setStock(stock);
        return updateById(product);
    }

    @Override
    @Transactional
    public boolean updateStatus(Long productId, Integer status) {
        Product product = getById(productId);
        if (product == null) {
            return false;
        }
        product.setStatus(status);
        return updateById(product);
    }

    @Service
    @RequiredArgsConstructor
    public static class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {

        private final ProductRepository productRepository;

        @Override
        public Page<Product> listProducts(Integer pageNum, Integer pageSize, String keyword) {
            Page<Product> page = new Page<>(pageNum, pageSize);
            LambdaQueryWrapper<Product> wrapper = new LambdaQueryWrapper<>();

            if (StringUtils.hasText(keyword)) {
                wrapper.like(Product::getName, keyword)
                       .or()
                       .like(Product::getSubtitle, keyword);
            }

            wrapper.orderByDesc(Product::getCreateTime);
            return page(page, wrapper);
        }

        @Override
        public Page<Product> listProductsByCategory(Long categoryId, Integer pageNum, Integer pageSize) {
            Page<Product> page = new Page<>(pageNum, pageSize);
            LambdaQueryWrapper<Product> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(Product::getCategoryId, categoryId)
                   .eq(Product::getStatus, 1) // 只查询上架商品
                   .orderByDesc(Product::getCreateTime);
            return page(page, wrapper);
        }

        @Override
        @Transactional
        public boolean updateStock(Long productId, Integer stock) {
            // 使用JPA的方式更新库存
            int result = productRepository.updateStock(productId, stock);
            return result > 0;
        }

        @Override
        @Transactional
        public boolean updateStatus(Long productId, Integer status) {
            // 使用JPA的方式更新状态
            int result = productRepository.updateStatus(productId, status);
            return result > 0;
        }

        @Override
        public org.springframework.data.domain.Page<Product> findProductsWithJpa(String keyword, Pageable pageable) {
            return productRepository.findByNameContainingOrSubtitleContaining(keyword, keyword, pageable);
        }

        @Override
        public org.springframework.data.domain.Page<Product> findProductsByCategoryWithJpa(Long categoryId, Pageable pageable) {
            return productRepository.findByCategoryIdAndStatus(categoryId, 1, pageable);
        }
    }
}
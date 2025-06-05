package com.wangjingran.mall.order.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wangjingran.mall.order.entity.Product;

public interface ProductService extends IService<Product> {
    /**
     * 分页查询商品列表
     */
    Page<Product> listProducts(Integer pageNum, Integer pageSize, String keyword);

    /**
     * 根据分类ID查询商品
     */
    Page<Product> listProductsByCategory(Long categoryId, Integer pageNum, Integer pageSize);

    /**
     * 更新商品库存
     */
    boolean updateStock(Long productId, Integer stock);

    /**
     * 更新商品状态（上架/下架）
     */
    boolean updateStatus(Long productId, Integer status);

    interface ProductService extends IService<Product> {
        /**
         * 分页查询商品列表
         */
        Page<Product> listProducts(Integer pageNum, Integer pageSize, String keyword);

        /**
         * 根据分类ID查询商品
         */
        Page<Product> listProductsByCategory(Long categoryId, Integer pageNum, Integer pageSize);

        /**
         * 更新商品库存
         */
        boolean updateStock(Long productId, Integer stock);

        /**
         * 更新商品状态（上架/下架）
         */
        boolean updateStatus(Long productId, Integer status);

        /**
         * 使用JPA分页查询商品
         */
        org.springframework.data.domain.Page<Product> findProductsWithJpa(String keyword, Pageable pageable);

        /**
         * 使用JPA根据分类查询商品
         */
        org.springframework.data.domain.Page<Product> findProductsByCategoryWithJpa(Long categoryId, Pageable pageable);
    }
}
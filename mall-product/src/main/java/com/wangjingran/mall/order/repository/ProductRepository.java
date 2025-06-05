package com.wangjingran.mall.order.repository;

import com.wangjingran.mall.order.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findByNameContainingOrSubtitleContaining(String name, String subtitle, Pageable pageable);

    Page<Product> findByCategoryIdAndStatus(Long categoryId, Integer status, Pageable pageable);

    @Modifying
    @Query("UPDATE Product p SET p.stock = :stock WHERE p.id = :productId")
    int updateStock(Long productId, Integer stock);

    @Modifying
    @Query("UPDATE Product p SET p.status = :status WHERE p.id = :productId")
    int updateStatus(Long productId, Integer status);
}
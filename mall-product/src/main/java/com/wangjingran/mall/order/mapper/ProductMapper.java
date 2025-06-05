package com.wangjingran.mall.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wangjingran.mall.order.entity.Product;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface ProductMapper extends BaseMapper<Product> {
    
    @Update("UPDATE product SET stock = #{stock} WHERE id = #{productId}")
    int updateStock(@Param("productId") Long productId, @Param("stock") Integer stock);
    
    @Update("UPDATE product SET status = #{status} WHERE id = #{productId}")
    int updateStatus(@Param("productId") Long productId, @Param("status") Integer status);
}
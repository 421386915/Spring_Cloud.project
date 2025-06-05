package com.wangjingran.mall.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface OrderMapper extends BaseMapper<Order> {
    
    @Update("UPDATE `order` SET status = #{status} WHERE id = #{orderId}")
    int updateStatus(@Param("orderId") Long orderId, @Param("status") Integer status);
    
    @Update("UPDATE `order` SET quantity = #{quantity}, total_amount = price * #{quantity} WHERE id = #{orderId}")
    int updateQuantity(@Param("orderId") Long orderId, @Param("quantity") Integer quantity);
    
    @Update("UPDATE `order` SET receiver_name = #{receiverName}, receiver_phone = #{receiverPhone}, receiver_address = #{receiverAddress} WHERE id = #{orderId}")
    int updateReceiver(@Param("orderId") Long orderId, @Param("receiverName") String receiverName, @Param("receiverPhone") String receiverPhone, @Param("receiverAddress") String receiverAddress);
}
package com.wangjingran.mall.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wangjingran.mall.order.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    
    @Update("UPDATE user SET status = #{status} WHERE id = #{userId}")
    int updateStatus(@Param("userId") Long userId, @Param("status") Integer status);
    
    @Update("UPDATE user SET password = #{newPassword} WHERE id = #{userId} AND password = #{oldPassword}")
    int updatePassword(@Param("userId") Long userId, @Param("oldPassword") String oldPassword, @Param("newPassword") String newPassword);
}
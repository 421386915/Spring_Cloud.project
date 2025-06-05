package com.wangjingran.mall.order.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@TableName("order_pre")
public class PreOrder {
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 订单总金额
     */
    private BigDecimal totalAmount;
    /**
     * 应付金额
     */
    private BigDecimal payAmount;
    /**
     * 收货人姓名
     */
    private String receiverName;
    /**
     * 收货人电话
     */
    private String receiverPhone;
    /**
     * 收货人地址
     */
    private String receiverAddress;
    /**
     * 订单备注
     */
    private String note;
    /**
     * 预订单状态：0->未确认；1->已确认
     */
    private Integer status;
    /**
     * 预订单过期时间
     */
    private LocalDateTime expireTime;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
    /**
     * 预订单商品列表
     */
    @TableField(exist = false)
    private List<PreOrderItem> orderItems;
    /**
     * 使用的优惠卷
     */
    @TableField (exist = false)
     private List <CouponHistory> coupons;

}

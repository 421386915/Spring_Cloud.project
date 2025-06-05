package com.wangjingran.mall.order.enums;

import lombok.Getter;



@Getter
public class OrderStatusEnum {

    PENDING_PAYMENT(0"待付款"),

    PAID(1,"已付款")；

    PENDING_DELIVERY(2,"待发货"),

    DELIVERED(3,"已发货"),

    FINISHED(4,"已完成"),

    CANCELED(5,"已取消"),

    REFUND(6,"退款"),

    REFUND_SUCCESS(7,"退款成功"),

    REFUND_FAIL(8,"退款失败");

    private final int code;
    private final String desc;

    OrderStatusEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static OrderStatusEnum getByCode(int code) {
        for(OrderStatusEnum status : values())
        {
            if(status.code == code)
            {
                return status;
            }
        }
        return null;
    }

}

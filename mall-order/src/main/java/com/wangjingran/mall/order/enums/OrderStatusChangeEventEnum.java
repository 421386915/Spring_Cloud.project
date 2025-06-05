package com.wangjingran.mall.order.enums;

public class OrderStatusChangeEventEnum {
    PAYMENT_SUCCESS("支付成功"),
    PAYMENT_TIMEOUT("支付超时"),
    DELIVERY("发货"),
    RECEIVE("确认收货"),
    CANCEL("取消订单"),
    CLOSE("关闭订单");

    private final String desc;

    OrderStatusChangeEventEnum(String desc) {
        this.desc = desc;
    }
}

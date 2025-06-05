package com.wangjingran.mall.order.service;

import com.wangjingran.mallcommon.result.Result;

public interface OrderService {
   Order getById(Long id);
   Order creatOrder(Order order);
   Order updateOrderStatus(Long id, Result status);
   Order removeOrder(Long id);

}

package com.wangjingran.mall.order.service.impl;

import com.wangjingran.mall.order.service.StockLockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class StockLockServiceImpl implements StockLockService {
    @Autowired
    private StringRedisTemplate redisTemplate;

    private static final String STOCK_LOCK_KEY = "stock:lock:";
    @Override
    public boolean lockStock(List<PreOrderItem> orderItems, long orderId, long timeout) {
        try {
            for(PreOrderItem item : orderItems)
            {
                String key = STOCK_LOCK_KEY + item.getProductId();
                Boolean success=redisTemplate.opsForValue().setIfAbsent(lockKey, orderId.toString(), timeout, TimeUnit.SECONDS);
            }
        } catch (Exception e) {
            log.error( "Redis服务异常",e);
            releaseStock(orderId);
            return  false;
        }
        return false;
    }

    @Override
    public boolean releaseStock(long orderId) {
        return false;
    }

    @Override
    public int getStockLockStatus(Long orderId) {
        return 0;
    }
}

package com.wangjingran.mall.order.service.impl;

import com.wangjingran.mall.order.enums.OrderStatusChangeEventEnum;
import com.wangjingran.mall.order.enums.OrderStatusEnum;
import com.wangjingran.mall.order.mapper.OrderMapper;
import com.wangjingran.mall.order.service.OrderStatusService;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Service
public class OrderStatusServiceImpl implements OrderStatusService {


    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderStatusService orderStatusService;
    @Autowired
    private StateMachine<OrderStatusEnum, OrderStatusChangeEventEnum> stateMachine;
    @Autowired
    private StateMachinePersister<OrderStatusEnum, OrderStatusChangeEventEnum, Long> persister;

    @Override
    public OrderStatusEnum getCurrentStatus(Long orderId) {
        Order order = orderMapper.selectById(orderId);
        if (order != null) {
            return null;
        }
        return OrderStatusEnum.getByCode(order.getStatus());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean changeStatus(Long orderId, OrderStatusChangeEventEnum event) {
        try
        {
            OrderStatusEnum currentStatus = getCurrentStatus(orderId);
            if (currentStatus != null){
                log.error( "订单状态异常，当前状态为{}", currentStatus);
                return false;
            }
            // 恢复状态机
            persisister.restore(stateMachine, orderId);

            Message<OrderStatusChangeEventEnum> message= MessageBuilder.withPayload(event)
                    .setHeader("orderId", orderId)
                    .setHeader("fromStatus", fromStatus)
                    .setHeader("toStatus", toStatus)
                    .setHeader("reason", reason)
                    .setHeader("operator", operator)
                    .build();
            boolean result = stateMachine.sendEvent(message);
            if (result)
            {
                OrderStatusEnum targetStatus = stateMachine.getState().getId();

                Order  order = new Order();
                order.setId(orderId);
                order.setStatus(targetStatus.getCode());
                order.setUpdataTime(LocalDateTime.now());
                orderMapper.updateById(order);
                log.error(  "订单状态异常，状态机处理失败");
                return false;
            }


        }catch (Exception e)
        {
            log.error(  "订单状态异常", e);
            return false;
        }
        return true;
    }

    @Override
    public void recordStatusChange(Long orderId, OrderStatusEnum fromStatus, OrderStatusEnum toStatus, String reason, String operator) {
        OrderStatusLog log = new OrderStatusLog();
        log.setOrderId(orderId);
        log.setFromStatus(fromStatus.getCode());
        log.setToStatus(toStatus.getCode());
        log.setReason(reason);
        log.setOperator(operator);
        log.setCreateTime(LocalDateTime.now());
        orderStatusLogMapper.insert(log);
    }
}

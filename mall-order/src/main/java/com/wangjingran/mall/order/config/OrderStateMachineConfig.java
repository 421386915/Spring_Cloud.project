package com.wangjingran.mall.order.config;

import com.wangjingran.mall.order.enums.OrderStatusChangeEventEnum;
import com.wangjingran.mall.order.enums.OrderStatusEnum;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableStateMachine
public class OrderStateMachineConfig extends EnumStateMachineConfigurerAdapter<OrderStatusEnum, OrderStatusChangeEventEnum>{
    @Override
    public void configure(StateMachineStateConfigurer<OrderStatusEnum, OrderStatusChangeEventEnum> states) throws Exception {
        states.withStates()
                .initial(OrderStatusEnum.WAIT_PAYMENT)
                .states(OrderStatusEnum.values());
    }
    @Override
    public void configure(StateMachineTransitionConfigurer<OrderStatusEnum, OrderStatusChangeEventEnum> transitions) throws Exception {
        transitions.withExternal()
                .source(OrderStatusEnum.WAIT_PAYMENT).target(OrderStatusEnum.WAIT_DELIVER)
                .event(OrderStatusChangeEventEnum.PAY_ORDER)
                .and()
                .withExternal()
                .source(OrderStatusEnum.WAIT_DELIVER).target(OrderStatusEnum.WAIT_RECEIVE)
    }

}

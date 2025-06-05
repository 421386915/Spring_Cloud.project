package com.wangjingran.mall.order.controller;

import com.wangjingran.mallcommon.result.Result;
import com.wangjingran.mall.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    @GetMapping("/user/{userid}")
    public Result<Order> getOrderById(@PathVariable Long id) {
        return Result.success(orderService.getById(id));
    }

    @PostMapping
    public Order creatOrder(@RequestBody Order order) {
        return orderService.creatOrder(order);
    }
    @PutMapping("/{id}/status")
    public Order updateOrderStatus(@PathVariable Long id, @RequestParam Result status) {
        return orderService.updateOrderStatus(id, status);
    }
    @DeleteMapping("/{id}")
    public Order deleteOrder(@PathVariable Long id) {
        return orderService.removeOrder(id);
    }

}

package com.wangjingran.mall.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wangjingran.mall.order.entity.Order;
import com.wangjingran.mall.order.entity.OrderItem;
import com.wangjingran.mall.order.entity.Warehouse;
import com.wangjingran.mall.order.entity.WarehouseStock;
import com.wangjingran.mall.order.mapper.WarehouseMapper;
import com.wangjingran.mall.order.mapper.WarehouseStockMapper;
import com.wangjingran.mall.order.service.WarehouseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class WarehouseServiceImpl implements WarehouseService {

    @Autowired
    private WarehouseMapper warehouseMapper;

    @Autowired
    private WarehouseStockMapper warehouseStockMapper;

    @Override
    public Map<Long, List<OrderItem>> selectWarehouse(Order order, List<OrderItem> orderItems) {
        Map<Long, List<OrderItem>> result = new HashMap<>();

        // 获取所有可用仓库
        List<Warehouse> warehouses = warehouseMapper.selectList(
                new LambdaQueryWrapper<Warehouse>()
                        .eq(Warehouse::getStatus, 1));

        if (warehouses.isEmpty()) {
            log.error("没有可用的仓库");
            return result;
        }

        // 计算用户地址与各仓库的距离
        Map<Long, Double> warehouseDistances = new HashMap<>();
        for (Warehouse warehouse : warehouses) {
            double distance = calculateDistance(order.getReceiverAddress(), warehouse);
            warehouseDistances.put(warehouse.getId(), distance);
        }

        // 为每个商品选择合适的仓库
        for (OrderItem item : orderItems) {
            Long selectedWarehouseId = null;
            double minDistance = Double.MAX_VALUE;

            // 遍历所有仓库，找出最近且有库存的仓库
            for (Warehouse warehouse : warehouses) {
                // 检查库存
                if (!checkStock(warehouse.getId(), item.getProductId(), item.getQuantity())) {
                    continue;
                }

                // 获取距离
                double distance = warehouseDistances.get(warehouse.getId());

                // 选择最近的仓库
                if (distance < minDistance) {
                    minDistance = distance;
                    selectedWarehouseId = warehouse.getId();
                }
            }

            // 如果找到合适的仓库
            if (selectedWarehouseId != null) {
                // 将商品添加到对应仓库的发货列表
                result.computeIfAbsent(selectedWarehouseId, k -> new ArrayList<>()).add(item);
            } else {
                log.error("商品{}没有找到合适的发货仓库", item.getProductId());
            }
        }

        return result;
    }

    @Override
    public double calculateDistance(String userAddress, Warehouse warehouse) {
        // 这里简化实现，实际应该调用地图API计算距离
        // 可以使用高德地图、百度地图等API

        // 示例：使用经纬度计算球面距离（Haversine公式）
        // 假设我们已经从用户地址解析出经纬度
        double userLat = 30.0; // 模拟值，实际应从地址解析
        double userLng = 120.0; // 模拟值，实际应从地址解析

        double warehouseLat = warehouse.getLatitude();
        double warehouseLng = warehouse.getLongitude();

        // 地球半径（单位：公里）
        final double EARTH_RADIUS = 6371.0;

        // 转换为弧度
        double radLat1 = Math.toRadians(userLat);
        double radLat2 = Math.toRadians(warehouseLat);
        double radLng1 = Math.toRadians(userLng);
        double radLng2 = Math.toRadians(warehouseLng);

        // 计算差值
        double deltaLat = radLat2 - radLat1;
        double deltaLng = radLng2 - radLng1;

        // Haversine公式
        double a = Math.sin(deltaLat / 2) * Math.sin(deltaLat / 2) +
                Math.cos(radLat1) * Math.cos(radLat2) *
                        Math.sin(deltaLng / 2) * Math.sin(deltaLng / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        // 计算距离
        return EARTH_RADIUS * c;
    }

    @Override
    public boolean checkStock(Long warehouseId, Long productId, Integer quantity) {
        WarehouseStock stock = warehouseStockMapper.selectOne(
                new LambdaQueryWrapper<WarehouseStock>()
                        .eq(WarehouseStock::getWarehouseId, warehouseId)
                        .eq(WarehouseStock::getProductId, productId));

        return stock != null && stock.getStock() >= quantity;
    }
}
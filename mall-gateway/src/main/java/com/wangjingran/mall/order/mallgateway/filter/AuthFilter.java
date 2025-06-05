package com.wangjingran.mall.order.mallgateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
public class AuthFilter implements GlobalFilter, Ordered {

    // 白名单路径
    private static final List<String> WHITE_LIST = Arrays.asList(
            "/api/auth/login",
            "/api/auth/register",
            "/api/product/list",
            "/api/product/detail"
    );

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getPath();

        // 白名单直接放行
        if (isWhiteList(path)) {
            return chain.filter(exchange);
        }

        // 获取token
        String token = getToken(request);

        // 简单校验token
        if (token == null || token.isEmpty()) {
            ServerHttpResponse response = exchange.getResponse();
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }

        // TODO: 实际项目中应该调用认证服务验证token

        // 将用户信息传递到下游服务
        ServerHttpRequest newRequest = request.mutate()
                .header("X-User-Id", "1") // 模拟用户ID
                .build();

        return chain.filter(exchange.mutate().request(newRequest).build());
    }

    private boolean isWhiteList(String path) {
        return WHITE_LIST.stream().anyMatch(path::startsWith);
    }

    private String getToken(ServerHttpRequest request) {
        List<String> headers = request.getHeaders().get("Authorization");
        if (headers != null && !headers.isEmpty()) {
            String auth = headers.get(0);
            if (auth != null && auth.startsWith("Bearer ")) {
                return auth.substring(7);
            }
        }
        return null;
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
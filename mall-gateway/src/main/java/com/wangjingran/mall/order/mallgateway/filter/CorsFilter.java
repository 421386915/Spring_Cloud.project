package com.wangjingran.mall.order.mallgateway.filter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Configuration
public class CorsFilter {
    private static final String ALLOWED_HEADERS = "X-Requested-With,Authorization,Content-Type,Accept,Origin";
    private static final String ALLOWED_METHODS = "GET,POST,PUT,DELETE,OPTIONS";
    private static final String ALLOWED_ORIGIN = "*";
    private static final String MAX_AGE = "3600";

    @Bean
    public WebFilter corsFilter() {
        return (ServerWebExchange ctx, WebFilterChain chain) -> {
            ServerHttpRequest request = ctx.getRequest();
            if (CorsUtils.isCorsRequest(request)) {
               ServerHttpResponse response = ctx.getResponse();
               HttpHeaders headers = response.getHeaders();
               headers.add("Access-Control-Allow-Origin", ALLOWED_ORIGIN);
               headers.add("Access-Control-Allow-Methods", ALLOWED_METHODS);
               headers.add("Access-Control-Max-Age", MAX_AGE);
               headers.add("Access-Control-Allow-Headers", ALLOWED_HEADERS);
               headers.add("Access-Control-Allow-Credentials", "true");
               if (request.getMethod() == HttpMethod.OPTIONS) {
                   response.setStatusCode(HttpStatus.OK);
                   return Mono.empty();
               }

            }
            return chain.filter(ctx);
        };
    }


}

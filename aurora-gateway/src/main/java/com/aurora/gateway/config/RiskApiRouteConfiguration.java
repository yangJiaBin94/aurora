package com.aurora.gateway.config;

import com.aurora.gateway.filter.risk.api.RiskApiGatewayFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: Nick
 * @create: 2022-03-22 11:09
 **/
@Configuration
public class RiskApiRouteConfiguration {

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(r -> r
                        .path("/risk/**")
                        .uri("lb://risk-api")
                        .filters(new RiskApiGatewayFilter())
                        .id("risk-api"))
                .route(r -> r
                        .path("/loan/**")
                        .uri("lb://loan-api")
                        .id("loan-api"))
                .route(r -> r
                        .path("/calculation/**")
                        .uri("lb://calculation-api")
                        .id("calculation-api"))
                .build();

    }
}

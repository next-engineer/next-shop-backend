package com.example.next.app.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI shopOpenAPI() {
        return new OpenAPI().info(new Info()
                .title("Next Shop API")
                .description("상품 검색/조회 API 문서")
                .version("v1"));
    }
}

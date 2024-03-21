package com.baekopa.backend.global.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

@Configuration
public class SwaggerConfig {
    @Value("${springdoc.swagger-ui.info.title}")
    private String title;
    @Value("${springdoc.swagger-ui.info.description}")
    private String description;
    @Value("${springdoc.swagger-ui.info.version}")
    private String version;

    final static String AUTHORIZATION = "Authorization";

    @Bean
    public OpenAPI openAPI() {

        return new OpenAPI()
                .components(components())
                .info(apiInfo());
    }

    private Components components() {
        return new Components()
                .addSecuritySchemes(AUTHORIZATION, securityScheme());
    }

    private SecurityScheme securityScheme() {
        return new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("Bearer")
                .bearerFormat("JWT")
                .in(SecurityScheme.In.HEADER)
                .name(HttpHeaders.AUTHORIZATION);
    }

    private Info apiInfo() {
        return new Info()
                .title(title)
                .description(description)
                .version(version);
    }
}
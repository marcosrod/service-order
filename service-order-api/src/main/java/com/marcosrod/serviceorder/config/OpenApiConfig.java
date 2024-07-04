package com.marcosrod.serviceorder.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig implements OpenApiCustomizer {

    @Override
    public void customise(OpenAPI openApi) {
        var securitySchemeName = "bearerAuth";
        openApi.getComponents().addSecuritySchemes(securitySchemeName, new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT"));
        openApi.addSecurityItem(new SecurityRequirement().addList(securitySchemeName));
    }
}

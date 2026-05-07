package com.demoworks.demodelivery.delivery.tracking.infrastructure.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
    info = @Info(
        title = "Delivery Tracking API",
        version = "1.0.0",
        description = "API para rastreamento e gerenciamento de entregas no sistema DemoDelivery. " +
                     "Esta API permite criar, editar, consultar e rastrear entregas em tempo real.",
        contact = @Contact(
            name = "DemoWorks Team",
            email = "support@demoworks.com",
            url = "https://demoworks.com"
        ),
        license = @License(
            name = "MIT",
            url = "https://opensource.org/licenses/MIT"
        )
    ),
    servers = {
        @Server(
            url = "http://localhost:8080",
            description = "Local Development Server"
        ),
        @Server(
            url = "http://gateway:8080",
            description = "Production Gateway Server"
        )
    }
)
@SecurityScheme(
    name = "bearerAuth",
    type = SecuritySchemeType.HTTP,
    scheme = "bearer",
    bearerFormat = "JWT",
    description = "JWT auth token"
)
public class OpenApiConfig {
}


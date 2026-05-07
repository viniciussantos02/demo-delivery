package com.demoworks.demodelivery.courier.management.infrastructure.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
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
        title = "Courier Management API",
        version = "1.0.0",
        description = "API para gerenciamento de couriers no sistema DemoDelivery. " +
                     "Esta API permite registrar, consultar e gerenciar couriers, além de calcular seus pagamentos.",
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
            url = "http://localhost:8081",
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


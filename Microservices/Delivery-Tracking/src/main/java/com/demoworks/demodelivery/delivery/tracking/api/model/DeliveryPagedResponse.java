package com.demoworks.demodelivery.delivery.tracking.api.model;

import com.demoworks.demodelivery.delivery.tracking.domain.model.Delivery;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "Resposta paginada contendo lista de entregas")
public record DeliveryPagedResponse(
        @Schema(description = "Lista de entregas")
        List<Delivery> content,
        @Schema(description = "Informações de paginação")
        PageInfo page
) {
    public record PageInfo(
            @Schema(description = "Número da página atual (começando de 0)")
            int number,
            @Schema(description = "Tamanho da página")
            int size,
            @Schema(description = "Total de elementos")
            long totalElements,
            @Schema(description = "Total de páginas")
            int totalPages
    ) {}
}


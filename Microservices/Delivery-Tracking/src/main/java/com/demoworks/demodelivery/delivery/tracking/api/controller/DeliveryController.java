package com.demoworks.demodelivery.delivery.tracking.api.controller;


import com.demoworks.demodelivery.delivery.tracking.api.model.DeliveryDTO;
import com.demoworks.demodelivery.delivery.tracking.api.model.DeliveryDetailsDTO;
import com.demoworks.demodelivery.delivery.tracking.api.model.DeliveryPagedResponse;
import com.demoworks.demodelivery.delivery.tracking.domain.model.Delivery;
import com.demoworks.demodelivery.delivery.tracking.domain.service.DeliveryCheckpointService;
import com.demoworks.demodelivery.delivery.tracking.domain.service.DeliveryConsultationService;
import com.demoworks.demodelivery.delivery.tracking.domain.service.DeliveryPreparationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/deliveries")
public class DeliveryController {

    private final DeliveryPreparationService deliveryPreparationService;
    private final DeliveryCheckpointService deliveryCheckpointService;
    private final DeliveryConsultationService deliveryConsultationService;

    @PostMapping
    @Operation(
            summary = "Criar um rascunho de entrega",
            description = "Cria uma nova entrega no estado de rascunho. A entrega ainda não está confirmada e pode ser editada livremente."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Entrega criada com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Delivery.class))),
            @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos", content = @Content()),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content())
    })
    public ResponseEntity<Delivery> draftDelivery(@Valid @RequestBody DeliveryDTO input) {
        return ResponseEntity.status(HttpStatus.CREATED).body(deliveryPreparationService.draftDelivery(input));
    }

    @PutMapping("/{deliveryId}")
    @Operation(
            summary = "Editar uma entrega",
            description = "Edita os dados de uma entrega existente. Apenas entregas em estado de rascunho podem ser editadas."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Entrega atualizada com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Delivery.class))),
            @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos", content = @Content()),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content())
    })
    public ResponseEntity<Delivery> editDelivery(
            @Parameter(description = "ID único da entrega") @PathVariable UUID deliveryId,
            @Valid @RequestBody DeliveryDTO input
    ) {
        return ResponseEntity.ok(deliveryPreparationService.editDelivery(deliveryId, input));
    }

    @PutMapping("/{deliveryId}/add-delivery-courier")
    @Operation(
            summary = "Adicionar courier à entrega",
            description = "Atribui um courier específico a uma entrega. O courier é responsável pelo transporte e entrega."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Courier adicionado com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Delivery.class))),
            @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos ou courier inválido", content = @Content()),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content())
    })
    public ResponseEntity<Delivery> addDeliveryCourier(
            @Parameter(description = "ID único da entrega") @PathVariable UUID deliveryId,
            @Valid @RequestBody DeliveryDetailsDTO input
    ) {
        return ResponseEntity.ok(deliveryPreparationService.addDeliveryCourier(deliveryId, input.courierId()));
    }

    @GetMapping
    @Operation(
            summary = "Listar todas as entregas",
            description = "Retorna uma lista paginada de todas as entregas no sistema. Suporta paginação através de parâmetros page e size."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de entregas recuperada com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = DeliveryPagedResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos", content = @Content()),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content())
    })
    public ResponseEntity<PagedModel<Delivery>> findallDeliveries(
            @PageableDefault Pageable pageable
    ) {
        return ResponseEntity.ok(deliveryConsultationService.findAllDeliveries(pageable));
    }

    @GetMapping("/{deliveryId}")
    @Operation(
            summary = "Obter detalhes de uma entrega",
            description = "Recupera os detalhes completos de uma entrega específica pelo seu ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Detalhes da entrega recuperados com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Delivery.class))),
            @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos", content = @Content()),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content())
    })
    public ResponseEntity<Delivery> findDeliveryById(
            @Parameter(description = "ID único da entrega") @PathVariable UUID deliveryId
    ) {
        return ResponseEntity.ok(deliveryConsultationService.findDeliveryById(deliveryId));
    }

    @PostMapping("/{deliveryId}/placement")
    @Operation(
            summary = "Colocar entrega para coleta",
            description = "Marca uma entrega como pronta para coleta. A entrega muda de estado e fica aguardando um courier."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Entrega colocada para coleta com sucesso", content = @Content()),
            @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos", content = @Content()),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content())
    })
    public ResponseEntity<Void> placeDelivery(
            @Parameter(description = "ID único da entrega") @PathVariable UUID deliveryId
    ) {
        deliveryCheckpointService.placeDelivery(deliveryId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{deliveryId}/pickups")
    @Operation(
            summary = "Realizar coleta da entrega",
            description = "Marca uma entrega como coletada. O courier inicia o transporte da encomenda até o endereço de destino."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Coleta realizada com sucesso", content = @Content()),
            @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos", content = @Content()),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content())
    })
    public ResponseEntity<Void> pickupDelivery(
            @Parameter(description = "ID único da entrega") @PathVariable UUID deliveryId,
            @Parameter(description = "ID do courier realizando a coleta (opcional)") @RequestParam @Nullable UUID courierId
    ) {
        deliveryCheckpointService.pickupDelivery(deliveryId, courierId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{deliveryId}/completion")
    @Operation(
            summary = "Completar entrega",
            description = "Marca uma entrega como concluída. A encomenda foi entregue ao destinatário e o pedido está finalizado."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Entrega completada com sucesso", content = @Content()),
            @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos", content = @Content()),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content())
    })
    public ResponseEntity<Void> completeDelivery(
            @Parameter(description = "ID único da entrega") @PathVariable UUID deliveryId
    ) {
        deliveryCheckpointService.completeDelivery(deliveryId);
        return ResponseEntity.ok().build();
    }
}

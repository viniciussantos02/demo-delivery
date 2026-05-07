package com.demoworks.demodelivery.courier.management.api.controller;

import com.demoworks.demodelivery.courier.management.api.model.CourierDTO;
import com.demoworks.demodelivery.courier.management.api.model.CourierPayoutCalculationInput;
import com.demoworks.demodelivery.courier.management.api.model.CourierPayoutResultModel;
import com.demoworks.demodelivery.courier.management.api.model.CourierPagedResponse;
import com.demoworks.demodelivery.courier.management.domain.model.Courier;
import com.demoworks.demodelivery.courier.management.domain.service.CourierConsultationService;
import com.demoworks.demodelivery.courier.management.domain.service.CourierPayoutService;
import com.demoworks.demodelivery.courier.management.domain.service.CourierRegistrationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/couriers")
@Tag(
    name = "Courier Management",
    description = "Endpoints para gerenciar couriers, incluindo registro, consulta e cálculo de pagamentos"
)
public class CourierController {

    private final CourierRegistrationService courierRegistrationService;
    private final CourierConsultationService courierConsultationService;
    private final CourierPayoutService courierPayoutService;

    @PostMapping
    @Operation(
        summary = "Registrar um novo courier",
        description = "Cria um novo courier no sistema. O courier fica disponível para ser atribuído a entregas."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Courier registrado com sucesso",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Courier.class))),
        @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos", content = @Content()),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content())
    })
    public ResponseEntity<Courier> createCourier(@Valid @RequestBody CourierDTO courierInput) {
        return ResponseEntity.status(HttpStatus.CREATED).body(courierRegistrationService.createRegistration(courierInput));
    }

    @PutMapping("/{courierId}")
    @Operation(
        summary = "Atualizar dados de um courier",
        description = "Atualiza as informações de um courier existente no sistema."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Courier atualizado com sucesso",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Courier.class))),
        @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos", content = @Content()),
        @ApiResponse(responseCode = "404", description = "Courier não encontrado", content = @Content()),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content())
    })
    public ResponseEntity<Courier> updateCourier(
        @Parameter(description = "ID único do courier") @PathVariable UUID courierId,
        @Valid @RequestBody CourierDTO courierInput
    ) {
        return ResponseEntity.ok(courierRegistrationService.updateCourier(courierId, courierInput));
    }

    @GetMapping
    @Operation(
        summary = "Listar todos os couriers",
        description = "Retorna uma lista paginada de todos os couriers registrados no sistema."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de couriers recuperada com sucesso",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CourierPagedResponse.class))),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content())
    })
    public ResponseEntity<PagedModel<Courier>> findAllCouriers(@PageableDefault Pageable pageable) {
        return ResponseEntity.ok(courierConsultationService.findAllCouriers(pageable));
    }

    @GetMapping("/{courierId}")
    @Operation(
        summary = "Obter detalhes de um courier",
        description = "Recupera as informações completas de um courier específico pelo seu ID."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Detalhes do courier recuperados com sucesso",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Courier.class))),
        @ApiResponse(responseCode = "404", description = "Courier não encontrado", content = @Content()),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content())
    })
    public ResponseEntity<Courier> findCourierById(
        @Parameter(description = "ID único do courier") @PathVariable UUID courierId
    ) {
        return ResponseEntity.ok(courierConsultationService.findCourierById(courierId));
    }

    @PostMapping("/payout-calculation")
    @Operation(
        summary = "Calcular pagamento do courier",
        description = "Calcula o valor do pagamento do courier baseado na distância percorrida em quilômetros."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Pagamento calculado com sucesso",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CourierPayoutResultModel.class))),
        @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos", content = @Content()),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content())
    })
    public ResponseEntity<CourierPayoutResultModel> calculateCourierPayout(@Valid @RequestBody CourierPayoutCalculationInput input) {
        BigDecimal payoutFee = courierPayoutService.calculatePayout(input.distanceInKm());

        return ResponseEntity.ok(new CourierPayoutResultModel(payoutFee));
    }
}
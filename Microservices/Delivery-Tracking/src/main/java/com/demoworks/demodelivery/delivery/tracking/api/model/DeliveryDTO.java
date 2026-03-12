package com.demoworks.demodelivery.delivery.tracking.api.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record DeliveryDTO(
        @Valid
        @NotNull
        ContactPointDTO sender,

        @Valid
        @NotNull
        ContactPointDTO recipient,

        @Valid
        @NotEmpty
        @Size(min = 1)
        List<ItemDTO> items
) {
}

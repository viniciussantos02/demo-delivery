package com.demoworks.demodelivery.delivery.tracking.api.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ItemDTO(@NotBlank String name,
                      @NotNull @Min(1) Integer quantity)
{}

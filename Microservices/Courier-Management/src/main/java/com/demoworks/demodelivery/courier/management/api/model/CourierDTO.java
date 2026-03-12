package com.demoworks.demodelivery.courier.management.api.model;

import jakarta.validation.constraints.NotBlank;

public record CourierDTO(@NotBlank String name, @NotBlank String phone)
{}

package com.demoworks.demodelivery.delivery.tracking.api.model;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CourierIdDTO(@NotNull UUID courierId) {}

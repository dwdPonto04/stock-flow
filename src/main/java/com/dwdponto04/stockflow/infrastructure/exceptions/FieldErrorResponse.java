package com.dwdponto04.stockflow.infrastructure.exceptions;

public record FieldErrorResponse(
        String field,
        String message
) {}

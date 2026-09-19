package com.dwdponto04.stockflow.infrastructure.exceptions;

import java.util.List;

public record ErrorResponse(
        int status,
        String message,
        List<FieldErrorResponse> errors
) {
}

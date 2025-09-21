package com.felipe.user.infra;

import lombok.Builder;


@Builder
public record RestErrorMessage(
    String name,
    String message,
    String action,
    int status_code
) {}


package com.felipe.user.dto;

import lombok.Builder;

import java.util.UUID;

@Builder
public record EmailDto (
        UUID userId,
        String emailTo,
        String emailSubject,
        String body
) {}


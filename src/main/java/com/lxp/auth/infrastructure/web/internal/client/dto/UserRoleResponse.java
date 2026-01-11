package com.lxp.auth.infrastructure.web.internal.client.dto;

import java.time.LocalDateTime;

public record UserRoleResponse(String role, String status, LocalDateTime deletedAt) {
}

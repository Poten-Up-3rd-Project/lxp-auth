package com.lxp.auth.application.port.required.query;

import java.time.LocalDateTime;

public record UserRoleQuery(String role, String status, LocalDateTime deletedAt) {
}

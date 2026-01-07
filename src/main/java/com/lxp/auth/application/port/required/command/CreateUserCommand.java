package com.lxp.auth.application.port.required.command;

import java.util.List;

public record CreateUserCommand(
    String userId,
    String name,
    String email,
    String role,
    List<Long> tagIds,
    String level
) {
}

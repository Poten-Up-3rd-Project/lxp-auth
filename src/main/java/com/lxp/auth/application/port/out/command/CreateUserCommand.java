package com.lxp.auth.application.port.out.command;

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

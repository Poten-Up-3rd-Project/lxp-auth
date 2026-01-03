package com.lxp.auth.application.port.in.command;

import com.lxp.common.application.cqrs.Command;

import java.util.List;

public record LocalAuthRegisterCommand(
    String email,
    String password,
    String name,
    String role,
    List<Long> tagIds,
    String level
) implements Command {
}

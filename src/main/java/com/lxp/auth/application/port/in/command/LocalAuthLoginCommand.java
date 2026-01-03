package com.lxp.auth.application.port.in.command;

import com.lxp.common.application.cqrs.Command;

public record LocalAuthLoginCommand(
    String email,
    String password
) implements Command {
}

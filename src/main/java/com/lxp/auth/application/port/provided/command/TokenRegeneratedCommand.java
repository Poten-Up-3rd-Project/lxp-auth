package com.lxp.auth.application.port.provided.command;

import com.lxp.common.application.cqrs.Command;

import java.util.List;

public record TokenRegeneratedCommand(
    String userId, List<String> role, String email, String token
) implements Command {
}

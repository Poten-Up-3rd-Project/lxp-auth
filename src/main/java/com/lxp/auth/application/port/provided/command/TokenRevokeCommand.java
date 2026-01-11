package com.lxp.auth.application.port.provided.command;

import com.lxp.common.application.cqrs.Command;

public record TokenRevokeCommand(String userId, String token) implements Command {
}

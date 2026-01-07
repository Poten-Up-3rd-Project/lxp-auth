package com.lxp.auth.application.port.provided.command;

public record LocalAuthLogoutCommand(
    String token
) {
}

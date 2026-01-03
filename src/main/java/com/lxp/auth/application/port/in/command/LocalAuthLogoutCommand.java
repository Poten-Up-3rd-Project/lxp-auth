package com.lxp.auth.application.port.in.command;

public record LocalAuthLogoutCommand(
    String token
) {
}

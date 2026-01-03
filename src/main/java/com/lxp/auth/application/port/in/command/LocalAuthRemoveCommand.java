package com.lxp.auth.application.port.in.command;

import com.lxp.auth.domain.common.model.vo.UserId;
import com.lxp.common.application.cqrs.Command;

public record LocalAuthRemoveCommand(UserId userId) implements Command {
}

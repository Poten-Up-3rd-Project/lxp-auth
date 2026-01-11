package com.lxp.auth.application.port.required;

import com.lxp.auth.application.port.required.command.CreateUserCommand;
import com.lxp.auth.application.port.required.query.UserRoleQuery;
import com.lxp.auth.domain.common.model.vo.UserId;

public interface UserServicePort {

    void createUser(CreateUserCommand command);

    UserRoleQuery getUserRole(UserId userId);

}

package com.lxp.auth.application.port.required;

import com.lxp.auth.application.port.required.command.CreateUserCommand;
import com.lxp.auth.application.port.required.query.UserInfo;

public interface UserServicePort {

    void createUser(CreateUserCommand command);

    UserInfo getUserInfo(String userId);

}

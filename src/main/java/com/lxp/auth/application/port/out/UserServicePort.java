package com.lxp.auth.application.port.out;

import com.lxp.auth.application.port.out.command.CreateUserCommand;
import com.lxp.auth.application.port.out.query.UserInfo;

public interface UserServicePort {

    void createUser(CreateUserCommand command);

    UserInfo getUserInfo(String userId);

}

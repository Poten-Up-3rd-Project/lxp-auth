package com.lxp.auth.infrastructure.web.external.adapter;

import com.lxp.auth.application.port.out.command.CreateUserCommand;
import com.lxp.auth.application.port.out.query.UserInfo;
import com.lxp.auth.infrastructure.web.external.dto.CreateUserRequest;
import com.lxp.auth.infrastructure.web.external.dto.UserResponse;
import org.springframework.stereotype.Component;

@Component
public class UserServiceMapper {

    public CreateUserRequest createUserRequest(CreateUserCommand command) {
        return new CreateUserRequest(
            command.userId(),
            command.name(),
            command.email(),
            command.role(),
            command.tagIds(),
            command.level()
        );
    }

    public UserInfo toUserInfo(UserResponse userResponse) {
        return new UserInfo(userResponse.role());
    }

}

package com.lxp.auth.infrastructure.web.external.client.adapter;

import com.lxp.auth.application.port.required.command.CreateUserCommand;
import com.lxp.auth.application.port.required.query.UserInfo;
import com.lxp.auth.infrastructure.web.external.client.dto.CreateUserRequest;
import com.lxp.auth.infrastructure.web.external.client.dto.UserResponse;
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

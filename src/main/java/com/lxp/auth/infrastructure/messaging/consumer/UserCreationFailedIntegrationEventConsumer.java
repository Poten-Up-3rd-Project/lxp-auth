package com.lxp.auth.infrastructure.messaging.consumer;

import com.lxp.auth.application.event.UserCreationFailedIntegrationEvent;
import com.lxp.auth.application.in.usecase.LocalAuthRemoveUseCase;
import com.lxp.auth.infrastructure.messaging.mapper.AuthIntegrationMapper;
import com.lxp.common.application.port.in.IntegrationEventHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserCreationFailedIntegrationEventConsumer implements IntegrationEventHandler<UserCreationFailedIntegrationEvent> {

    private final LocalAuthRemoveUseCase localAuthRemoveUseCase;
    private final AuthIntegrationMapper authIntegrationMapper;

    @Override
    @RabbitListener(queues = "${rabbitmq.queue.user-events}")
    public void handle(UserCreationFailedIntegrationEvent event) {
        String userId = event.getUserId();
        log.warn("Received UserCreationFailedIntegrationEvent: userId={}, reason={}", userId, event.getReason());

        try {
            localAuthRemoveUseCase.execute(authIntegrationMapper.toRemoveCommand(event));
            log.info("Successfully compensated (deleted) LocalAuth: userId={}", userId);
        } catch (Exception e) {
            log.error("Failed to compensate LocalAuth: userId={}", userId, e);
            throw e;
        }
    }

    @Override
    public Class<UserCreationFailedIntegrationEvent> supportedEventType() {
        return UserCreationFailedIntegrationEvent.class;
    }
}

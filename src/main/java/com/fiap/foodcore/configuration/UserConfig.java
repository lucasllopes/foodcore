package com.fiap.foodcore.configuration;

import com.fiap.foodcore.application.gateway.UserGateway;
import com.fiap.foodcore.application.service.strategy.CreateUserStrategyFactory;
import com.fiap.foodcore.application.usecase.*;
import com.fiap.foodcore.infrastructure.gateways.UserRepositoryGateway;
import com.fiap.foodcore.infrastructure.gateways.persistence.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class UserConfig {

    @Bean
    UserGateway userGateway(UserRepository userRepository) {
        return new UserRepositoryGateway(userRepository);
    }

    @Bean
    public CreateUserInteractor createUserInteractor(CreateUserStrategyFactory strategyFactory) {
        return new CreateUserInteractor(strategyFactory);
    }

    @Bean
    public FindUserByIdInteractor findUserByIdInteractor(UserGateway userGateway) {
        return new FindUserByIdInteractor(userGateway);
    }

    @Bean
    public ListUserInteractor listUserInteractor(UserGateway userGateway) {
        return new ListUserInteractor(userGateway);
    }

    @Bean
    public UpdateUserInteractor updateUserInteractor(UserGateway userGateway) {
        return new UpdateUserInteractor(userGateway);
    }

    @Bean
    public DeleteUserInteractor deleteUserInteractor(UserGateway userGateway) {
        return new DeleteUserInteractor(userGateway);
    }

    @Bean
    public ChangePasswordInteractor changePasswordInteractor(UserGateway userGateway,
                                                             PasswordEncoder passwordEncoder) {
        return new ChangePasswordInteractor(userGateway, passwordEncoder);
    }
}

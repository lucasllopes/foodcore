package com.fiap.foodcore.infrastructure.configuration;

import com.fiap.foodcore.application.gateway.PasswordEncryptionGateway;
import com.fiap.foodcore.application.gateway.RestaurantGateway;
import com.fiap.foodcore.application.gateway.UserGateway;
import com.fiap.foodcore.application.gateway.UserTypeGateway;
import com.fiap.foodcore.application.strategy.CreateUserStrategyFactory;
import com.fiap.foodcore.application.usecase.*;
import com.fiap.foodcore.infrastructure.gateways.BCryptPasswordEncryptionGateway;
import com.fiap.foodcore.infrastructure.gateways.RestaurantRepositoryGateway;
import com.fiap.foodcore.infrastructure.gateways.UserRepositoryGateway;
import com.fiap.foodcore.infrastructure.gateways.UserTypeRepositoryGateway;
import com.fiap.foodcore.infrastructure.gateways.persistence.RestaurantRepository;
import com.fiap.foodcore.infrastructure.gateways.persistence.UserRepository;
import com.fiap.foodcore.infrastructure.gateways.persistence.UserTypeRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class BeanConfig {

    @Bean
    UserGateway userGateway(UserRepository userRepository) {
        return new UserRepositoryGateway(userRepository);
    }

    @Bean
    PasswordEncryptionGateway passwordEncryptionGateway(PasswordEncoder passwordEncoder) {
        return new BCryptPasswordEncryptionGateway(passwordEncoder);
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
                                                             PasswordEncryptionGateway passwordEncryptionGateway) {
        return new ChangePasswordInteractor(userGateway, passwordEncryptionGateway);
    }

    @Bean
    RestaurantGateway restaurantGateway(RestaurantRepository restaurantRepository) {
        return new RestaurantRepositoryGateway(restaurantRepository);
    }

    @Bean
    public CreateRestaurantInteractor createRestaurantInteractor(RestaurantGateway restaurantGateway, UserGateway userGateway) {
        return new CreateRestaurantInteractor(restaurantGateway, userGateway);
    }

    @Bean
    public FindRestaurantByIdInteractor findRestaurantByIdInteractor(RestaurantGateway restaurantGateway) {
        return new FindRestaurantByIdInteractor(restaurantGateway);
    }

    @Bean
    public ListRestaurantInteractor listRestaurantInteractor(RestaurantGateway restaurantGateway) {
        return new ListRestaurantInteractor(restaurantGateway);
    }
    @Bean
    public UpdateRestaurantInteractor updateRestaurantInteractor(RestaurantGateway restaurantGateway) {
        return new UpdateRestaurantInteractor(restaurantGateway);
    }

    @Bean
    public DeleteRestaurantInteractor deleteRestaurantInteractor(RestaurantGateway restaurantGateway, FindRestaurantByIdInteractor findRestaurantByIdInteractor) {
        return new DeleteRestaurantInteractor(restaurantGateway, findRestaurantByIdInteractor);
    }

    @Bean
    public UserTypeGateway userTypeGateway(UserTypeRepository userTypeRepository) {
        return new UserTypeRepositoryGateway(userTypeRepository);
    }

    @Bean
    public CreateUserTypeInteractor createUserTypeInteractor(UserTypeGateway userTypeGateway) {
        return new CreateUserTypeInteractor(userTypeGateway);
    }
}

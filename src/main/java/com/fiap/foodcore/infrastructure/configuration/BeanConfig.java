package com.fiap.foodcore.infrastructure.configuration;

import com.fiap.foodcore.application.gateway.PasswordEncryptionGateway;
import com.fiap.foodcore.application.gateway.RestaurantGateway;
import com.fiap.foodcore.application.gateway.UserGateway;
import com.fiap.foodcore.application.gateway.UserTypeGateway;
import com.fiap.foodcore.application.strategy.CreateUserStrategyFactory;
import com.fiap.foodcore.application.usecase.*;
import com.fiap.foodcore.application.usecase.interactor.restaurant.*;
import com.fiap.foodcore.application.usecase.interactor.user.*;
import com.fiap.foodcore.application.usecase.interactor.usertype.*;
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
    public CreateUserUseCase createUserInteractor(CreateUserStrategyFactory strategyFactory) {
        return new CreateUserInteractor(strategyFactory);
    }

    @Bean
    public FindUserByIdUseCase findUserByIdInteractor(UserGateway userGateway) {
        return new FindUserByIdInteractor(userGateway);
    }

    @Bean
    public ListUserUseCase listUserInteractor(UserGateway userGateway) {
        return new ListUserInteractor(userGateway);
    }

    @Bean
    public UpdateUserUseCase updateUserInteractor(UserGateway userGateway) {
        return new UpdateUserInteractor(userGateway);
    }

    @Bean
    public DeleteUserUseCase deleteUserInteractor(UserGateway userGateway) {
        return new DeleteUserInteractor(userGateway);
    }

    @Bean
    public ChangePasswordUseCase changePasswordInteractor(UserGateway userGateway,
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
    public FindRestaurantByIdUseCase findRestaurantByIdInteractor(RestaurantGateway restaurantGateway) {
        return new FindRestaurantByIdInteractor(restaurantGateway);
    }

    @Bean
    public ListRestaurantUseCase listRestaurantInteractor(RestaurantGateway restaurantGateway) {
        return new ListRestaurantInteractor(restaurantGateway);
    }
    @Bean
    public UpdateRestaurantUseCase updateRestaurantInteractor(RestaurantGateway restaurantGateway) {
        return new UpdateRestaurantInteractor(restaurantGateway);
    }

    @Bean
    public DeleteRestaurantUseCase deleteRestaurantInteractor(RestaurantGateway restaurantGateway, FindRestaurantByIdUseCase findRestaurantByIdUseCase) {
        return new DeleteRestaurantInteractor(restaurantGateway, findRestaurantByIdUseCase);
    }

    @Bean
    public UserTypeGateway userTypeGateway(UserTypeRepository userTypeRepository) {
        return new UserTypeRepositoryGateway(userTypeRepository);
    }

    @Bean
    public CreateUserTypeUseCase createUserTypeInteractor(UserTypeGateway userTypeGateway) {
        return new CreateUserTypeInteractor(userTypeGateway);
    }

    @Bean
    public UpdateUserTypeUseCase updateUserTypeInteractor(UserTypeGateway userTypeGateway) {
        return new UpdateUserTypeInteractor(userTypeGateway);
    }

    @Bean
    public FindUserTypeByIdUseCase findUserTypeByIdInteractor(UserTypeGateway userTypeGateway) {
        return new FindUserTypeByIdInteractor(userTypeGateway);
    }

    @Bean
    public FindUserTypeByNameUseCase findUserTypeByNameUseCase(UserTypeGateway userTypeGateway) {
        return new FindUserTypeByNameInteractor(userTypeGateway);
    }

    @Bean
    public AssignUserTypeToUserUseCase assignUserTypeToUserUseCase(UserGateway userGateway, UserTypeGateway userTypeGateway) {
        return new AssignUserTypeToUserInteractor(userGateway, userTypeGateway);
    }

    @Bean
    public DeleteUserTypeUseCase deleteUserTypeUseCase(UserTypeGateway userTypeGateway) {
        return new DeleteUserTypeInteractor(userTypeGateway);
    }

}

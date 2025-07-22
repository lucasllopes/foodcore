package com.fiap.foodcore.usecase.restaurant;

import com.fiap.foodcore.application.exception.DuplicatedDataException;
import com.fiap.foodcore.application.gateway.RestaurantGateway;
import com.fiap.foodcore.application.gateway.UserGateway;
import com.fiap.foodcore.application.usecase.input.CreateAddressInput;
import com.fiap.foodcore.application.usecase.input.CreateUserInput;
import com.fiap.foodcore.application.usecase.input.restaurant.CreateRestaurantInput;
import com.fiap.foodcore.application.usecase.interactor.restaurant.CreateRestaurantInteractor;
import com.fiap.foodcore.application.usecase.output.AddressOutput;
import com.fiap.foodcore.application.usecase.output.CreateRestaurantOutput;
import com.fiap.foodcore.application.usecase.restaurant.CreateRestaurantUseCase;
import com.fiap.foodcore.domain.Address;
import com.fiap.foodcore.domain.Restaurant;
import com.fiap.foodcore.domain.User;
import com.fiap.foodcore.domain.UserTypeDomain;
import com.fiap.foodcore.domain.exception.UserTypeNotOwnerException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateRestaurantUseCaseTest {
    private static final Long OWNER_ID_CLIENTE = 1L;
    private static final Long OWNER_ID_DONO = 2L;
    @Mock
    private RestaurantGateway restaurantGateway;
    @Mock
    private UserGateway userGateway;
    @InjectMocks
    private CreateRestaurantInteractor createRestaurantInteractor;

    private CreateRestaurantUseCase createRestaurantUseCase;

    @BeforeEach
    void setup() {
        this.createRestaurantUseCase = createRestaurantInteractor;
    }
    @Test
    public void shouldCreateRestaurantWithSucess(){
        when(userGateway.findById(OWNER_ID_DONO)).thenReturn(Optional.of(getUserDono()));
        when(restaurantGateway.save(any())).thenReturn(getRestauranteExpected());
        CreateRestaurantOutput createRestaurantOutputExpected = this.createRestaurantUseCase.execute(getCreateRestaurantInput(OWNER_ID_DONO));
        Assertions.assertEquals(1L, createRestaurantOutputExpected.id());
        assertAddress(createRestaurantOutputExpected.address());
        Assertions.assertEquals("Restaurante XPTO", createRestaurantOutputExpected.nome());
        Assertions.assertEquals("Fast Food", createRestaurantOutputExpected.cuisineType());
        Assertions.assertEquals(LocalTime.of(19,00), createRestaurantOutputExpected.openingHours());
        Assertions.assertEquals(LocalTime.of(23,59), createRestaurantOutputExpected.closingHours());
        Assertions.assertEquals(2L, createRestaurantOutputExpected.ownerId());
    }
    private void assertAddress(AddressOutput address){
        Assertions.assertEquals("Rua A", address.logradouro());
        Assertions.assertEquals("123", address.numero());
        Assertions.assertEquals("", address.complemento());
        Assertions.assertEquals("Bairro", address.bairro());
        Assertions.assertEquals("Cidade", address.cidade());
        Assertions.assertEquals("12345-678", address.cep());
        Assertions.assertEquals("SP", address.estado());
    }
    @Test
    public void shouldThrowUserTypeExceptionWhenOwnerIdIsClient() {
        when(userGateway.findById(OWNER_ID_CLIENTE)).thenReturn(Optional.of(getUserUser()));
        UserTypeNotOwnerException exception = Assertions.assertThrows(UserTypeNotOwnerException.class, () ->
                createRestaurantUseCase.execute(getCreateRestaurantInput(OWNER_ID_CLIENTE))
        );
        Assertions.assertEquals("O código de usuário informado não é do tipo Dono.", exception.getMessage());
    }
    @Test
    public void shouldThrowUserTypeExceptionWhenRestaurantExisting() {
        when(userGateway.findById(OWNER_ID_DONO)).thenReturn(Optional.of(getUserDono()));
        when(restaurantGateway.findByName(any())).thenReturn(Optional.of(getRestauranteExpected()));
        DuplicatedDataException exception = Assertions.assertThrows(DuplicatedDataException.class, () ->
                createRestaurantUseCase.execute(getCreateRestaurantInput(OWNER_ID_DONO))
        );
        Assertions.assertEquals("Já existe um restaurante cadastrado com este nome.", exception.getMessage());
    }
    private User getUserDono(){
        return User.create("123", UserTypeDomain.DONO, createUserInput());
    }
    private User getUserUser(){
        return User.create("123", UserTypeDomain.CLIENTE, createUserInput());
    }
    private Restaurant getRestauranteExpected(){
        LocalTime openingHours = LocalTime.of(19,00);
        LocalTime closingHours = LocalTime.of(23,59);
        return Restaurant.create(1L, "Restaurante XPTO", getAddressExpected(), "Fast Food", openingHours, closingHours, OWNER_ID_DONO);
    }
    private Address getAddressExpected(){
        return Address.addAddress(getCreateAddressInput());
    }
    private CreateUserInput createUserInput(){
        return new CreateUserInput("Zequinha", "zequinha@gmail.com", "zequinha", "123", UserTypeDomain.DONO, List.of(getCreateAddressInput()));

    }
    private CreateRestaurantInput getCreateRestaurantInput(Long ownerId){
        LocalTime openingHours = LocalTime.of(19,00);
        LocalTime closingHours = LocalTime.of(23,59);
        return new CreateRestaurantInput("Restaurante XPTO", getCreateAddressInput(), "Fast Food", openingHours, closingHours, ownerId);
    }
    private CreateAddressInput getCreateAddressInput(){
        return new CreateAddressInput("Rua A", "123", "", "Bairro", "Cidade", "12345-678", "SP");
    }
}
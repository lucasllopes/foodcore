package com.fiap.foodcore.usecase.restaurant;

import com.fiap.foodcore.application.gateway.RestaurantGateway;
import com.fiap.foodcore.application.usecase.interactor.restaurant.DeleteRestaurantInteractor;
import com.fiap.foodcore.application.usecase.output.AddressOutput;
import com.fiap.foodcore.application.usecase.output.CreateRestaurantOutput;
import com.fiap.foodcore.application.usecase.restaurant.DeleteRestaurantUseCase;
import com.fiap.foodcore.application.usecase.restaurant.FindRestaurantByIdUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;

@ExtendWith(MockitoExtension.class)
class DeleteRestaurantInteractorTest {
    @Mock
    private RestaurantGateway restaurantGateway;
    @Mock
    private FindRestaurantByIdUseCase findRestaurantByIdUseCase;
    @InjectMocks
    private DeleteRestaurantInteractor deleteRestaurantInteractor;
    DeleteRestaurantUseCase deleteRestaurantUseCase;
    @BeforeEach
    public void setup() {
        this.deleteRestaurantUseCase = deleteRestaurantInteractor;
    }
    @Test
    public void shouldDeleteRestaurant(){
        CreateRestaurantOutput createRestaurantOutput = getCreateRestaurantOutput();
        Mockito.when(findRestaurantByIdUseCase.execute(1L)).thenReturn(createRestaurantOutput);
        this.deleteRestaurantUseCase.execute(createRestaurantOutput.id());
    }
    private CreateRestaurantOutput getCreateRestaurantOutput(){
        LocalTime openingHours = LocalTime.of(19,00);
        LocalTime closingHours = LocalTime.of(23,59);
        return new CreateRestaurantOutput(1L, "Test Restaurant", getAddressOutput(), "Fast Food", openingHours, closingHours, 2L);
    }
    private AddressOutput getAddressOutput() {
        return new AddressOutput("Rua A", "123", "Test Neighborhood", "Test City", "Test State", "12345-678", "Test Estado");
    }
}
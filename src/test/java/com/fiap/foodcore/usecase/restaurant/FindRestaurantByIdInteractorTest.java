package com.fiap.foodcore.usecase.restaurant;

import com.fiap.foodcore.application.exception.DataNotFoundException;
import com.fiap.foodcore.application.gateway.RestaurantGateway;
import com.fiap.foodcore.application.usecase.input.CreateAddressInput;
import com.fiap.foodcore.application.usecase.interactor.restaurant.FindRestaurantByIdInteractor;
import com.fiap.foodcore.application.usecase.output.CreateRestaurantOutput;
import com.fiap.foodcore.application.usecase.restaurant.FindRestaurantByIdUseCase;
import com.fiap.foodcore.domain.Address;
import com.fiap.foodcore.domain.Restaurant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class FindRestaurantByIdInteractorTest {
    private static final Long OWNER_ID_DONO = 2L;
    @Mock
    private RestaurantGateway restaurantGateway;
    @InjectMocks
    private FindRestaurantByIdInteractor findRestaurantByIdInteractor;
    private FindRestaurantByIdUseCase findRestaurantByIdUseCase;

    private static final Long VALID_RESTAURANT_ID = 1L;
    private static final Long INVALID_RESTAURANT_ID = 999L;

    @BeforeEach
    void setUp() {
        this.findRestaurantByIdUseCase = findRestaurantByIdInteractor;
    }

    @Test
    void shouldReturnRestaurantWhenIdIsValid() {

        Restaurant restaurant = getRestaurantExpected();
        when(restaurantGateway.findById(VALID_RESTAURANT_ID)).thenReturn(Optional.of(restaurant));

        CreateRestaurantOutput restaurantOutput =  this.findRestaurantByIdUseCase.execute(VALID_RESTAURANT_ID);

        assertNotNull(restaurantOutput);
        assertEquals(restaurant.getId(), restaurantOutput.id());
        verify(restaurantGateway, times(1)).findById(VALID_RESTAURANT_ID);
    }

    @Test
    void shouldThrowDataNotFoundExceptionWhenIdIsInvalid() {
        when(restaurantGateway.findById(INVALID_RESTAURANT_ID)).thenReturn(Optional.empty());
        DataNotFoundException exception = assertThrows(DataNotFoundException.class,
                () -> findRestaurantByIdInteractor.execute(INVALID_RESTAURANT_ID));
        assertEquals("Restaurante não encontrado.", exception.getMessage());
        verify(restaurantGateway, times(1)).findById(INVALID_RESTAURANT_ID);
    }
    private Restaurant getRestaurantExpected(){
        LocalTime openingHours = LocalTime.of(19,00);
        LocalTime closingHours = LocalTime.of(23,59);
        return Restaurant.create(1L, "Restaurante XPTO", getAddressExpected(), "Fast Food", openingHours, closingHours, OWNER_ID_DONO);
    }
    private Address getAddressExpected(){
        return Address.addAddress(getCreateAddressInput());
    }
    private CreateAddressInput getCreateAddressInput(){
        return new CreateAddressInput("Rua A", "123", "", "Bairro", "Cidade", "12345-678", "SP");
    }
}
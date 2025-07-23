package com.fiap.foodcore.usecase.restaurant;

import com.fiap.foodcore.application.exception.DataNotFoundException;
import com.fiap.foodcore.application.exception.DuplicatedDataException;
import com.fiap.foodcore.application.gateway.RestaurantGateway;
import com.fiap.foodcore.application.usecase.input.AddressUpdateInput;
import com.fiap.foodcore.application.usecase.input.CreateAddressInput;
import com.fiap.foodcore.application.usecase.input.restaurant.UpdateRestaurantInput;
import com.fiap.foodcore.application.usecase.interactor.restaurant.UpdateRestaurantInteractor;
import com.fiap.foodcore.application.usecase.output.AddressOutput;
import com.fiap.foodcore.application.usecase.output.CreateRestaurantOutput;
import com.fiap.foodcore.application.usecase.restaurant.UpdateRestaurantUseCase;
import com.fiap.foodcore.domain.Address;
import com.fiap.foodcore.domain.Restaurant;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
public class UpdateRestaurantInteractorTest {

    private static final Long ID_RESTAURANT = 1L;
    private static final Long ID_RESTAURANT_INVALID = 99L;

    private static final Long OWNER_ID_DONO = 2L;
    @Mock
    private RestaurantGateway restaurantGateway;
    @InjectMocks
    private UpdateRestaurantInteractor updateRestaurantInteractor;
    private UpdateRestaurantUseCase updateRestaurantUseCase;

    @BeforeEach
    public void setUp() {
        updateRestaurantUseCase = updateRestaurantInteractor;
    }
    @Test
    public void shouldThrow(){
        when(restaurantGateway.findById(ID_RESTAURANT_INVALID))
                .thenReturn(Optional.empty());
        DataNotFoundException exception = Assertions.assertThrows(DataNotFoundException.class, () ->
                this.updateRestaurantUseCase.execute(ID_RESTAURANT_INVALID, getUpdateRestaurantInput())
        );
        Assertions.assertEquals("Restaurante não encontrado.", exception.getMessage());
    }
    @Test
    public void shouldThrow2(){
        Restaurant restaurantExpected = getRestauranteExpected();
        when(restaurantGateway.findById(ID_RESTAURANT))
                .thenReturn(Optional.of(restaurantExpected));
        when(restaurantGateway.findByName("Restaurante XPTO"))
                .thenReturn(Optional.of(restaurantExpected));
        DuplicatedDataException exception = Assertions.assertThrows(DuplicatedDataException.class, () ->
                this.updateRestaurantUseCase.execute(ID_RESTAURANT, getUpdateRestaurantInput())
        );
        Assertions.assertEquals("Já existe um restaurante cadastrado com este nome.", exception.getMessage());
    }
    @Test
    public void shouldUpdateRestaurantWithSuccess(){
        Restaurant restaurantExpected = getRestauranteExpected();
        when(restaurantGateway.findById(ID_RESTAURANT))
                .thenReturn(Optional.of(restaurantExpected));
        when(restaurantGateway.findByName("Restaurante XPTO"))
                .thenReturn(Optional.empty());
        when(restaurantGateway.save(restaurantExpected))
                .thenReturn(restaurantExpected);

        CreateRestaurantOutput createRestaurantOutputAtual = this.updateRestaurantUseCase.execute(ID_RESTAURANT, getUpdateRestaurantInput());

        assertEquals(restaurantExpected.getId(), createRestaurantOutputAtual.id());
        assertEquals(restaurantExpected.getName(), createRestaurantOutputAtual.nome());
        assertAddress(createRestaurantOutputAtual.address());
        assertEquals(restaurantExpected.getCuisineType(), createRestaurantOutputAtual.cuisineType());
        assertEquals(restaurantExpected.getOpeningHours(), createRestaurantOutputAtual.openingHours());
        assertEquals(restaurantExpected.getClosingHours(), createRestaurantOutputAtual.closingHours());
        assertEquals(restaurantExpected.getOwnerId(), createRestaurantOutputAtual.ownerId());
    }
    private void assertAddress(AddressOutput address){
        assertEquals("Rua A", address.logradouro());
        assertEquals("123", address.numero());
        assertEquals("", address.complemento());
        assertEquals("Bairro", address.bairro());
        assertEquals("Cidade", address.cidade());
        assertEquals("12345-678", address.cep());
        assertEquals("SP", address.estado());
    }
    private Restaurant getRestauranteExpected(){
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
    private UpdateRestaurantInput getUpdateRestaurantInput(){
        return new UpdateRestaurantInput("Restaurante XPTO", getAddressUpdateInput(), "Fast Food", LocalTime.of(19,00), LocalTime.of(23,59));
    }
    private AddressUpdateInput getAddressUpdateInput(){
        return new AddressUpdateInput("Rua A", "123", "", "Bairro", "Cidade", "SP", "12345-678");
    }
}

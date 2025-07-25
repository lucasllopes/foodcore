package com.fiap.foodcore.usecase.restaurant;

import com.fiap.foodcore.application.gateway.RestaurantGateway;
import com.fiap.foodcore.application.usecase.input.CreateAddressInput;
import com.fiap.foodcore.application.usecase.interactor.restaurant.ListRestaurantInteractor;
import com.fiap.foodcore.application.usecase.output.CreateRestaurantOutput;
import com.fiap.foodcore.application.usecase.restaurant.ListRestaurantUseCase;
import com.fiap.foodcore.domain.Address;
import com.fiap.foodcore.domain.Restaurant;
import com.fiap.foodcore.domain.builder.AddressBuilder;
import com.fiap.foodcore.domain.pagination.DomainPage;
import com.fiap.foodcore.domain.pagination.PageRequestDomain;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class ListRestaurantInteractorTest {

    private static final Long OWNER_ID_DONO = 2L;

    @Mock
    private RestaurantGateway restaurantGateway;

    @InjectMocks
    private ListRestaurantInteractor listRestaurantInteractor;
    private ListRestaurantUseCase listRestaurantUseCase;

    @BeforeEach
    void setUp() {
        this.listRestaurantUseCase = listRestaurantInteractor;
    }

    @Test
    void shouldReturnRestaurants() {

        PageRequestDomain pr = new PageRequestDomain(
                0,
                10,
                null
        );

        List<Restaurant> restaurants = new ArrayList<>();
        Restaurant restaurant = getRestaurantExpected();
        restaurants.add(restaurant);

        DomainPage<Restaurant> domainPage = new DomainPage<Restaurant>(
                restaurants,
                pr.page(),
                pr.size(),
                restaurants.size()
        );

        when(restaurantGateway.findAll(pr)).thenReturn(domainPage);

        DomainPage<CreateRestaurantOutput> domainPageResult = this.listRestaurantUseCase.execute(null, pr);

        assertNotNull(domainPageResult);
        assertNotNull(domainPageResult.getItems());
        assertEquals(domainPageResult.getItems().size(), domainPage.getTotalElements());
        assertEquals(domainPageResult.getPage(), domainPage.getPage());
        assertEquals(domainPageResult.getSize(), domainPage.getSize());

        verify(restaurantGateway, times(1)).findAll(pr);
    }

    @Test
    void shouldReturnRestaurantsFilteredByName() {

        PageRequestDomain pr = new PageRequestDomain(
                0,
                10,
                null
        );

        List<Restaurant> restaurants = new ArrayList<>();
        Restaurant restaurant = getRestaurantExpected();
        restaurants.add(restaurant);

        DomainPage<Restaurant> domainPage = new DomainPage<Restaurant>(
                restaurants,
                pr.page(),
                pr.size(),
                restaurants.size()
        );

        when(restaurantGateway.findAllByName("name", pr)).thenReturn(domainPage);

        DomainPage<CreateRestaurantOutput> domainPageResult = this.listRestaurantUseCase.execute("name", pr);

        assertNotNull(domainPageResult);
        assertNotNull(domainPageResult.getItems());
        assertEquals(domainPageResult.getItems().size(), domainPage.getTotalElements());
        assertEquals(domainPageResult.getPage(), domainPage.getPage());
        assertEquals(domainPageResult.getSize(), domainPage.getSize());

        verify(restaurantGateway, times(1)).findAllByName("name",pr);
    }

    private Restaurant getRestaurantExpected() {
        LocalTime openingHours = LocalTime.of(19,00);
        LocalTime closingHours = LocalTime.of(23,59);
        return Restaurant.create(1L, "Restaurante XPTO", getAddressExpected(), "Fast Food", openingHours, closingHours, OWNER_ID_DONO);
    }

    private Address getAddressExpected(){
        CreateAddressInput input = getCreateAddressInput();
        return AddressBuilder.getInstance().
                withStreet(input.logradouro())
                .withNumber(input.numero())
                .withNeighborhood(input.bairro())
                .withCity(input.cidade())
                .withState(input.estado())
                .withComplement(input.complemento())
                .withZipCode(input.cep()).build();
    }

    private CreateAddressInput getCreateAddressInput() {
        return new CreateAddressInput("Rua A", "123", "", "Bairro", "Cidade", "12345-678", "SP");
    }
}
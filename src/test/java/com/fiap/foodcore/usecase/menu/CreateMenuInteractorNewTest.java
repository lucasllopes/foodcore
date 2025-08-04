//package com.fiap.foodcore.usecase.menu;
//
//import com.fiap.foodcore.application.exception.DataNotFoundException;
//import com.fiap.foodcore.application.exception.DuplicatedDataException;
//import com.fiap.foodcore.application.gateway.RestaurantGateway;
//import com.fiap.foodcore.application.usecase.input.CreateAddressInput;
//import com.fiap.foodcore.application.usecase.input.CreateItemInput;
//import com.fiap.foodcore.application.usecase.input.CreateMenuInput;
//import com.fiap.foodcore.application.usecase.menu.CreateMenuInteractor;
//import com.fiap.foodcore.application.usecase.output.MenuCreateOutput;
//import com.fiap.foodcore.domain.Address;
//import com.fiap.foodcore.domain.Item;
//import com.fiap.foodcore.domain.Menu;
//import com.fiap.foodcore.domain.Restaurant;
//import com.fiap.foodcore.domain.builder.AddressBuilder;
//import com.fiap.foodcore.infrastructure.gateways.MenuRepositoryGateway;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.math.BigDecimal;
//import java.time.LocalTime;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyLong;
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//class CreateMenuInteractorNewTest {
//    private static final Long OWNER_ID_CLIENTE = 1L;
//    private static final Long OWNER_ID_DONO = 2L;
//    @Mock
//    private MenuRepositoryGateway menuRepositoryGateway;
//
//    @Mock
//    private RestaurantGateway restaurantGateway;
//
//    private CreateMenuInteractor createMenuInteractor;
//
//    @BeforeEach
//    void setUp() {
//        createMenuInteractor = new CreateMenuInteractor(menuRepositoryGateway, restaurantGateway);
//    }
//
//    @Test
//    @DisplayName("Deve criar um menu com sucesso quando dados válidos são fornecidos")
//    void executeSuccess() {
//        // Arrange
//        Long restaurantId = 1L;
//        String menuName = "Menu de Teste";
//        String menuDescription = "Descrição do Menu de Teste";
//
//        // Criando item com ID (incluindo ID conforme solicitado)
//        CreateItemInput itemInput = new CreateItemInput(
//                1L, // ID adicionado conforme solicitado
//                "Item de Teste",
//                "Descrição do Item",
//                new BigDecimal("10.00"),
//                "Disponível",
//                "path/to/photo",
//                restaurantId
//        );
//
//        CreateMenuInput input = new CreateMenuInput(menuName, menuDescription, restaurantId, List.of(itemInput));
//
//
//
//        when(restaurantGateway.findById(restaurantId)).thenReturn(Optional.of(getRestauranteExpected()));
//        when(menuRepositoryGateway.findByNameAndRestaurantId(menuName, restaurantId)).thenReturn(Optional.empty());
//
//        Menu savedMenu = new Menu.Builder()
//                .id(1L)
//                .name(menuName)
//                .description(menuDescription)
//                .restaurantId(getRestauranteExpected())
//                .itemsList(List.of(
//                        new Item.Builder()
//                                .id(1L)
//                                .name("Item de Teste")
//                                .description("Descrição do Item")
//                                .price(new BigDecimal("10.00"))
//                                .availability("Disponível")
//                                .photo("path/to/photo")
//                                .ownerId(restaurant)
//                                .build()
//                ))
//                .build();
//
//        when(menuRepositoryGateway.save(any(Menu.class))).thenReturn(savedMenu);
//
//        // Act
//        MenuCreateOutput result = createMenuInteractor.execute(input);
//
//        // Assert
//        assertNotNull(result);
//        assertEquals(1L, result.id());
//        assertEquals(menuName, result.name());
//        assertEquals(menuDescription, result.description());
//        assertEquals(restaurantId, result.restaurantId());
//        assertEquals(1, result.items().size());
//
//        verify(restaurantGateway).findById(restaurantId);
//        verify(menuRepositoryGateway).findByNameAndRestaurantId(menuName, restaurantId);
//        verify(menuRepositoryGateway).save(any(Menu.class));
//    }
//
//    @Test
//    @DisplayName("Deve criar um menu sem itens quando lista de itens for nula")
//    void executeSucessWithNullItems() {
//        // Arrange
//        Long restaurantId = 1L;
//        String menuName = "Menu Sem Itens";
//        String menuDescription = "Descrição do Menu Sem Itens";
//
//        CreateMenuInput input = new CreateMenuInput(menuName, menuDescription, restaurantId, null);
//
//        Restaurant restaurant = new Restaurant.Builder()
//                .id(restaurantId)
//                .name("Restaurante Teste")
//                .build();
//
//        when(restaurantGateway.findById(restaurantId)).thenReturn(Optional.of(restaurant));
//        when(menuRepositoryGateway.findByNameAndRestaurantId(menuName, restaurantId)).thenReturn(Optional.empty());
//
//        Menu savedMenu = new Menu.Builder()
//                .id(1L)
//                .name(menuName)
//                .description(menuDescription)
//                .restaurantId(restaurant)
//                .build();
//
//        when(menuRepositoryGateway.save(any(Menu.class))).thenReturn(savedMenu);
//
//        // Act
//        MenuCreateOutput result = createMenuInteractor.execute(input);
//
//        // Assert
//        assertNotNull(result);
//        assertEquals(1L, result.id());
//        assertEquals(menuName, result.name());
//        assertEquals(menuDescription, result.description());
//        assertTrue(result.items().isEmpty());
//
//        verify(restaurantGateway).findById(restaurantId);
//        verify(menuRepositoryGateway).save(any(Menu.class));
//    }
//
//    @Test
//    @DisplayName("Deve lançar DataNotFoundException quando restaurante não existe")
//    void executeWithNonExistingRestaurant() {
//        // Arrange
//        Long restaurantId = 999L;
//        String menuName = "Menu de Teste";
//        String menuDescription = "Descrição do Menu de Teste";
//
//        CreateMenuInput input = new CreateMenuInput(menuName, menuDescription, restaurantId, List.of());
//
//        when(restaurantGateway.findById(restaurantId)).thenReturn(Optional.empty());
//
//        // Act & Assert
//        DataNotFoundException exception = assertThrows(
//                DataNotFoundException.class,
//                () -> createMenuInteractor.execute(input)
//        );
//
//        assertEquals("Restaurante não encontrado com ID: " + restaurantId, exception.getMessage());
//        verify(restaurantGateway).findById(restaurantId);
//        verify(menuRepositoryGateway, never()).save(any());
//    }
//
//    @Test
//    @DisplayName("Deve lançar DuplicatedDataException quando menu com mesmo nome já existe para o restaurante")
//    void executeWithDuplicatedName() {
//        // Arrange
//        Long restaurantId = 1L;
//        String menuName = "Menu Duplicado";
//        String menuDescription = "Descrição do Menu Duplicado";
//
//        CreateMenuInput input = new CreateMenuInput(menuName, menuDescription, restaurantId, List.of());
//
//        Restaurant restaurant = new Restaurant.Builder()
//                .id(restaurantId)
//                .name("Restaurante Teste")
//                .build();
//
//        Menu existingMenu = new Menu.Builder()
//                .id(1L)
//                .name(menuName)
//                .description("Outro menu com mesmo nome")
//                .restaurantId(restaurant)
//                .build();
//
//        when(restaurantGateway.findById(restaurantId)).thenReturn(Optional.of(restaurant));
//        when(menuRepositoryGateway.findByNameAndRestaurantId(menuName, restaurantId))
//                .thenReturn(Optional.of(existingMenu));
//
//        // Act & Assert
//        DuplicatedDataException exception = assertThrows(
//                DuplicatedDataException.class,
//                () -> createMenuInteractor.execute(input)
//        );
//
//        assertEquals("Cardápio com o nome '" + menuName + "' ja existe para o restaurante com ID: " + restaurantId,
//                exception.getMessage());
//
//        verify(restaurantGateway).findById(restaurantId);
//        verify(menuRepositoryGateway).findByNameAndRestaurantId(menuName, restaurantId);
//        verify(menuRepositoryGateway, never()).save(any());
//    }
//
//    @Test
//    @DisplayName("Deve criar um menu com múltiplos itens quando dados válidos são fornecidos")
//    void executeWithMultipleItems() {
//        // Arrange
//        Long restaurantId = 1L;
//        String menuName = "Menu com Múltiplos Itens";
//        String menuDescription = "Descrição do Menu com Múltiplos Itens";
//
//        // Criando itens com ID (incluindo ID conforme solicitado)
//        CreateItemInput item1 = new CreateItemInput(
//                1L, // ID adicionado conforme solicitado
//                "Item 1",
//                "Descrição do Item 1",
//                new BigDecimal("10.00"),
//                "Disponível",
//                "path/to/photo1",
//                restaurantId
//        );
//
//        CreateItemInput item2 = new CreateItemInput(
//                2L, // ID adicionado conforme solicitado
//                "Item 2",
//                "Descrição do Item 2",
//                new BigDecimal("20.00"),
//                "Disponível",
//                "path/to/photo2",
//                restaurantId
//        );
//
//        CreateMenuInput input = new CreateMenuInput(menuName, menuDescription, restaurantId, List.of(item1, item2));
//
//        Restaurant restaurant = new Restaurant.Builder()
//                .id(restaurantId)
//                .name("Restaurante Teste")
//                .build();
//
//        when(restaurantGateway.findById(restaurantId)).thenReturn(Optional.of(restaurant));
//        when(menuRepositoryGateway.findByNameAndRestaurantId(menuName, restaurantId)).thenReturn(Optional.empty());
//
//        Menu savedMenu = new Menu.Builder()
//                .id(1L)
//                .name(menuName)
//                .description(menuDescription)
//                .restaurantId(restaurant)
//                .itemsList(List.of(
//                        new Item.Builder()
//                                .id(1L)
//                                .name("Item 1")
//                                .description("Descrição do Item 1")
//                                .price(new BigDecimal("10.00"))
//                                .availability("Disponível")
//                                .photo("path/to/photo1")
//                                .ownerId(restaurant)
//                                .build(),
//                        new Item.Builder()
//                                .id(2L)
//                                .name("Item 2")
//                                .description("Descrição do Item 2")
//                                .price(new BigDecimal("20.00"))
//                                .availability("Disponível")
//                                .photo("path/to/photo2")
//                                .ownerId(restaurant)
//                                .build()
//                ))
//                .build();
//
//        when(menuRepositoryGateway.save(any(Menu.class))).thenReturn(savedMenu);
//
//        // Act
//        MenuCreateOutput result = createMenuInteractor.execute(input);
//
//        // Assert
//        assertNotNull(result);
//        assertEquals(1L, result.id());
//        assertEquals(menuName, result.name());
//        assertEquals(menuDescription, result.description());
//        assertEquals(restaurantId, result.restaurantId());
//        assertEquals(2, result.items().size());
//        assertEquals("Item 1", result.items().get(0).name());
//        assertEquals("Item 2", result.items().get(1).name());
//
//        verify(restaurantGateway).findById(restaurantId);
//        verify(menuRepositoryGateway).findByNameAndRestaurantId(menuName, restaurantId);
//        verify(menuRepositoryGateway).save(any(Menu.class));
//    }
//
//    private Restaurant getRestauranteExpected(){
//        LocalTime openingHours = LocalTime.of(19,00);
//        LocalTime closingHours = LocalTime.of(23,59);
//        return Restaurant.builder()
//                .id(1L)
//                .name("Restaurante XPTO")
//                .address(getAddressExpected())
//                .cuisineType("Fast Food")
//                .openingHours(openingHours)
//                .closingHours(closingHours)
//                .ownerId(OWNER_ID_DONO)
//                .build();
//    }
//    private Address getAddressExpected(){
//        CreateAddressInput input = getCreateAddressInput();
//        return AddressBuilder.getInstance().
//                withStreet(input.logradouro())
//                .withNumber(input.numero())
//                .withNeighborhood(input.bairro())
//                .withCity(input.cidade())
//                .withState(input.estado())
//                .withComplement(input.complemento())
//                .withZipCode(input.cep()).build();
//    }
//}

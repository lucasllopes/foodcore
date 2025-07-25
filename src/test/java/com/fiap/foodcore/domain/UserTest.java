package com.fiap.foodcore.domain;

import com.fiap.foodcore.application.usecase.input.AddressUpdateInput;
import com.fiap.foodcore.application.usecase.input.CreateAddressInput;
import com.fiap.foodcore.application.usecase.input.CreateUserInput;
import com.fiap.foodcore.application.usecase.input.UpdateUserInput;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
public class UserTest {

    @Test
    void deveCriarUserComSucesso() {

        CreateAddressInput addressInput = new CreateAddressInput("Rua A", "123", "", "Bairro", "Cidade", "12345-678", "SP");

        CreateUserInput input = new CreateUserInput("User", "user@email.com", "loginuser", "123AAA666", UserTypeDomain.CLIENTE, List.of(addressInput));
        String userPassEncoded = "userpassencoded";

        User user = User.create(userPassEncoded, UserTypeDomain.CLIENTE, input);

        assertEquals("User", user.getNome());
        assertEquals("user@email.com", user.getEmail());
        assertEquals("loginuser", user.getLogin());
        assertEquals(userPassEncoded, user.getSenha());
        assertEquals(UserTypeDomain.CLIENTE, user.getTipo());
        assertEquals(1, user.getAddress().size());
        assertNotNull(user.getDataUltimaAlteracao());
    }

    @Test
    void deveAtualizarSenhaEData() {
        User user = User.rebuildUser(1L, "User", "user@email.com", "loginuser", "oldpassword", UserTypeDomain.CLIENTE, List.of(), LocalDateTime.now());
        String newPassword = "newpassword";

        user.changePassword(newPassword);

        assertEquals(newPassword, user.getSenha());
        assertNotNull(user.getDataUltimaAlteracao());
    }

    @Test
    void deveAtualizarDados() {
        AddressUpdateInput newAddress = new AddressUpdateInput("Rua nova", "123", "COMPLEMENTO A", "BAIRRO NOVO", "NOVA CIDADE", "99999-999", "RJ");
        UpdateUserInput input = new UpdateUserInput("new user", "new@email.com", List.of(newAddress));

        User user = User.rebuildUser(1L, "user", "user@email.com", "user", "password", UserTypeDomain.CLIENTE, List.of(), LocalDateTime.now());

        user.updateInformation(input);

        assertEquals("new user", user.getNome());
        assertEquals("new@email.com", user.getEmail());
        assertEquals(1, user.getAddress().size());
        assertEquals("Rua nova", user.getAddress().getFirst().getLogradouro());
    }

    @Test
    void deveRetornarUsuario() {
        LocalDateTime data = LocalDateTime.now();

        User user = User.rebuildUser(1L, "user", "user@email.com", "user", "123", UserTypeDomain.DONO, Collections.emptyList(), data);

        assertEquals(1L, user.getId());
        assertEquals("user", user.getNome());
        assertEquals("user@email.com", user.getEmail());
        assertEquals("user", user.getLogin());
        assertEquals("123", user.getSenha());
        assertEquals(UserTypeDomain.DONO, user.getTipo());
        assertEquals(data, user.getDataUltimaAlteracao());
    }

    @Test
    void deveRetornarUsuarioParaAutenticacao() {
        User user = User.rebuildForAuthentication(1L, "login", "password123", UserTypeDomain.CLIENTE);

        assertEquals(1L, user.getId());
        assertEquals("login", user.getLogin());
        assertEquals("password123", user.getSenha());
        assertEquals(UserTypeDomain.CLIENTE, user.getTipo());
    }

    @Test
    void shouldAssignUserType() {
        // Arrange
        User user = User.rebuildUser(
                1L,
                "User",
                "user@email.com",
                "login",
                "123",
                null,
                List.of(),
                LocalDateTime.now().minusDays(1)
        );

        assertNull(user.getTipo());

        UserSubtype userType = UserSubtype.reconstruct(10L, "OWNER", LocalDateTime.now().minusDays(10));

        // Act
        user.assignUserType(userType);

        // Assert
        assertEquals(userType, user.getUserSubtype());
        assertNotNull(user.getDataUltimaAlteracao());
        assertTrue(user.getDataUltimaAlteracao().isAfter(LocalDateTime.now().minusSeconds(5)));
    }
}

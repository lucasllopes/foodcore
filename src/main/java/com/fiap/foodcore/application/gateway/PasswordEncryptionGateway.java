package com.fiap.foodcore.application.gateway;

public interface PasswordEncryptionGateway {

    String encode(String password);
    boolean matches(String password, String encryptedPassword);
}

package com.fiap.foodcore.infrastructure.gateways;

import com.fiap.foodcore.application.gateway.PasswordEncryptionGateway;
import org.springframework.security.crypto.password.PasswordEncoder;

public class BCryptPasswordEncryptionGateway implements PasswordEncryptionGateway {

    private final PasswordEncoder passwordEncoder;

    public BCryptPasswordEncryptionGateway(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String encode(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    @Override
    public boolean matches(String rawPassword, String encryptedPassword) {
        return passwordEncoder.matches(rawPassword, encryptedPassword);
    }
}

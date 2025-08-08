package com.addisonglobal.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.CompletableFuture;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.addisonglobal.model.Credentials;
import com.addisonglobal.model.UserToken;

class TokenServiceImplTest {

    private TokenServiceImpl tokenService;

    @BeforeEach
    void setUp() {
        tokenService = new TokenServiceImpl();
    }

    @Test
    void shouldReturnUserTokenWhenCredentialsValidAndUserDoesNotStartWithA() throws ExecutionException, InterruptedException {
        // Given
        Credentials credentials = new Credentials("house", "HOUSE");

        // When
        CompletableFuture<UserToken> future = tokenService.requestToken(credentials);
        UserToken token = future.get(); // Bloqueia até concluir (ok em testes)

        // Then
        assertNotNull(token);
        assertNotNull(token.getToken());
        assertTrue(token.getToken().startsWith("house_"));
        assertTrue(token.getToken().endsWith("Z"));
        assertTrue(token.getToken().contains("_"));
    }

    @Test
    void shouldFailWhenPasswordDoesNotMatchUsernameUpperCase() {
        // Given
        Credentials credentials = new Credentials("house", "House");

        // When & Then
        ExecutionException exception = assertThrows(ExecutionException.class, () -> {
            tokenService.requestToken(credentials).get();
        });

        assertTrue(exception.getCause().getMessage().contains("Invalid credentials"));
    }

    @Test
    void shouldFailWhenUserIdStartsWithA() {
        // Given
        Credentials credentials = new Credentials("Alice", "ALICE");

        // When & Then
        ExecutionException exception = assertThrows(ExecutionException.class, () -> {
            tokenService.requestToken(credentials).get();
        });

        assertTrue(exception.getCause().getMessage().contains("cannot start with 'A'"));
    }

    @Test
    void shouldHandleMultipleConcurrentRequests() throws Exception {
        // Given
        Credentials creds1 = new Credentials("house", "HOUSE");
        Credentials creds2 = new Credentials("car", "CAR");
        Credentials creds3 = new Credentials("bike", "BIKE");

        // When: Executa em paralelo
        CompletableFuture<UserToken> f1 = tokenService.requestToken(creds1);
        CompletableFuture<UserToken> f2 = tokenService.requestToken(creds2);
        CompletableFuture<UserToken> f3 = tokenService.requestToken(creds3);

        // Then: Todos devem completar com sucesso
        UserToken t1 = f1.get();
        UserToken t2 = f2.get();
        UserToken t3 = f3.get();

        assertNotNull(t1.getToken());
        assertNotNull(t2.getToken());
        assertNotNull(t3.getToken());
        assertTrue(t1.getToken().startsWith("house_"));
        assertTrue(t2.getToken().startsWith("car_"));
        assertTrue(t3.getToken().startsWith("bike_"));
    }
}
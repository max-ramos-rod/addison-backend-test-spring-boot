package com.addisonglobal.service;

import java.util.concurrent.CompletableFuture;

import com.addisonglobal.model.Credentials;
import com.addisonglobal.model.UserToken;
import org.springframework.stereotype.Service;
@Service
public class TokenServiceImpl {

    private final AuthService authService = new AuthService();
    private final TokenService tokenService = new TokenService();

    public CompletableFuture<UserToken> requestToken(Credentials credentials) {
        return authService.authenticate(credentials)
            .thenCompose(tokenService::issueToken);
    }
}
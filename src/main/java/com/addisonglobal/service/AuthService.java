package com.addisonglobal.service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;
import com.addisonglobal.model.Credentials;
import com.addisonglobal.model.User;

public class AuthService {

    public CompletableFuture<User> authenticate(Credentials credentials) {
        return CompletableFuture.supplyAsync(() -> {
            randomDelay();

            if (credentials.getPassword().equals(credentials.getUsername().toUpperCase())) {
                return new User(credentials.getUsername());
            } else {
                throw new IllegalArgumentException("Invalid credentials");
            }
        });
    }

    private void randomDelay() {
        long delay = ThreadLocalRandom.current().nextLong(5000);
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
    }
}

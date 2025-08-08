package com.addisonglobal.service;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;
import com.addisonglobal.model.User;
import com.addisonglobal.model.UserToken;

public class TokenService {

    private static final DateTimeFormatter FORMATTER =
        DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");

    public CompletableFuture<UserToken> issueToken(User user) {
        return CompletableFuture.supplyAsync(() -> {
            randomDelay();

            if (user.getUserId().startsWith("A")) {
                throw new IllegalArgumentException("User ID cannot start with 'A'");
            }

            String timestamp = ZonedDateTime.now().format(FORMATTER);
            String token = user.getUserId() + "_" + timestamp;
            return new UserToken(token);
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
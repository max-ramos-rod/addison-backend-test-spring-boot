package com.addisonglobal.controller;

import java.util.concurrent.CompletableFuture;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.addisonglobal.model.Credentials;
import com.addisonglobal.service.TokenServiceImpl;

@RestController
public class TokenController {

    private final TokenServiceImpl tokenService;

    // ✅ Injeção de dependência via construtor (melhor prática Spring)
    public TokenController(TokenServiceImpl tokenService) {
        this.tokenService = tokenService;
    }

    @PostMapping("/token")
    public CompletableFuture<ResponseEntity<Object>> requestToken(@RequestBody Credentials credentials) {
        return tokenService.requestToken(credentials)
            .thenApply(token -> {
                // ✅ Sucesso: retorna ResponseEntity com UserToken como Object
                return ResponseEntity.ok().body((Object) token);
            })
            .exceptionally(ex -> {
                // ✅ Falha: retorna ResponseEntity com mensagem de erro como String (Object)
                return ResponseEntity.badRequest().body((Object) ex.getMessage());
            });
    }
}
package com.addisonglobal.model;

public class Credentials {
    private String username;
    private String password;
    // ✅ Construtor necessário para os testes
    public Credentials() {
        // Construtor padrão (necessário para frameworks como Spring)
    }

    public Credentials(String username, String password) {
        this.username = username;
        this.password = password;
    }
    // Getters e Setters
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
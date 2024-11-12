package com.letterfood.model;

public class Usuario {
    private String id;
    private String nome;
    private String email;
    private String senhaHash;

    // Getter para id
    public String getId() {
        return id;
    }

    // Setter para id
    public void setId(String id) {
        this.id = id;
    }

    // Getter para nome
    public String getNome() {
        return nome;
    }

    // Setter para nome
    public void setNome(String nome) {
        this.nome = nome;
    }

    // Getter para email
    public String getEmail() {
        return email;
    }

    // Setter para email
    public void setEmail(String email) {
        this.email = email;
    }

    // Getter para senhaHash
    public String getSenhaHash() {
        return senhaHash;
    }

    // Setter para senhaHash
    public void setSenhaHash(String senhaHash) {
        this.senhaHash = senhaHash;
    }
}

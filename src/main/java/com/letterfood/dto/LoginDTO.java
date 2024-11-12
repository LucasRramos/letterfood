package com.letterfood.dto;

public class LoginDTO {
    private String email;
    private String senha;

    // Construtor padrão
    public LoginDTO() {}

    // Construtor com parâmetros
    public LoginDTO(String email, String senha) {
        this.email = email;
        this.senha = senha;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("O campo email é obrigatório.");
        }
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        if (senha == null || senha.trim().isEmpty()) {
            throw new IllegalArgumentException("O campo senha é obrigatório.");
        }
        this.senha = senha;
    }

    // Validação simples de formato de email
    public boolean isEmailValido() {
        return email != null && email.contains("@") && email.contains(".");
    }

    @Override
    public String toString() {
        return "LoginDTO{" +
                "email='" + email + '\'' +
                ", senha='****'" +  // Senha mascarada para maior segurança
                '}';
    }
}

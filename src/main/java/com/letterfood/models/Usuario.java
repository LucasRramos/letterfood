package com.letterfood.models;

public class Usuario {
    private String id;
    private String nome;
    private String email;
    private String senha;
    private String descricao;  // Nova descrição do usuário
    private String imagemId; 

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

    // Getter para senha
    public String getSenha() {
        return senha;
    }

    // Setter para senha
    public void setSenha(String senha) {
        this.senha = senha;
    }

    // Getters e Setters para os novos campos
    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getImagemId() {
        return imagemId;
    }

    public void setImagemId(String imagemId) {
        this.imagemId = imagemId;
    }
}

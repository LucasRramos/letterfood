package com.letterfood.models;

import java.util.List;

public class Restaurante {
    private String id;
    private String nome;
    private String endereco;
    private List<Avaliacao> avaliacoes;
    private String descricao;
    private String imagemId;  // Referência à imagem no GridFS

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

    // Getter para endereco
    public String getEndereco() {
        return endereco;
    }

    // Setter para endereco
    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    // Getter para avaliacoes
    public List<Avaliacao> getAvaliacoes() {
        return avaliacoes;
    }

    // Setter para avaliacoes
    public void setAvaliacoes(List<Avaliacao> avaliacoes) {
        this.avaliacoes = avaliacoes;
    }

    // Getter para imagemId
    public String getImagemId() {
        return imagemId;
    }

    // Setter para imagemId
    public void setImagemId(String imagemId) {
        this.imagemId = imagemId;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}

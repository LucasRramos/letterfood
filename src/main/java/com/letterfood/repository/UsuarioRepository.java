package com.letterfood.repository;

import com.letterfood.config.MongoConfig;
import com.letterfood.models.Usuario;
import com.mongodb.client.MongoCollection;

import java.util.Optional;
import static com.mongodb.client.model.Filters.eq;  // Importar o eq para filtragem no MongoDB

public class UsuarioRepository {

    private final MongoCollection<Usuario> usuarioCollection;

    public UsuarioRepository() {
        this.usuarioCollection = MongoConfig.getInstance().getDatabase().getCollection("usuarios", Usuario.class);
    }

    // Método para buscar usuário pelo campo "email"
    public Optional<Usuario> findByEmail(String email) {
        Usuario usuario = usuarioCollection.find(eq("email", email)).first();
        return Optional.ofNullable(usuario);
    }

    // Método para salvar usuário (inserir ou atualizar)
    public void save(Usuario usuario) {
        usuarioCollection.insertOne(usuario);  // Insere o usuário
        // Se precisar atualizar, pode usar updateOne em vez de insertOne
    }

    public MongoCollection<Usuario> getCollection() {
        return usuarioCollection;
    }
}

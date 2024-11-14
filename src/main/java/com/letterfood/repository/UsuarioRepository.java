package com.letterfood.repository;

import com.letterfood.config.MongoConfig;
import com.letterfood.models.Usuario;
import com.mongodb.client.MongoCollection;
import org.bson.conversions.Bson;

import java.util.Optional;

import static com.mongodb.client.model.Filters.eq;

public class UsuarioRepository {

    private final MongoCollection<Usuario> usuarioCollection;

    public UsuarioRepository() {
        // Instancia a coleção "usuarios" a partir da configuração do MongoDB
        this.usuarioCollection = MongoConfig.getInstance().getDatabase().getCollection("usuarios", Usuario.class);
    }

    // Método para buscar usuário pelo campo "email"
    public Optional<Usuario> findByEmail(String email) {
        Usuario usuario = usuarioCollection.find(eq("email", email)).first();
        return Optional.ofNullable(usuario);
    }

    // Getter para acessar a coleção diretamente, caso seja necessário
    public MongoCollection<Usuario> getCollection() {
        return usuarioCollection;
    }
}

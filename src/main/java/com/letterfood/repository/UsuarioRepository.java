package com.letterfood.repository;

import com.letterfood.config.MongoConfig;
import com.letterfood.model.Usuario;

import java.util.Optional;

public class UsuarioRepository extends BaseRepository<Usuario> {
    public UsuarioRepository(MongoConfig mongoConfig) {
        super(mongoConfig, "usuarios", Usuario.class);
    }

    // Busca específica pelo email do usuário
    public Optional<Usuario> findByEmail(String email) {
        return findByField("email", email);
    }

    // Getter para collection (caso precise de uma coleção específica)
    public MongoCollection<Usuario> getCollection() {
        return mongoConfig.getDatabase().getCollection("usuarios", Usuario.class);
    }

}

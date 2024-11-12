package com.letterfood.repository;

import com.letterfood.model.Usuario;

import java.util.Optional;

public class UsuarioRepository {

    public Optional<Usuario> findByEmail(String email) {
        // Código para encontrar usuário no MongoDB
        return Optional.empty();
    }

    public void save(Usuario usuario) {
        // Código para salvar usuário no MongoDB
    }
}

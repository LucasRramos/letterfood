package com.letterfood.service;

import com.letterfood.dto.LoginDTO;
import com.letterfood.model.Usuario;
import com.letterfood.repository.UsuarioRepository;

public class UsuarioService {
    private UsuarioRepository usuarioRepository = new UsuarioRepository();

    public void registrarUsuario(Usuario usuario) {
        String senhaHash = hashPassword(usuario.getSenhaHash());
        usuario.setSenhaHash(senhaHash);
        usuarioRepository.save(usuario);
    }

    public String autenticarUsuario(LoginDTO loginDTO) {
        Usuario usuario = usuarioRepository.findByEmail(loginDTO.getEmail()).orElse(null);
        if (usuario != null && validatePassword(loginDTO.getSenha(), usuario.getSenhaHash())) {
            return generateJwtToken(usuario);
        }
        return null;
    }

    private String hashPassword(String senha) {
        return senha; // Implementação do hash
    }

    private boolean validatePassword(String senha, String senhaHash) {
        return senha.equals(senhaHash); // Validação simplificada
    }

    private String generateJwtToken(Usuario usuario) {
        return "token"; // Geração simplificada do token
    }
}

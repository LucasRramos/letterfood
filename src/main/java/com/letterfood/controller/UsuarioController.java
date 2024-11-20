package com.letterfood.controller;

import com.letterfood.dto.LoginDTO;
import com.letterfood.models.Usuario;
import com.letterfood.service.UsuarioService;

import java.util.logging.Logger;

public class UsuarioController {
    private final UsuarioService usuarioService;
    private static final Logger logger = Logger.getLogger(UsuarioController.class.getName());

    // Construtor com injeção de dependência
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // Método para registrar usuário
    public String registrarUsuario(Usuario usuario, String descricao, String imagemId) {
        if (usuario == null || usuario.getEmail() == null || usuario.getSenha() == null) {
            throw new IllegalArgumentException("Usuário, email e senha não podem ser nulos.");
        }

        try {
            // Adicionando descrição e imagem ao usuário
            usuario.setDescricao(descricao);
            usuario.setImagemId(imagemId);

            usuarioService.registrarUsuario(usuario, descricao, imagemId);
            logger.info("Usuário registrado com sucesso: " + usuario.getEmail());
            return "Usuário registrado com sucesso.";
        } catch (RuntimeException e) {
            logger.warning("Erro ao registrar usuário: " + e.getMessage());
            return "Erro ao registrar usuário: " + e.getMessage();
        }
    }

    // Método para autenticar usuário
    public String autenticarUsuario(LoginDTO loginDTO) {
        if (loginDTO == null || loginDTO.getEmail() == null || loginDTO.getSenha() == null) {
            throw new IllegalArgumentException("Email e senha são obrigatórios para autenticação.");
        }
        
        try {
            String token = usuarioService.autenticarUsuario(loginDTO);
            if (token != null) {
                logger.info("Usuário autenticado com sucesso: " + loginDTO.getEmail());
                return "Autenticação bem-sucedida. Token: " + token;
            } else {
                logger.info("Falha na autenticação: Credenciais inválidas para " + loginDTO.getEmail());
                return "Falha na autenticação: Credenciais inválidas.";
            }
        } catch (RuntimeException e) {
            logger.warning("Erro ao autenticar usuário: " + e.getMessage());
            return "Erro ao autenticar usuário: " + e.getMessage();
        }
    }

}

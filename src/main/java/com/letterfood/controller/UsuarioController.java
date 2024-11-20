package com.letterfood.controller;

import com.google.gson.Gson; // Importar Gson
import com.letterfood.dto.LoginDTO;
import com.letterfood.models.Usuario;
import com.letterfood.service.UsuarioService;

import static spark.Spark.*;

import java.util.logging.Logger;

public class UsuarioController {
    private final UsuarioService usuarioService;
    private static final Logger logger = Logger.getLogger(UsuarioController.class.getName());

    // Construtor com injeção de dependência
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;

        // Definindo os endpoints
        definirEndpoints();
    }

    private void definirEndpoints() {
        Gson gson = new Gson(); // Instanciar Gson

        // Registrar um usuário
        post("/usuarios", (req, res) -> {
            Usuario usuario = gson.fromJson(req.body(), Usuario.class); // Converter JSON para Usuario
            return registrarUsuario(usuario);
        });

        // Autenticar um usuário
        post("/usuarios/autenticar", (req, res) -> {
            LoginDTO loginDTO = gson.fromJson(req.body(), LoginDTO.class); // Converter JSON para LoginDTO
            return autenticarUsuario(loginDTO);
        });

        // Buscar usuário por email
        get("/usuarios/email/:email", (req, res) -> {
            return buscarUsuarioPorEmail(req.params(":email"));
        });
    }

    // Método para registrar um usuário
    public String registrarUsuario(Usuario usuario) {
        if (usuario == null || usuario.getEmail() == null || usuario.getSenhaHash() == null) {
            throw new IllegalArgumentException("Usuário, email e senha não podem ser nulos.");
        }

        try {
            usuarioService.registrarUsuario(usuario);
            logger.info("Usuário registrado com sucesso: " + usuario.getEmail());
            return "Usuário registrado com sucesso.";
        } catch (RuntimeException e) {
            logger.warning("Erro ao registrar usuário: " + e.getMessage());
            return "Erro ao registrar usuário: " + e.getMessage();
        }
    }

    // Método para autenticar um usuário
    public String autenticarUsuario(LoginDTO loginDTO) {
        if (loginDTO == null || loginDTO.getEmail() == null || loginDTO.getSenha() == null) {
            throw new IllegalArgumentException("Email e senha são obrigatórios para autenticação.");
        }

        try {
            String token = usuarioService.autenticarUsuario(loginDTO);
            logger.info("Usuário autenticado com sucesso: " + loginDTO.getEmail());
            return "Autenticação bem-sucedida. Token: " + token;
        } catch (RuntimeException e) {
            logger.warning("Erro ao autenticar usuário: " + e.getMessage());
            return "Erro ao autenticar usuário: " + e.getMessage();
        }
    }

    // Método para buscar usuário por email
    public Usuario buscarUsuarioPorEmail(String email) {
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Email não pode ser nulo ou vazio.");
        }

        try {
            Usuario usuario = usuarioService.buscarUsuarioPorEmail(email);
            logger.info("Usuário encontrado com sucesso: " + email);
            return usuario;
        } catch (RuntimeException e) {
            logger.warning("Erro ao buscar usuário: " + e.getMessage());
            throw new RuntimeException("Erro ao buscar usuário: " + e.getMessage());
        }
    }
}
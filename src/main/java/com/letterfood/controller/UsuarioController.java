package com.letterfood.controller;

import com.letterfood.dto.LoginDTO;
import com.letterfood.models.Usuario;
import com.letterfood.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@RestController // Indica que esta classe é um controlador REST
@RequestMapping("/api/usuarios") // Prefixo para todos os endpoints deste controlador
public class UsuarioController {

    private final UsuarioService usuarioService;
    private static final Logger logger = Logger.getLogger(UsuarioController.class.getName());

    // Injeção de dependência via construtor
    @Autowired
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // Endpoint para registrar um novo usuário
    @PostMapping("/registrar") // Mapeia requisição POST para /api/usuarios/registrar
    public ResponseEntity<String> registrarUsuario(@RequestBody Usuario usuario) {
        if (usuario == null || usuario.getEmail() == null || usuario.getSenhaHash() == null) {
            // Retorna 400 Bad Request se faltar dados obrigatórios
            throw new IllegalArgumentException("Usuário, email e senha não podem ser nulos.");
        }

        try {
            usuarioService.registrarUsuario(usuario); // Registra o usuário no serviço
            logger.info("Usuário registrado com sucesso: " + usuario.getEmail());
            return ResponseEntity.status(HttpStatus.CREATED).body("Usuário registrado com sucesso.");
        } catch (RuntimeException e) {
            logger.warning("Erro ao registrar usuário: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao registrar usuário: " + e.getMessage());
        }
    }

    // Endpoint para autenticar um usuário
    @PostMapping("/autenticar") // Mapeia requisição POST para /api/usuarios/autenticar
    public ResponseEntity<String> autenticarUsuario(@RequestBody LoginDTO loginDTO) {
        if (loginDTO == null || loginDTO.getEmail() == null || loginDTO.getSenha() == null) {
            // Retorna 400 Bad Request se faltarem dados obrigatórios
            throw new IllegalArgumentException("Email e senha são obrigatórios para autenticação.");
        }

        try {
            String token = usuarioService.autenticarUsuario(loginDTO); // Tenta autenticar
            if (token != null) {
                logger.info("Usuário autenticado com sucesso: " + loginDTO.getEmail());
                return ResponseEntity.ok("Autenticação bem-sucedida. Token: " + token);
            } else {
                logger.info("Falha na autenticação: Credenciais inválidas para " + loginDTO.getEmail());
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Falha na autenticação: Credenciais inválidas.");
            }
        } catch (RuntimeException e) {
            logger.warning("Erro ao autenticar usuário: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao autenticar usuário: " + e.getMessage());
        }
    }
}
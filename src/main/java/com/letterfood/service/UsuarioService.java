package com.letterfood.service;

import com.letterfood.config.MongoConfig;
import com.letterfood.dto.LoginDTO;
import com.letterfood.models.Usuario;
import com.letterfood.repository.UsuarioRepository;
import org.mindrot.jbcrypt.BCrypt;

import java.util.Optional;
import java.util.logging.Logger;

public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private static final Logger logger = Logger.getLogger(UsuarioService.class.getName());

    // Construtor com injeção de dependência
    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public UsuarioService(MongoConfig mongoConfig) {
        //TODO Auto-generated constructor stub
    }

    // Registro de novo usuário
    public void registrarUsuario(Usuario usuario) {
        if (usuario == null || usuario.getSenhaHash() == null || usuario.getSenhaHash().isEmpty()) {
            throw new IllegalArgumentException("Usuário e senha não podem ser nulos ou vazios.");
        }

        String senhaHash = hashPassword(usuario.getSenhaHash());
        usuario.setSenhaHash(senhaHash);
        usuarioRepository.save(usuario);

        logger.info("Usuário registrado com sucesso: " + usuario.getEmail());
    }

    // Autenticação de usuário
    public String autenticarUsuario(LoginDTO loginDTO) {
        if (loginDTO == null || loginDTO.getEmail() == null || loginDTO.getSenha() == null) {
            throw new IllegalArgumentException("Email e senha não podem ser nulos.");
        }

        Optional<Usuario> usuarioOptional = usuarioRepository.findByEmail(loginDTO.getEmail());
        Usuario usuario = usuarioOptional.orElseThrow(() -> new AuthenticationException("Usuário não encontrado"));

        if (validatePassword(loginDTO.getSenha(), usuario.getSenhaHash())) {
            String token = generateJwtToken(usuario);
            logger.info("Usuário autenticado com sucesso: " + usuario.getEmail());
            return token;
        } else {
            logger.warning("Falha de autenticação para o email: " + loginDTO.getEmail());
            throw new AuthenticationException("Credenciais inválidas.");
        }
    }

    // Hash seguro para senha
    private String hashPassword(String senha) {
        // Exemplo de hash usando BCrypt
        return BCrypt.hashpw(senha, BCrypt.gensalt());
    }

    // Validação de senha usando hash seguro
    private boolean validatePassword(String senha, String senhaHash) {
        return BCrypt.checkpw(senha, senhaHash);
    }

    // Geração de JWT usando uma biblioteca como JJWT
    private String generateJwtToken(Usuario usuario) {
        // Exemplo de criação de JWT usando JJWT
        return Jwts.builder()
                .setSubject(usuario.getEmail())
                .claim("id", usuario.getId())
                .signWith(SignatureAlgorithm.HS256, "chaveSecretaParaToken")
                .compact();
    }

    // Exceção personalizada para autenticação
    public static class AuthenticationException extends RuntimeException {
        public AuthenticationException(String message) {
            super(message);
        }
    }
}

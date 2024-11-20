package com.letterfood.controller;

import com.letterfood.models.Avaliacao;
import com.letterfood.service.RestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController // Indica que esta classe é um controlador REST
@RequestMapping("/api/restaurantes") // Prefixo para todos os endpoints deste controlador
public class RestauranteController {

    private final RestauranteService restauranteService;
    private static final Logger logger = Logger.getLogger(RestauranteController.class.getName());

    // Injeção de dependência via construtor
    @Autowired
    public RestauranteController(RestauranteService restauranteService) {
        this.restauranteService = restauranteService;
    }

    // Endpoint para adicionar uma avaliação ao restaurante
    @PostMapping("/{restauranteId}/avaliacoes") // Mapeia a requisição POST para /api/restaurantes/{restauranteId}/avaliacoes
    public ResponseEntity<String> adicionarAvaliacao(
            @PathVariable String restauranteId, // Extrai o restauranteId da URL
            @RequestBody Avaliacao avaliacao) {  // Extrai o corpo da requisição como um objeto Avaliacao
        if (restauranteId == null || restauranteId.isEmpty() || avaliacao == null) {
            // Retorna 400 Bad Request se faltarem dados obrigatórios
            throw new IllegalArgumentException("ID do restaurante e avaliação não podem ser nulos.");
        }

        try {
            restauranteService.adicionarAvaliacao(restauranteId, avaliacao);
            logger.info("Avaliação adicionada com sucesso para o restaurante ID: " + restauranteId);
            return ResponseEntity.status(HttpStatus.CREATED).body("Avaliação adicionada com sucesso.");
        } catch (RuntimeException e) {
            logger.warning("Erro ao adicionar avaliação: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao adicionar avaliação: " + e.getMessage());
        }
    }

    // Endpoint para listar avaliações de um restaurante
    @GetMapping("/{restauranteId}/avaliacoes") // Mapeia a requisição GET para /api/restaurantes/{restauranteId}/avaliacoes
    public ResponseEntity<List<Avaliacao>> listarAvaliacoes(
            @PathVariable String restauranteId) { // Extrai o restauranteId da URL
        if (restauranteId == null || restauranteId.isEmpty()) {
            // Retorna 400 Bad Request se o ID do restaurante for inválido
            throw new IllegalArgumentException("ID do restaurante não pode ser nulo.");
        }

        try {
            List<Avaliacao> avaliacoes = restauranteService.listarAvaliacoes(restauranteId);
            logger.info("Avaliações listadas para o restaurante ID: " + restauranteId);
            return ResponseEntity.ok(avaliacoes); // Retorna a lista com status 200 OK
        } catch (RuntimeException e) {
            logger.warning("Erro ao listar avaliações: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
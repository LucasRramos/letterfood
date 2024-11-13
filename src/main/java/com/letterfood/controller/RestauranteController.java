package com.letterfood.controller;

import com.letterfood.models.Avaliacao;
import com.letterfood.service.RestauranteService;

import java.util.List;
import java.util.logging.Logger;

public class RestauranteController {
    private final RestauranteService restauranteService;
    private static final Logger logger = Logger.getLogger(RestauranteController.class.getName());

    // Construtor com injeção de dependência
    public RestauranteController(RestauranteService restauranteService) {
        this.restauranteService = restauranteService;
    }

    // Método para adicionar uma avaliação ao restaurante
    public String adicionarAvaliacao(String restauranteId, Avaliacao avaliacao) {
        if (restauranteId == null || restauranteId.isEmpty() || avaliacao == null) {
            throw new IllegalArgumentException("ID do restaurante e avaliação não podem ser nulos.");
        }
        
        try {
            restauranteService.adicionarAvaliacao(restauranteId, avaliacao);
            logger.info("Avaliação adicionada com sucesso para o restaurante ID: " + restauranteId);
            return "Avaliação adicionada com sucesso.";
        } catch (RuntimeException e) {
            logger.warning("Erro ao adicionar avaliação: " + e.getMessage());
            return "Erro ao adicionar avaliação: " + e.getMessage();
        }
    }

    // Método para listar avaliações do restaurante
    public List<Avaliacao> listarAvaliacoes(String restauranteId) {
        if (restauranteId == null || restauranteId.isEmpty()) {
            throw new IllegalArgumentException("ID do restaurante não pode ser nulo.");
        }

        try {
            List<Avaliacao> avaliacoes = restauranteService.listarAvaliacoes(restauranteId);
            logger.info("Avaliações listadas para o restaurante ID: " + restauranteId);
            return avaliacoes;
        } catch (RuntimeException e) {
            logger.warning("Erro ao listar avaliações: " + e.getMessage());
            throw new RuntimeException("Erro ao listar avaliações: " + e.getMessage());
        }
    }
}

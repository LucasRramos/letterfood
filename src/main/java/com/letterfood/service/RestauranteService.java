package com.letterfood.service;

import com.letterfood.config.MongoConfig;
import com.letterfood.models.Avaliacao;
import com.letterfood.models.Restaurante;
import com.letterfood.repository.RestauranteRepository;

import java.util.List;
import java.util.logging.Logger;

public class RestauranteService {
    private final MongoConfig restauranteRepository;
    private static final Logger logger = Logger.getLogger(RestauranteService.class.getName());

    // Construtor com injeção de dependência
    public RestauranteService(MongoConfig mongoConfig) {
        this.restauranteRepository = mongoConfig;
    }

    // Adicionar uma avaliação ao restaurante
    public void adicionarAvaliacao(String restauranteId, Avaliacao avaliacao) {
        if (restauranteId == null || restauranteId.isEmpty()) {
            logger.warning("O ID do restaurante está vazio ou nulo.");
            throw new IllegalArgumentException("ID do restaurante não pode ser vazio ou nulo.");
        }
        if (avaliacao == null) {
            logger.warning("A avaliação fornecida está nula.");
            throw new IllegalArgumentException("Avaliação não pode ser nula.");
        }

        Restaurante restaurante = restauranteRepository.findById(restauranteId)
                .orElseThrow(() -> new RestauranteNotFoundException("Restaurante com ID " + restauranteId + " não encontrado."));
        
        restaurante.getAvaliacoes().add(avaliacao);
        restauranteRepository.save(restaurante);
        
        logger.info("Avaliação adicionada com sucesso ao restaurante com ID " + restauranteId);
    }

    // Listar todas as avaliações de um restaurante
    public List<Avaliacao> listarAvaliacoes(String restauranteId) {
        if (restauranteId == null || restauranteId.isEmpty()) {
            logger.warning("O ID do restaurante está vazio ou nulo.");
            throw new IllegalArgumentException("ID do restaurante não pode ser vazio ou nulo.");
        }

        Restaurante restaurante = restauranteRepository.findById(restauranteId)
                .orElseThrow(() -> new RestauranteNotFoundException("Restaurante com ID " + restauranteId + " não encontrado."));
        
        logger.info("Listando avaliações para o restaurante com ID " + restauranteId);
        return restaurante.getAvaliacoes();
    }
    
    // Exceção personalizada para restaurante não encontrado
    public static class RestauranteNotFoundException extends RuntimeException {
        public RestauranteNotFoundException(String message) {
            super(message);
        }
    }
}

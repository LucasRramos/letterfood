package com.letterfood.service;

import com.letterfood.model.Avaliacao;
import com.letterfood.model.Restaurante;
import com.letterfood.repository.RestauranteRepository;
import java.util.List;

public class RestauranteService {
    private RestauranteRepository restauranteRepository = new RestauranteRepository();

    public void adicionarAvaliacao(String restauranteId, Avaliacao avaliacao) {
        Restaurante restaurante = restauranteRepository.findById(restauranteId)
                .orElseThrow(() -> new RuntimeException("Restaurante não encontrado"));
        restaurante.getAvaliacoes().add(avaliacao);
        restauranteRepository.save(restaurante);
    }

    public List<Avaliacao> listarAvaliacoes(String restauranteId) {
        Restaurante restaurante = restauranteRepository.findById(restauranteId)
                .orElseThrow(() -> new RuntimeException("Restaurante não encontrado"));
        return restaurante.getAvaliacoes();
    }
}

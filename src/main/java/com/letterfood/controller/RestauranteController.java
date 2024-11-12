package com.letterfood.controller;

import com.letterfood.model.Avaliacao;
import com.letterfood.service.RestauranteService;

public class RestauranteController {
    private RestauranteService restauranteService = new RestauranteService();

    public void adicionarAvaliacao(String restauranteId, Avaliacao avaliacao) {
        restauranteService.adicionarAvaliacao(restauranteId, avaliacao);
    }

    public void listarAvaliacoes(String restauranteId) {
        restauranteService.listarAvaliacoes(restauranteId).forEach(System.out::println);
    }
}

package com.letterfood.controller;

import com.google.gson.Gson; // Importar Gson
import com.letterfood.models.Avaliacao;
import com.letterfood.models.Restaurante;
import com.letterfood.service.RestauranteService;

import static spark.Spark.*;

import java.io.InputStream;
import java.util.List;
import java.util.logging.Logger;

public class RestauranteController {
    private final RestauranteService restauranteService;
    private static final Logger logger = Logger.getLogger(RestauranteController.class.getName());

    // Construtor com injeção de dependência
    public RestauranteController(RestauranteService restauranteService) {
        this.restauranteService = restauranteService;
        definirEndpoints(); // Chamar o método para definir os endpoints
    }

    private void definirEndpoints() {
        Gson gson = new Gson(); // Instanciar Gson

        // Endpoint para adicionar uma avaliação
        post("/restaurantes/:id/avaliacoes", (req, res) -> {
            String restauranteId = req.params(":id");
            Avaliacao avaliacao = gson.fromJson(req.body(), Avaliacao.class); // Converter JSON para Avaliacao
            return adicionarAvaliacao(restauranteId, avaliacao);
        }, gson::toJson); // Usar Gson para converter a resposta em JSON

        // Endpoint para listar avaliações do restaurante
        get("/restaurantes/:id/avaliacoes", (req, res) -> {
            String restauranteId = req.params(":id");
            return listarAvaliacoes(restauranteId);
        }, gson::toJson); // Usar Gson para converter a resposta em JSON

        // Endpoint para adicionar imagem ao restaurante
        post("/restaurantes/:id/imagem", (req, res) -> {
            String restauranteId = req.params(":id");
            InputStream imagemStream = req.raw().getInputStream(); // Obter InputStream da imagem
            String nomeImagem = req.queryParams("nomeImagem"); // Obter nome da imagem dos parâmetros da query
            return adicionarImagemAoRestaurante(restauranteId, imagemStream, nomeImagem);
        });

        // Endpoint para buscar imagem do restaurante
        get("/restaurantes/:id/imagem", (req, res) -> {
            String restauranteId = req.params(":id");
            return buscarImagemDoRestaurante(restauranteId);
        });
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

    // Método para adicionar imagem ao restaurante
    public String adicionarImagemAoRestaurante(String restauranteId, InputStream imagemStream, String nomeImagem) {
        if (restauranteId == null || restauranteId.isEmpty() || imagemStream == null || nomeImagem == null) {
            throw new IllegalArgumentException("ID do restaurante, imagem e nome da imagem não podem ser nulos.");
        }

        try {
            String imagemId = restauranteService.adicionarImagemAoRestaurante(restauranteId, imagemStream, nomeImagem);
            logger.info("Imagem adicionada com sucesso ao restaurante ID: " + restauranteId);
            return "Imagem adicionada com sucesso com o ID: " + imagemId;
        } catch (RuntimeException e) {
            logger.warning("Erro ao adicionar imagem: " + e.getMessage());
            return "Erro ao adicionar imagem: " + e.getMessage();
        }
    }

    // Método para buscar imagem do restaurante
    public InputStream buscarImagemDoRestaurante(String restauranteId) {
        if (restauranteId == null || restauranteId.isEmpty()) {
            throw new IllegalArgumentException("ID do restaurante não pode ser nulo.");
        }

        try {
            InputStream imagem = restauranteService.buscarImagemDoRestaurante(restauranteId);
            logger.info("Imagem encontrada para o restaurante ID: " + restauranteId);
            return imagem; // Retorna o InputStream da imagem
        } catch (RuntimeException e) {
            logger.warning("Erro ao buscar imagem: " + e.getMessage());
            throw new RuntimeException("Erro ao buscar imagem: " + e.getMessage());
        }
    }
}
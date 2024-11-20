package com.letterfood.repository;

import com.letterfood.config.MongoConfig;
import com.letterfood.models.Restaurante;
import com.mongodb.client.MongoCollection;

import java.util.Optional; // Para Optional
import com.mongodb.client.model.Filters; // Para Filters

public class RestauranteRepository {

    private final MongoCollection<Restaurante> restauranteCollection;

    public RestauranteRepository(MongoConfig mongoConfig) {
        this.restauranteCollection = MongoConfig.getInstance().getDatabase().getCollection("restaurantes", Restaurante.class);
    }

    // Método para buscar restaurante por ID
    public Optional<Restaurante> findById(String id) {
        Restaurante restaurante = restauranteCollection.find(Filters.eq("_id", id)).first();
        return Optional.ofNullable(restaurante);
    }

    // Método para salvar restaurante (inserir ou atualizar)
    public void save(Restaurante restaurante) {
        restauranteCollection.insertOne(restaurante);  // Insere o restaurante
        // Se precisar atualizar, pode usar updateOne em vez de insertOne
    }

    public MongoCollection<Restaurante> getCollection() {
        return restauranteCollection;
    }
}

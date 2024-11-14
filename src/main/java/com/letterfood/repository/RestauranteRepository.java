package com.letterfood.repository;

import com.letterfood.config.MongoConfig;
import com.letterfood.models.Restaurante;
import com.mongodb.client.MongoCollection;

public class RestauranteRepository {

    private final MongoCollection<Restaurante> restauranteCollection;

    public RestauranteRepository() {
        this.restauranteCollection = MongoConfig.getInstance().getDatabase().getCollection("restaurantes", Restaurante.class);
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

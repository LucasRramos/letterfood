package com.letterfood.repository;

import com.letterfood.config.MongoConfig;
import com.letterfood.models.Restaurante;

public class RestauranteRepository extends BaseRepository<Restaurante> {
    public RestauranteRepository(MongoConfig mongoConfig) {
        super(mongoConfig, "restaurantes", Restaurante.class);
    }

    // Getter para collection
    @Override
    public MongoCollection<Restaurante> getCollection() {
        return super.getCollection();
    }
    
}

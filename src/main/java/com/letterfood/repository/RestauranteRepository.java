package com.letterfood.repository;

import com.letterfood.config.MongoConfig;
import com.letterfood.model.Restaurante;

public class RestauranteRepository extends BaseRepository<Restaurante> {
    public RestauranteRepository(MongoConfig mongoConfig) {
        super(mongoConfig, "restaurantes", Restaurante.class);
    }

    // Métodos específicos de Restaurante podem ser adicionados aqui
}

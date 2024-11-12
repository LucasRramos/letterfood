package com.letterfood;

import com.letterfood.config.MongoConfig;
import com.letterfood.controller.RestauranteController;
import com.letterfood.controller.UsuarioController;

public class LetterfoodApplication {
    public static void main(String[] args) {
        MongoConfig mongoConfig = new MongoConfig();
        mongoConfig.connect();

        UsuarioController usuarioController = new UsuarioController();
        RestauranteController restauranteController = new RestauranteController();

        System.out.println("Aplicação Letterfood inicializada.");
    }
}

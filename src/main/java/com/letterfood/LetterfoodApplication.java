package com.letterfood;

import com.letterfood.config.MongoConfig;
import com.letterfood.controller.RestauranteController;
import com.letterfood.controller.UsuarioController;
import com.letterfood.repository.RestauranteRepository;
import com.letterfood.repository.UsuarioRepository;

public class LetterfoodApplication {

    public static void main(String[] args) {
        // Configuração do MongoDB
        MongoConfig mongoConfig = new MongoConfig();
        mongoConfig.connect();

        // Inicialização dos repositórios
        UsuarioRepository usuarioRepository = new UsuarioRepository(mongoConfig);
        RestauranteRepository restauranteRepository = new RestauranteRepository(mongoConfig);

        // Inicialização dos controladores com os repositórios
        UsuarioController usuarioController = new UsuarioController(usuarioRepository);
        RestauranteController restauranteController = new RestauranteController(restauranteRepository);

        System.out.println("Aplicação Letterfood inicializada.");

        // Fechar conexão ao encerrar a aplicação
        Runtime.getRuntime().addShutdownHook(new Thread(mongoConfig::disconnect));
    }
}

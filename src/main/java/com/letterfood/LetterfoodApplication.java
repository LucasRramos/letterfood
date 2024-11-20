package com.letterfood;

import com.letterfood.config.MongoConfig;
import com.letterfood.controller.RestauranteController;
import com.letterfood.controller.UsuarioController;
import com.letterfood.repository.RestauranteRepository;
import com.letterfood.repository.UsuarioRepository;
import com.letterfood.service.RestauranteService;
import com.letterfood.service.UsuarioService;

import static spark.Spark.*;

public class LetterfoodApplication {

    public static void main(String[] args) {
        try {
            // Configuração do MongoDB
            MongoConfig mongoConfig = new MongoConfig();
    
            // Inicialização dos repositórios
            RestauranteRepository restauranteRepository = new RestauranteRepository(mongoConfig);
            UsuarioRepository usuarioRepository = new UsuarioRepository();
    
            // Inicialização dos serviços com os repositórios
            RestauranteService restauranteService = new RestauranteService(restauranteRepository);
            UsuarioService usuarioService = new UsuarioService(usuarioRepository);
    
            // Inicialização dos controladores com os serviços
            UsuarioController usuarioController = new UsuarioController(usuarioService);
            RestauranteController restauranteController = new RestauranteController(restauranteService);
    
            System.out.println("Aplicação Letterfood inicializada.");
    
            port(8080); // Define porta
            get("/", (req, res) -> "Letterfood API Rodando");
    
            // Fechar conexão ao encerrar a aplicação
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                stop();
                mongoConfig.disconnect();
            }));
        } catch (Exception e) {
            System.err.println("Erro ao inicializar a aplicação: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
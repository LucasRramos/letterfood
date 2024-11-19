package com.letterfood.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class MongoConfig {
    private static MongoConfig instance;
    private MongoClient mongoClient;
    private MongoDatabase database;
    
    public MongoConfig() {
        try {
            String connectionString = System.getenv("MONGO_URI");
            if (connectionString == null || connectionString.isEmpty()) {
                throw new IllegalArgumentException("A variável de ambiente MONGO_URI não está configurada.");
            }
            mongoClient = MongoClients.create(connectionString);
            database = mongoClient.getDatabase("letterfood");
        } catch (Exception e) {
            throw new RuntimeException("Erro ao conectar ao MongoDB: " + e.getMessage(), e);
        }
    }    

    public static MongoConfig getInstance() { 
        if (instance == null) {
            instance = new MongoConfig();
        }
        return instance;
    }

    public MongoDatabase getDatabase() {
        return database;
    }

    public void disconnect() {
        if (mongoClient != null) {
            mongoClient.close();
            System.out.println("Conexão com MongoDB fechada.");
        }
    }
}

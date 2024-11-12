package com.letterfood.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class MongoConfig {
    private MongoClient mongoClient;
    private MongoDatabase database;

    public void connect() {
        String connectionString = "mongodb://localhost:27017";
        mongoClient = MongoClients.create(connectionString);
        database = mongoClient.getDatabase("letterfood");
        System.out.println("Conectado ao MongoDB.");
    }

    public MongoDatabase getDatabase() {
        return database;
    }

    public void disconnect() {
        mongoClient.close();
        System.out.println("Conexão com MongoDB encerrada.");
    }
}

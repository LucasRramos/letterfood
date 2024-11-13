package com.letterfood.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class MongoConfig {
    private static MongoConfig instance;
    private MongoClient mongoClient;
    private MongoDatabase database;

    public MongoConfig() {
        String connectionString = System.getenv("MONGO_URI");
        mongoClient = MongoClients.create(connectionString);
        database = mongoClient.getDatabase("letterfood");
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
        }
    }

    public void connect() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'connect'");
    }
}

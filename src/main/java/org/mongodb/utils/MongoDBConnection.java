package org.mongodb.utils;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoException;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class MongoDBConnection {

    private static MongoClient client;

    public static MongoDatabase getDatabase() {
        // Connection string to your MongoDB Atlas instance
        String connectionString = System.getenv("MONGO_URI");

        ServerApi serverApi = ServerApi.builder()
                .version(ServerApiVersion.V1)
                .build();

        // MongoClient settings to apply the connection string and server API version
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(connectionString))
                .serverApi(serverApi)
                .build();

        // Create a new MongoClient (without closing it immediately)
        if (client == null) {
            client = MongoClients.create(settings);
        }

        try {
            // Ping the database to ensure a successful connection
            MongoDatabase database = client.getDatabase("admin");
            database.runCommand(new Document("ping", 1));
            System.out.println("Pinged your deployment. You successfully connected to MongoDB!");
        } catch (MongoException e) {
            e.printStackTrace();
        }

        // Return the MongoDatabase object (use your actual database name)
        return client.getDatabase("test_ticketing_system");
    }

    // Close the MongoClient connection
    public static void closeClient() {
        if (client != null) {
            client.close();
        }
    }
}

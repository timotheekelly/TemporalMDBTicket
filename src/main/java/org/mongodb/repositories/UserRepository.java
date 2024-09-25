package org.mongodb.repositories;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.mongodb.models.User;

public class UserRepository {

    private final MongoCollection<Document> collection;

    public UserRepository(MongoDatabase database) {
        this.collection = database.getCollection("users");
    }

    // Retrieve the next userId by finding the user with the highest userId
    public Integer getNextUserId() {
        Document lastUser = collection.find().sort(new Document("userId", -1)).limit(1).first();
        if (lastUser != null) {
            return lastUser.getInteger("userId") + 1;
        } else {
            return 1; // Default to 1 if no users exist
        }
    }

    // Create a new user and insert it into the collection
    public void createUser(User user) {
        try {
            Document newUser = new Document("_id", new ObjectId())
                    .append("userId", user.getUserId())
                    .append("firstName", user.getFirstName())
                    .append("lastName", user.getLastName())
                    .append("title", user.getTitle());

            collection.insertOne(newUser);
            System.out.println("User created: " + user.getFirstName() + " " + user.getLastName());
        } catch (Exception e) {
            System.err.println("Error while inserting user: " + e.getMessage());
        }
    }


    // Retrieve a user by their first and last name
    public Integer getUserIdByName(String firstName, String lastName) {
        Document userDoc = collection.find(new Document("firstName", firstName).append("lastName", lastName)).first();
        if (userDoc != null) {
            return userDoc.getInteger("userId");
        }
        return null;
    }
}

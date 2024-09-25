package org.mongodb;

import org.junit.jupiter.api.*;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.mongodb.repositories.UserRepository;
import org.mongodb.services.UserService;

import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest {

    private static MongoClient client;
    private static MongoDatabase database;
    private UserService userService;

    @BeforeAll
    public static void setupDatabase() {
        // Use MongoDB Atlas connection string
        String connectionString = System.getenv("MONGO_URI");

        // Initialize the MongoClient and select the test database
        client = MongoClients.create(connectionString);
        database = client.getDatabase("test_ticketing_system");

        // Ensure the collection is clean before each test run
        database.getCollection("users").drop();
    }

    @BeforeEach
    public void setup() {
        UserRepository userRepository = new UserRepository(database);
        userService = new UserService(userRepository);
    }

    @Test
    public void testCreateUser() {
        int newUserId = userService.createUser("John", "Doe", "Engineer");

        Integer userId = userService.getUserId("John", "Doe");
        assertEquals(newUserId, userId, "User ID should match the one returned from createUser");
    }

    @Test
    public void testGetNonExistentUser() {
        Integer userId = userService.getUserId("Jane", "Smith");
        assertNull(userId, "User ID should be null for non-existent user");
    }

    @AfterAll
    public static void tearDown() {
        // Drop the test collection after all tests are complete
        database.getCollection("users").drop();
        client.close();
    }
}

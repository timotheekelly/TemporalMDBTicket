package org.mongodb.controllers;

import io.javalin.Javalin;
import org.mongodb.repositories.UserRepository;
import org.mongodb.services.UserService;
import org.mongodb.utils.MongoDBConnection;

public class UserController {

    public static void main(String[] args) {
        Javalin app = Javalin.create().start(8000);

        UserService userService = new UserService(new UserRepository(MongoDBConnection.getDatabase()));

        app.post("/users/create", ctx -> {
            String firstName = ctx.queryParam("firstName");
            String lastName = ctx.queryParam("lastName");
            String title = ctx.queryParam("title");

            Integer userId = userService.createUser(firstName, lastName, title);
            ctx.json(userId);
        });

        app.get("/users/getUserId", ctx -> {
            String firstName = ctx.queryParam("firstName");
            String lastName = ctx.queryParam("lastName");

            Integer userId = userService.getUserId(firstName, lastName);
            ctx.json(userId);
        });
    }
}

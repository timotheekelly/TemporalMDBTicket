package org.mongodb.controllers;

import io.javalin.Javalin;
import org.mongodb.models.PriorityType;
import org.mongodb.models.SupportEngineerType;
import org.mongodb.models.TicketStatusType;
import org.mongodb.services.TicketService;

public class TicketController {

    public static Javalin createApp(TicketService ticketService) {
        Javalin app = Javalin.create();

        app.post("/tickets/create", ctx -> {
            int customerId = Integer.parseInt(ctx.formParam("customerId"));
            int responseMinSLA = Integer.parseInt(ctx.formParam("responseMinSLA"));
            String priority = ctx.formParam("priority");
            String status = ctx.formParam("status");

            // Create ticket and assign to most available engineer
            int ticketNumber = ticketService.createTicket(
                    customerId,
                    responseMinSLA,
                    PriorityType.valueOf(priority),
                    TicketStatusType.valueOf(status)
            );
            ctx.json(ticketNumber);
        });

        app.put("/tickets/:ticketNumber/assign", ctx -> {
            int ticketNumber = Integer.parseInt(ctx.pathParam("ticketNumber"));
            String assignedTo = ctx.formParam("assignedTo");

            boolean success = ticketService.updateSupportEngineer(ticketNumber, SupportEngineerType.valueOf(assignedTo));
            if (success) {
                ctx.status(200);
            } else {
                ctx.status(400);
            }
        });

        return app;
    }
}

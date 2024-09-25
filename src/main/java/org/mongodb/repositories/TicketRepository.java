package org.mongodb.repositories;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.mongodb.models.PriorityType;
import org.mongodb.models.SupportEngineerType;
import org.mongodb.models.Ticket;
import org.mongodb.models.TicketStatusType;

import java.util.Optional;

public class TicketRepository {

    private final MongoCollection<Document> collection;

    public TicketRepository(MongoDatabase database) {
        this.collection = database.getCollection("tickets");
    }

    // Get the next ticket number
    public int getNextTicketNumber() {
        Document lastTicket = collection.find().sort(new Document("ticketNumber", -1)).first();
        if (lastTicket != null) {
            return lastTicket.getInteger("ticketNumber") + 1;
        }
        return 1;  // Default ticket number if no tickets exist
    }

    // Create a new ticket
    public void createTicket(Ticket ticket) {
        Document newTicket = new Document("_id", new ObjectId())
                .append("ticketNumber", ticket.getTicketNumber())
                .append("customerId", ticket.getCustomerId())
                .append("responseMinSLA", ticket.getResponseMinSLA())
                .append("priority", ticket.getPriority().toString())
                .append("status", ticket.getStatus().toString())
                .append("assignedTo", ticket.getAssignedTo().toString());

        collection.insertOne(newTicket);
    }

    // Retrieve a ticket by its ticket number
    public Optional<Ticket> getTicketByNumber(int ticketNumber) {
        Document document = collection.find(Filters.eq("ticketNumber", ticketNumber)).first();
        if (document != null) {
            Ticket ticket = new Ticket(
                    document.getInteger("ticketNumber"),
                    document.getInteger("customerId"),
                    document.getInteger("responseMinSLA"),
                    PriorityType.valueOf(document.getString("priority")),
                    TicketStatusType.valueOf(document.getString("status")),
                    SupportEngineerType.valueOf(document.getString("assignedTo"))
            );
            return Optional.of(ticket);
        }
        return Optional.empty();
    }

    // Update the assigned support engineer
    public boolean updateAssignedEngineer(int ticketNumber, SupportEngineerType assignedTo) {
        Document updatedTicket = collection.findOneAndUpdate(
                Filters.eq("ticketNumber", ticketNumber),
                Updates.set("assignedTo", assignedTo.toString())
        );
        return updatedTicket != null;
    }

    // Update the priority of a ticket
    public boolean updatePriority(int ticketNumber, PriorityType priority) {
        Document updatedTicket = collection.findOneAndUpdate(
                Filters.eq("ticketNumber", ticketNumber),
                Updates.set("priority", priority.toString())
        );
        return updatedTicket != null;
    }

    // Update the status of a ticket
    public boolean updateStatus(int ticketNumber, TicketStatusType status) {
        Document updatedTicket = collection.findOneAndUpdate(
                Filters.eq("ticketNumber", ticketNumber),
                Updates.set("status", status.toString())
        );
        return updatedTicket != null;
    }
}

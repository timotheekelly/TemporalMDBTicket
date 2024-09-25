package org.mongodb.repositories;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.mongodb.models.SupportEngineer;

import java.util.ArrayList;
import java.util.Optional;

import static com.mongodb.client.model.Aggregates.*;
import static com.mongodb.client.model.Accumulators.sum;
import static com.mongodb.client.model.Sorts.ascending;

public class SupportEngineerRepository {

    private final MongoCollection<Document> collection;

    public SupportEngineerRepository(MongoDatabase database) {
        this.collection = database.getCollection("supportEngineers");
    }

    // Get the next available engineer number
    public int getNextEngineerNumber() {
        Document lastEngineer = collection.find().sort(new Document("engineerNumber", -1)).first();
        if (lastEngineer != null) {
            return lastEngineer.getInteger("engineerNumber") + 1;
        }
        return 1;  // Default engineer number
    }

    // Create a new support engineer and insert into the database
    public int createUniqueEngineer(String firstName, String lastName) {
        int engineerNumber = getNextEngineerNumber();
        SupportEngineer newEngineer = new SupportEngineer(engineerNumber, firstName, lastName, true, 0, new ArrayList<>());
        Document engineerDoc = new Document("_id", new ObjectId())
                .append("engineerNumber", newEngineer.getEngineerNumber())
                .append("firstName", newEngineer.getFirstName())
                .append("lastName", newEngineer.getLastName())
                .append("active", newEngineer.isActive())
                .append("numAssignedTickets", newEngineer.getNumAssignedTickets())
                .append("tickets", newEngineer.getTickets());

        collection.insertOne(engineerDoc);
        return engineerNumber;
    }

    // Get engineer by number
    public Optional<SupportEngineer> getEngineerByNumber(int engineerNumber) {
        Document engineerDoc = collection.find(Filters.eq("engineerNumber", engineerNumber)).first();
        if (engineerDoc != null) {
            SupportEngineer engineer = new SupportEngineer(
                    engineerDoc.getInteger("engineerNumber"),
                    engineerDoc.getString("firstName"),
                    engineerDoc.getString("lastName"),
                    engineerDoc.getBoolean("active"),
                    engineerDoc.getInteger("numAssignedTickets"),
                    new ArrayList<>()  // Tickets can be handled separately
            );
            return Optional.of(engineer);
        }
        return Optional.empty();
    }

    // Update engineer's ticket count
    public boolean updateAssignedTickets(int engineerNumber, int newTicketCount) {
        Document updatedEngineer = collection.findOneAndUpdate(
                Filters.eq("engineerNumber", engineerNumber),
                Updates.set("numAssignedTickets", newTicketCount)
        );
        return updatedEngineer != null;
    }

    // Find the least utilized active engineer and return their engineer number
    public Optional<Integer> findMostAvailableEngineer() {
        // Aggregation to find the least utilized engineer
        Document leastUtilizedEngineer = collection.aggregate(
                new ArrayList<Document>() {{
                    add(new Document("$match", new Document("active", true)));
                    add(new Document("$sort", new Document("numAssignedTickets", 1)));
                    add(new Document("$limit", 1));
                }}
        ).first();

        if (leastUtilizedEngineer != null) {
            return Optional.of(leastUtilizedEngineer.getInteger("engineerNumber"));
        }
        return Optional.empty();
    }
}

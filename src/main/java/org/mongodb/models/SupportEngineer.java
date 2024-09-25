package org.mongodb.models;

import org.bson.types.ObjectId;
import java.util.List;

public class SupportEngineer {

    private ObjectId id;
    private int engineerNumber;
    private String firstName;
    private String lastName;
    private boolean active;
    private int numAssignedTickets;
    private List<Ticket> tickets;

    public SupportEngineer(int engineerNumber, String firstName, String lastName, boolean active, int numAssignedTickets, List<Ticket> tickets) {
        this.engineerNumber = engineerNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.active = active;
        this.numAssignedTickets = numAssignedTickets;
        this.tickets = tickets;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public int getEngineerNumber() {
        return engineerNumber;
    }

    public void setEngineerNumber(int engineerNumber) {
        this.engineerNumber = engineerNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getNumAssignedTickets() {
        return numAssignedTickets;
    }

    public void setNumAssignedTickets(int numAssignedTickets) {
        this.numAssignedTickets = numAssignedTickets;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }
}

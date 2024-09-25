package org.mongodb.models;

import java.util.Date;

public class Ticket {
    private int ticketNumber;
    private int customerId;
    private int responseMinSLA;
    private PriorityType priority;
    private TicketStatusType status;
    private SupportEngineerType assignedTo;

    // Constructor
    public Ticket(int ticketNumber, int customerId, int responseMinSLA, PriorityType priority, TicketStatusType status, SupportEngineerType assignedTo) {
        this.ticketNumber = ticketNumber;
        this.customerId = customerId;
        this.responseMinSLA = responseMinSLA;
        this.priority = priority;
        this.status = status;
        this.assignedTo = assignedTo;
    }

    // Getters and Setters
    public int getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(int ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getResponseMinSLA() {
        return responseMinSLA;
    }

    public void setResponseMinSLA(int responseMinSLA) {
        this.responseMinSLA = responseMinSLA;
    }

    public PriorityType getPriority() {
        return priority;
    }

    public void setPriority(PriorityType priority) {
        this.priority = priority;
    }

    public TicketStatusType getStatus() {
        return status;
    }

    public void setStatus(TicketStatusType status) {
        this.status = status;
    }

    public SupportEngineerType getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(SupportEngineerType assignedTo) {
        this.assignedTo = assignedTo;
    }
}

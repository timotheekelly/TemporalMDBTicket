package org.mongodb.models;

public class TicketDescription {
    private int customerId;
    private PriorityType priority;
    private String title;
    private String description;

    public TicketDescription(int customerId, PriorityType priority, String title, String description) {
        this.customerId = customerId;
        this.priority = priority;
        this.title = title;
        this.description = description;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public PriorityType getPriority() {
        return priority;
    }

    public void setPriority(PriorityType priority) {
        this.priority = priority;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

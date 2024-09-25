package org.mongodb.dto;

public class EngineerRequest {
    private String assignedTo;

    public EngineerRequest() {}

    public EngineerRequest(String assignedTo) {
        this.assignedTo = assignedTo;
    }

    public String getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
    }
}

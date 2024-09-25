package org.mongodb.dto;

import org.mongodb.models.PriorityType;
import org.mongodb.models.SupportEngineerType;
import org.mongodb.models.TicketStatusType;

public class TicketRequest {
    private int customerId;
    private int responseMinSLA;
    private PriorityType priority;
    private TicketStatusType status;
    private SupportEngineerType assignedTo;

    public TicketRequest() {}

    public TicketRequest(int customerId, int responseMinSLA, PriorityType priority, TicketStatusType status, SupportEngineerType assignedTo) {
        this.customerId = customerId;
        this.responseMinSLA = responseMinSLA;
        this.priority = priority;
        this.status = status;
        this.assignedTo = assignedTo;
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

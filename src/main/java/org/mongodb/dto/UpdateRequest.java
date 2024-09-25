package org.mongodb.dto;

import org.mongodb.models.PriorityType;
import org.mongodb.models.TicketStatusType;

public class UpdateRequest {
    private PriorityType priority;
    private TicketStatusType status;

    public UpdateRequest() {}

    public UpdateRequest(PriorityType priority, TicketStatusType status) {
        this.priority = priority;
        this.status = status;
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
}

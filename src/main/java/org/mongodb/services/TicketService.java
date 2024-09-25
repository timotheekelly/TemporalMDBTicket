package org.mongodb.services;

import org.mongodb.models.PriorityType;
import org.mongodb.models.SupportEngineerType;
import org.mongodb.models.Ticket;
import org.mongodb.models.TicketStatusType;
import org.mongodb.repositories.SupportEngineerRepository;
import org.mongodb.repositories.TicketRepository;

import java.util.Optional;

public class TicketService {

    private final TicketRepository ticketRepository;
    private final SupportEngineerRepository engineerRepository;

    public TicketService(TicketRepository ticketRepository, SupportEngineerRepository engineerRepository) {
        this.ticketRepository = ticketRepository;
        this.engineerRepository = engineerRepository;
    }

    // Create a ticket and assign it to the least utilized engineer
    public int createTicket(int customerId, int responseMinSLA, PriorityType priority, TicketStatusType status) {
        // Find the most available engineer
        Optional<Integer> availableEngineerOpt = engineerRepository.findMostAvailableEngineer();
        if (!availableEngineerOpt.isPresent()) {
            throw new IllegalStateException("No available support engineer.");
        }

        // Assign the ticket to the most available engineer
        int ticketNumber = ticketRepository.getNextTicketNumber();
        SupportEngineerType assignedEngineer = SupportEngineerType.values()[availableEngineerOpt.get()];
        Ticket ticket = new Ticket(ticketNumber, customerId, responseMinSLA, priority, status, assignedEngineer);
        ticketRepository.createTicket(ticket);

        // Update the assigned engineer's ticket count
        engineerRepository.updateAssignedTickets(assignedEngineer.ordinal(), 1);

        return ticketNumber;
    }

    // Retrieve a ticket by its number
    public Optional<Ticket> getTicketByNumber(int ticketNumber) {
        return ticketRepository.getTicketByNumber(ticketNumber);
    }

    // Update the assigned engineer
    public boolean updateSupportEngineer(int ticketNumber, SupportEngineerType assignedTo) {
        return ticketRepository.updateAssignedEngineer(ticketNumber, assignedTo);
    }

    // Update the priority of the ticket separately
    public boolean updateTicketPriority(int ticketNumber, PriorityType priority) {
        return ticketRepository.updatePriority(ticketNumber, priority);
    }

    // Update the status of the ticket separately
    public boolean updateTicketStatus(int ticketNumber, TicketStatusType status) {
        return ticketRepository.updateStatus(ticketNumber, status);
    }
}

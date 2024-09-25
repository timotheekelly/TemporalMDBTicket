package org.mongodb;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.junit.jupiter.api.*;
import org.mongodb.models.PriorityType;
import org.mongodb.models.SupportEngineerType;
import org.mongodb.models.Ticket;
import org.mongodb.models.TicketStatusType;
import org.mongodb.repositories.SupportEngineerRepository;
import org.mongodb.repositories.TicketRepository;
import org.mongodb.services.TicketService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TicketServiceTest {

    private static MongoClient client;
    private static MongoDatabase database;
    private SupportEngineerRepository engineerRepository;
    private TicketService ticketService;

    @BeforeAll
    public static void setupDatabase() {
        // Use MongoDB Atlas connection string
        String connectionString = System.getenv("MONGO_URI");

        // Initialize the MongoClient and select the test database
        client = MongoClients.create(connectionString);
        database = client.getDatabase("test_ticketing_system");

        // Ensure the collection is clean before each test run
        database.getCollection("tickets").drop();
    }

    @BeforeEach
    public void setup() {
        TicketRepository ticketRepository = new TicketRepository(database);
        engineerRepository = new SupportEngineerRepository(database);
        ticketService = new TicketService(ticketRepository, engineerRepository);
    }

    @Test
    public void testCreateTicketAndAssignEngineer() {
        int engineerNumber = engineerRepository.createUniqueEngineer("John", "Doe");
        int ticketNumber = ticketService.createTicket(123, 60, PriorityType.S1, TicketStatusType.UNASSIGNED);

        // Verify that the ticket was created and assigned to an engineer
        var ticket = ticketService.getTicketByNumber(ticketNumber);
        assertTrue(ticket.isPresent());
        assertEquals(SupportEngineerType.values()[engineerNumber], ticket.get().getAssignedTo());
    }

    @Test
    public void testUpdateSupportEngineer() {
        // Arrange
        int customerId = 124;
        int responseMinSLA = 50;
        PriorityType priority = PriorityType.S2;
        TicketStatusType status = TicketStatusType.UNASSIGNED;
        SupportEngineerType originalEngineer = SupportEngineerType.ADE;

        // Create a ticket
        int ticketNumber = ticketService.createTicket(customerId, responseMinSLA, priority, status);

        // Act - Update the support engineer
        boolean updated = ticketService.updateSupportEngineer(ticketNumber, SupportEngineerType.MARY);

        // Assert
        assertTrue(updated, "Support engineer should be updated");
        Optional<Ticket> updatedTicket = ticketService.getTicketByNumber(ticketNumber);
        assertTrue(updatedTicket.isPresent(), "Updated ticket should be present in the database");
        assertEquals(SupportEngineerType.MARY, updatedTicket.get().getAssignedTo(), "Assigned engineer should be updated to MARY");
    }

    @Test
    public void testUpdateTicketPriority() {
        // Arrange
        int customerId = 125;
        int responseMinSLA = 45;
        PriorityType originalPriority = PriorityType.S3;
        TicketStatusType status = TicketStatusType.UNASSIGNED;
        SupportEngineerType assignedTo = SupportEngineerType.MIKE;

        // Create a ticket
        int ticketNumber = ticketService.createTicket(customerId, responseMinSLA, originalPriority, status);

        // Act - Update the priority
        boolean updated = ticketService.updateTicketPriority(ticketNumber, PriorityType.S1);

        // Assert
        assertTrue(updated, "Priority should be updated");
        Optional<Ticket> updatedTicket = ticketService.getTicketByNumber(ticketNumber);
        assertTrue(updatedTicket.isPresent(), "Updated ticket should be present in the database");
        assertEquals(PriorityType.S1, updatedTicket.get().getPriority(), "Priority should be updated to S1");
    }

    @Test
    public void testUpdateTicketStatus() {
        // Arrange
        int customerId = 126;
        int responseMinSLA = 70;
        PriorityType priority = PriorityType.S4;
        TicketStatusType originalStatus = TicketStatusType.UNASSIGNED;
        SupportEngineerType assignedTo = SupportEngineerType.STEVE;

        // Create a ticket
        int ticketNumber = ticketService.createTicket(customerId, responseMinSLA, priority, originalStatus);

        // Act - Update the status
        boolean updated = ticketService.updateTicketStatus(ticketNumber, TicketStatusType.IN_PROGRESS);

        // Assert
        assertTrue(updated, "Status should be updated");
        Optional<Ticket> updatedTicket = ticketService.getTicketByNumber(ticketNumber);
        assertTrue(updatedTicket.isPresent(), "Updated ticket should be present in the database");
        assertEquals(TicketStatusType.IN_PROGRESS, updatedTicket.get().getStatus(), "Status should be updated to IN_PROGRESS");
    }

    @AfterAll
    public static void tearDown() {
        // Drop the test collection after all tests are complete
        database.getCollection("tickets").drop();
        client.close();
    }
}

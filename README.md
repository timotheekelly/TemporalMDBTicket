# TemporalMDBTicket

## Overview

This application is a **Java-based Ticketing System** designed to handle technical support tickets, manage support engineers, and perform various operations like ticket creation, assignment, and status updates. 

---

## Core Features

1. **Ticket Creation & Management:**
   - Users can create new tickets with various attributes such as customer ID, priority, response SLA, and initial status.
   - Each ticket is assigned a unique ticket number.
   - Tickets can be updated with new priorities, statuses, or reassigned to different engineers.

2. **Support Engineer Assignment:**
   - Engineers are automatically assigned to tickets based on their workload (number of assigned tickets).
   - The assignment algorithm ensures that the least-burdened engineers are selected.

3. **Status Management:**
   - Tickets can have various statuses such as "unassigned", "in progress", "waiting for customer", and "closed".
   - The ticket status can be updated during its lifecycle as work progresses.

---

## Key Classes

1. **TicketService**
   - Contains core business logic for managing tickets, such as creating new tickets, updating priorities, and assigning support engineers.

2. **TicketRepository**
   - Handles database interactions for tickets, including saving, retrieving, and updating ticket information.

3. **SupportEngineerRepository**
   - Manages the support engineer database, including creating new engineers and finding the least busy engineer for ticket assignment.

---

## How to Run

### 1. Set Up MongoDB
Ensure that a MongoDB instance is running and set the environment variable `MONGO_URI` with your MongoDB connection string.

Example:
```bash
export MONGO_URI="mongodb+srv://username:password@cluster.mongodb.net/ticketing_system"
```

### 2. Run the Application
Use Maven to build and run the Java application.

```bash
mvn clean install
mvn exec:java -Dexec.mainClass="org.mongodb.Main"
```

### 3. Testing the Application
JUnit tests are provided to ensure that the ticketing system functions correctly.

```bash
mvn test
```

---

## Future Enhancements: Temporal Integration

### **Adding Temporal for Workflow Management**

In the future, **Temporal** will be integrated into the application to handle long-running workflows like:
- **Ticket Lifecycle Automation:** Temporal will manage the lifecycle of each ticket, automating tasks such as engineer assignment, SLA checks, and escalation.
- **SLA and Escalation Management:** The system will track whether SLAs are met, automatically escalating tickets when necessary.

This will significantly enhance the system's ability to handle time-based processes and automate repetitive tasks efficiently.

---

## Conclusion

This Java-based ticketing system provides a robust foundation for managing support tickets and engineers. With future enhancements such as Temporal for workflow management, the application will become more automated and efficient in handling complex, time-dependent processes.

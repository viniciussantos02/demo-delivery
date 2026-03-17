# DemoDelivery

A demo repository for studying Domain Driven Design (DDD) in a microservices architecture. This project simulates a delivery system with two main microservices: Delivery Tracking and Courier Management.

## Features

- **Delivery Tracking**: Manages delivery orders, including items, status transitions, and tracking.
- **Courier Management**: Handles courier-related operations.
- Domain-Driven Design principles applied, with aggregates, value objects, and domain events.
- RESTful APIs for interacting with the services.
- PostgreSQL database for data persistence.

## Architecture

The project follows a microservices architecture:

- **Delivery-Tracking Service** (Port 8080): Handles delivery creation, status updates, and item management.
- **Courier-Management Service** (Port 8081): Manages couriers and their assignments.
- Shared database setup using Docker Compose for PostgreSQL and PgAdmin.

## Technologies Used

- **Java 25**
- **Spring Boot 3.5.9**
- **Spring Data JPA**
- **PostgreSQL**
- **Maven** for build management
- **Lombok** for reducing boilerplate code
- **Docker** and **Docker Compose** for containerization

## Prerequisites

- Java 25 or higher
- Maven 3.6+
- Docker and Docker Compose

## Installation and Running

1. **Clone the repository**:
   ```bash
   git clone <https://github.com/viniciussantos02/demo-delivery.git>
   cd DemoDelivery
   ```

2. **Start the database**:
   Use Docker Compose to start PostgreSQL and PgAdmin:
   ```bash
   docker-compose up -d
   ```
   - PostgreSQL will be available on port 5432.
   - PgAdmin will be accessible at http://localhost:8083 (email: dba@demodelivery.com, password: demodelivery).

3. **Run the microservices**:
   - For Delivery-Tracking:
     ```bash
     cd Microservices/Delivery-Tracking
     mvn spring-boot:run
     ```
   - For Courier-Management:
     ```bash
     cd Microservices/Courier-Management
     mvn spring-boot:run
     ```

4. **Access the services**:
   - Delivery-Tracking API: http://localhost:8080
   - Courier-Management API: http://localhost:8081

## Usage

- Use REST clients like Postman or curl to interact with the APIs.
- Example: Create a delivery draft, add items, set details, place the order, assign to courier, etc.

## Contributing

This is a demo project for learning purposes. Feel free to fork and experiment.

## License

Not specified.

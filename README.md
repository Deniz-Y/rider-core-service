# Login/Registration Service 

 This project demonstrates an implementation of **Kotlin** with **Spring Boot**, specifically designed for **Registration and Login** processes.

## Features

- **Rider Management:** CRUD operations
- **Secure Authentication:** Password hashing using **BCrypt** (Spring Security).
- **Global Exception Handling:** Error management with JSON responses.
- **Containerization:** Dockerized PostgreSQL database for local development.
- **Unit Testing:** Test for business logic using **JUnit 5** and **Mockito**.

## Tech Stack

- **Language:** Kotlin
- **Framework:** Spring Boot 3.x
- **Database:** PostgreSQL 15
- **Security:** Spring Security (BCrypt)
- **Containerization:** Docker & Docker Compose
- **Build Tool:** Gradle (Kotlin DSL)
- **Testing:** JUnit 5, Mockito, MockK

## Getting Started

### Prerequisites
- Docker & Docker Compose
- JDK 17 or higher

### 1. Database Setup
Start the PostgreSQL container:
```bash
docker-compose up -d
 ```
### 2. Run the Application
```bash
./gradlew bootRun
 ```
### 3. Run Unit Tests
```bash
./gradlew test
```

## API Documentation

The service exposes the following endpoints under `http://localhost:8080/api/auth`:

| Method | Endpoint | Description | Payload Example |
| :--- | :--- | :--- | :--- |
| **POST** | `/register` | Register a new rider | `{"email": "...", "password": "...", "firstName": "...", "lastName": "..."}` |
| **POST** | `/login` | Authenticate rider | `{"email": "...", "password": "..."}` |
| **PUT** | `/{id}` | Update profile | `{"firstName": "NewName", "lastName": "NewSurname"}` |
| **DELETE** | `/{id}` | Remove a rider | *None* |

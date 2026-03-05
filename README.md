# eCommerce Application

Microservices e-commerce application built with **Spring Boot 4.0.3** and **Java 25**, fully dockerized with PostgreSQL as the database.

## 🚀 Features

- Complete user management
- Product catalog with search functionality
- Shopping cart
- Order system
- RESTful API
- PostgreSQL database (product and order service)
- MongoDB database (user service)
- Database administration panel with pgAdmin

## 📋 Prerequisites

- Docker and Docker Compose
- Java 25 (for local development)
- Maven 3.x (for local development)
- MongoDB installed locally or configured in the cloud via Atlas

## 🐳 Installation and Execution with Docker

1. Clone the repository:
```bash
git clone <repository-url>
cd ecom-microservices-application
```

2. Start services with Docker Compose:
```bash
docker-compose up -d
```

This will start:
- **PostgreSQL** on port `5432`
- **pgAdmin** on port `5050`

3. Build and run the application:
```bash
./mvnw clean package
./mvnw spring-boot:run
```

## 🔧 Configuration

### Database
The application connects to PostgreSQL with the following configuration (defined in `application.yml`):

- **URL**: `jdbc:postgresql://localhost:5432/leoga`
- **Username**: `leoga`
- **Password**: `leoga`

### pgAdmin
Access pgAdmin at `http://localhost:5050`:
- **Email**: `pgadmin4@pgadmin.org`
- **Password**: `admin`

### MongoDB (Install Compass)
- url: mongodb://localhost:27017/userdb

## 📚 API Documentation

For more details about available endpoints, see [API_DOCUMENTATION.md](API_DOCUMENTATION.md)

## 🏗️ Architecture

This is a **monolithic** application with the following structure:

```
ecom-microservices-application/
├── order
│   ├── main/
│   │   ├── java/com/leoga/ecom/order/
│   │   │   ├── controllers/     # REST Controllers
│   │   │   ├── services/        # Business Logic
│   │   │   ├── repositories/    # Data Access
│   │   │   ├── model/           # JPA Entities
│   │   │   └── dto/             # Data Transfer Objects
│   │   └── resources/
│   │       └── application.yml
│   └── test/
├── product
│   ├── main/
│   │   ├── java/com/leoga/ecom/product/
│   │   │   ├── controllers/     # REST Controllers
│   │   │   ├── services/        # Business Logic
│   │   │   ├── repositories/    # Data Access
│   │   │   ├── model/           # JPA Entities
│   │   │   └── dto/             # Data Transfer Objects
│   │   └── resources/
│   │       └── application.yml
│   └── test/
├── user
│   ├── main/
│   │   ├── java/com/leoga/ecom/user/
│   │   │   ├── controllers/     # REST Controllers
│   │   │   ├── services/        # Business Logic
│   │   │   ├── repositories/    # Data Access
│   │   │   ├── model/           # JPA Entities
│   │   │   └── dto/             # Data Transfer Objects
│   │   └── resources/
│   │       └── application.yml
│   └── test/
├── docker-compose.yml
```

## 🛠️ Technologies

- **Framework**: Spring Boot 4.0.3
- **Language**: Java 25
- **Database**: PostgreSQL 18 And MongoDB 8.2.5 Community 
- **ORM**: Hibernate/JPA
- **Build Tool**: Maven
- **Containerization**: Docker & Docker Compose
- **Additional Dependencies**:
  - Lombok
  - Spring Data JPA

## 📝 License

This project is part of a training course and is available for educational purposes.

## 👤 Author

Leoga

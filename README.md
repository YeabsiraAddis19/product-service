# Product Service

![CI](https://github.com/YeabsiraAddis19/product-service/actions/workflows/ci.yml/badge.svg)

A Spring Boot REST API for managing products with H2 in-memory database.

**Author:** Yeabsira Addis | ATE/8574/14

## Technologies

- **Java 17** - Programming language
- **Spring Boot 3** - Application framework
- **Maven** - Build and dependency management
- **Spring Data JPA** - Data persistence abstraction
- **Hibernate** - ORM framework
- **H2 Database** - In-memory database for development
- **Spring Web** - RESTful API development
- **Spring Validation** - Input validation
- **Lombok** - Boilerplate code reduction (optional)
- **JUnit 5** - Unit testing framework
- **Mockito** - Mocking framework for tests
- **Spring Boot Test** - Integration testing support

## Prerequisites

- Java 17 or higher
- Maven 3.8+

## Project Structure

```
src/
├── main/
│   ├── java/com/ctbe/yeabsira/productservice/
│   │   ├── ProductServiceApplication.java     # Main application class
│   │   ├── controller/                         # REST controllers
│   │   ├── dto/                                # Data transfer objects
│   │   ├── exception/                          # Exception handling
│   │   ├── mapper/                             # Object mappers
│   │   ├── model/                              # JPA entities
│   │   ├── repository/                         # Spring Data JPA repositories
│   │   └── service/                            # Business logic
│   └── resources/
│       └── application.properties              # Application configuration
└── test/
    └── java/com/ctbe/yeabsira/productservice/  # Unit and integration tests
```

## API Endpoints

All endpoints are prefixed with `/api/v1/products`.

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/v1/products` | Create a new product |
| GET | `/api/v1/products` | Get all products |
| GET | `/api/v1/products/{id}` | Get product by ID |
| GET | `/api/v1/products/sku/{sku}` | Get product by SKU |
| GET | `/api/v1/products/category/{category}` | Get products by category |
| GET | `/api/v1/products/active` | Get all active products |
| GET | `/api/v1/products/search?name={name}` | Search products by name |
| PUT | `/api/v1/products/{id}` | Update a product |
| DELETE | `/api/v1/products/{id}` | Delete a product |

## Postman Collection

A Postman collection for testing the API endpoints is available in the `postman/` directory:

- **[Product Service Lab 2 Collection](postman/product-service-lab2.json)**

You can import this collection into Postman to quickly test all available endpoints with pre-configured requests and sample data.

## Building and Running

### Using Maven Wrapper

```bash
# Run tests
./mvnw test

# Run the application
./mvnw spring-boot:run

# Build the project
./mvnw clean package
```

### Using System Maven

```bash
# Run tests
mvn test

# Run the application
mvn spring-boot:run

# Build the project
mvn clean package
```

### Running the JAR

```bash
java -jar target/product-service-0.0.1-SNAPSHOT.jar
```

## H2 Database Console

The H2 database console is available at:

```
http://localhost:8080/h2-console
```

**JDBC URL:** `jdbc:h2:mem:productdb`  
**Username:** `sa`  
**Password:** (leave empty)

## Example Requests

### Create a Product

```bash
curl -X POST http://localhost:8080/api/v1/products \
  -H "Content-Type: application/json" \
  -d '{
    "sku": "LAPTOP-001",
    "name": "Gaming Laptop",
    "description": "High-performance gaming laptop",
    "price": 1299.99,
    "stockQuantity": 50,
    "category": "Electronics",
    "active": true
  }'
```

### Get All Products

```bash
curl http://localhost:8080/api/v1/products
```

### Get Product by ID

```bash
curl http://localhost:8080/api/v1/products/1
```

### Get Product by SKU

```bash
curl http://localhost:8080/api/v1/products/sku/LAP001
```

### Get Products by Category

```bash
curl http://localhost:8080/api/v1/products/category/Electronics
```

### Get Active Products

```bash
curl http://localhost:8080/api/v1/products/active
```

### Search Products by Name

```bash
curl http://localhost:8080/api/v1/products/search?name=laptop
```

### Update a Product

```bash
curl -X PUT http://localhost:8080/api/v1/products/1 \
  -H "Content-Type: application/json" \
  -d '{
    "sku": "LAPTOP-002",
    "name": "Updated Laptop",
    "description": "Updated description",
    "price": 1399.99,
    "stockQuantity": 45,
    "category": "Electronics",
    "active": true
  }'
```

### Delete a Product

```bash
curl -X DELETE http://localhost:8080/api/v1/products/1
```

## Configuration

Key application properties in `application.properties`:

- `server.port`: Server port (default: 8080)
- `spring.datasource.url`: H2 database URL
- `spring.jpa.hibernate.ddl-auto`: DDL generation strategy (update)
- `management.endpoints.web.exposure.include`: Exposed actuator endpoints

## Testing

The project includes comprehensive tests:

- **Controller Tests**: Mock MVC tests for REST endpoints
- **Service Tests**: Business logic tests with mocked repositories
- **Repository Tests**: Spring Data JPA repository tests (if added)

Run all tests:

```bash
mvn test
```

## CI/CD

GitHub Actions workflow is configured for:

- Building the project with Maven
- Running all tests
- Code quality checks (if configured)

The CI badge at the top of this file shows the current build status.

See `.github/workflows/ci.yml` for details.
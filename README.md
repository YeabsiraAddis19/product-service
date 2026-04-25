# Product Service

A Spring Boot REST API for managing products with H2 in-memory database.

## Features

- Create, Read, Update, Delete (CRUD) operations for products
- Search products by name, category, or SKU
- Filter active products
- Input validation
- Global exception handling
- H2 in-memory database
- JPA/Hibernate for data persistence
- RESTful API design

## Prerequisites

- Java 17 or higher
- Maven 3.8+

## Project Structure

```
src/
├── main/
│   ├── java/com/ctbe/productservice/
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
    └── java/com/ctbe/productservice/  # Unit and integration tests
```

## API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/products` | Create a new product |
| GET | `/api/products` | Get all products |
| GET | `/api/products/{id}` | Get product by ID |
| GET | `/api/products/sku/{sku}` | Get product by SKU |
| GET | `/api/products/category/{category}` | Get products by category |
| GET | `/api/products/active` | Get all active products |
| GET | `/api/products/search?name={name}` | Search products by name |
| PUT | `/api/products/{id}` | Update a product |
| DELETE | `/api/products/{id}` | Delete a product |

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
curl -X POST http://localhost:8080/api/products \
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
curl http://localhost:8080/api/products
```

### Get Product by ID

```bash
curl http://localhost:8080/api/products/1
```

### Search Products by Name

```bash
curl http://localhost:8080/api/products/search?name=laptop
```

### Update a Product

```bash
curl -X PUT http://localhost:8080/api/products/1 \
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
curl -X DELETE http://localhost:8080/api/products/1
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

See `.github/workflows/ci.yml` for details.
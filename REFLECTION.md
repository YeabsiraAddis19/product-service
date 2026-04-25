# Reflection – Yeabsira Addis (ATE/8574/14)

## Lab 1 – Hello Enterprise World

### 1. What is the purpose of the Service layer? Why should a Controller never call a Repository directly?

The Service layer is where all the business logic lives. It decides what to do with the data and coordinates calls to the Repository. The Controller should only worry about HTTP stuff (like getting the request, validating it, and sending back a response). If the Controller called the Repository directly, the business logic would be scattered and hard to reuse or test. Also, the Service layer can combine multiple Repository calls (e.g., get user, then update product) – that should never happen inside a Controller. So keeping the Controller “thin” and the Service “fat” makes the app easier to maintain.

### 2. Why do enterprise systems use layered architecture? What problems does it solve compared to writing all logic in one class?

Layered architecture separates concerns: Controller, Service, Repository, Entity. That way, each layer has one clear job. If you put everything in one big class, changing one tiny thing might break everything else. Also, layers make testing easier – you can mock the Repository when testing the Service, or mock the Service when testing the Controller. Without layers, you end up with “spaghetti code” that is hard to read, debug, and extend. Enterprises need reliable software that many developers can work on, so layers keep the code organised.

### 3. What advantages does Spring Data JPA provide over writing plain JDBC code? Give at least two specific examples.

First, with Spring Data JPA you don’t have to write SQL queries manually. For example, just by declaring `interface ProductRepository extends JpaRepository<Product, Long>`, you automatically get methods like `findAll()`, `save()`, `deleteById()` – no JDBC boilerplate. Second, derived query methods: writing `List<Product> findByNameContainingIgnoreCase(String keyword)` makes Spring generate the SQL for you. With plain JDBC you would have to write the SQL, map ResultSet to objects, handle connections, and close resources – JPA does all that.

### 4. In your unit test, you used a mock for ProductRepository. Why is it important to isolate the class under test from its real dependencies?

If we used the real ProductRepository, our test would actually hit the database. That makes the test slow and also the result would depend on what data is currently in the database. With a mock, we can control exactly what the repository returns (e.g., `when(repo.findById(1L)).thenReturn(Optional.of(product))`). That means we are only testing the Service logic, not the database or JPA. Also, if the database fails, the unit test would fail for the wrong reason. Isolating with mocks keeps tests fast and reliable.

### 5. What would happen if spring.jpa.hibernate.ddl-auto were set to update instead of create-drop? When would you use each setting?

- `create-drop` drops all tables when the app stops and recreates them at startup. This is great for development and tests because you always start with a clean schema. But you lose all data.
- `update` keeps existing tables and only adds new columns or constraints if the entity changes. It does not drop tables or delete data. You would use `update` in a development environment where you don't want to lose test data every restart, but never in production because it can cause unexpected schema changes. In production, people usually use a migration tool like Flyway or set it to `validate`.

---

## Lab 2 – RESTful Product Catalogue API

### 1. Why should the ProductRequest DTO carry the @Valid annotations instead of the Product entity itself?

The Product entity is the internal “database” object, while the DTO is what the client sends over the wire. Validation rules for an API can be different from database constraints. For example, the client maybe should not send an `id` field, but the entity has an `id`. Putting validation on the DTO keeps the entity clean and focused on persistence. Also, the same entity might be reused in different contexts (e.g., admin API vs public API) that have different validation rules. So it’s better to keep validation on the DTO and leave the entity as a pure JPA mapping.

### 2. What is the purpose of the Location header returned on a POST 201 Created response, and which HTTP specification mandates it?

The Location header tells the client where the newly created resource can be found. For example, after `POST /api/v1/products`, the response might have `Location: /api/v1/products/4`. The client can then immediately fetch that resource with a GET request. This is required by the HTTP/1.1 specification (RFC 7231, and more generally RFC 9110). It’s a standard way to make APIs more discoverable and RESTful.

### 3. Explain the difference between @ControllerAdvice and @ExceptionHandler. When would you use each?

- `@ExceptionHandler` is used inside a single controller to handle exceptions thrown by that controller’s methods. For example, you can put it in `ProductController` to handle `ResourceNotFoundException` only for product endpoints.
- `@ControllerAdvice` (or `@RestControllerAdvice`) is a global component that applies to all controllers. It centralises exception handling so you don’t have to repeat the same `@ExceptionHandler` in every controller.

Use `@ExceptionHandler` for controller‑specific errors. Use `@RestControllerAdvice` for cross‑cutting concerns like validation errors, 404 handling, or generic exceptions that can happen anywhere.

### 4. In your MockMvc tests you used @Transactional on the test class. What would happen to the database state between tests if you removed this annotation?

Without `@Transactional`, each test method would commit its changes to the database. So after the first test saves a product, the second test would see that product. That could cause tests to interfere with each other – for example, a test that expects exactly three products might fail because another test added extra products. With `@Transactional`, Spring wraps each test in a transaction that rolls back at the end, so the database goes back to its original state before each test. This makes tests independent and repeatable.

### 5. What does RFC 9457 define, and why does following it produce better APIs than returning a generic {error: 'something went wrong'} message?

RFC 9457 defines the “Problem Details for HTTP APIs” format. It standardises how error responses should look, including fields like `type`, `title`, `status`, `detail`, and `instance`. By following it, every API returns errors in the same structure, making it easier for clients to handle them programmatically. A generic `{"error":"something went wrong"}` does not tell the client what exactly failed, what the status code is, or how to fix it. Using ProblemDetail gives rich, consistent error information.

### 6. What is the difference between an integration test (MockMvc) and a unit test (Mockito)? When is each approach preferable?

- **Unit test (Mockito)** tests a single class in isolation. We mock all its dependencies. They are very fast and help catch logic errors early. Prefer them for testing business logic in the Service layer.
- **Integration test (MockMvc)** tests the whole Spring MVC stack including controllers, validation, exception handling, serialisation, and even the database (if not mocked). They are slower but give confidence that all parts work together. Use them to test endpoints, HTTP status codes, and JSON responses.

A good test suite has many unit tests (fast) and a smaller number of integration tests (to verify end‑to‑end behaviour).
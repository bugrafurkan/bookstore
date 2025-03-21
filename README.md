# Book Store API

The Book Store API is a RESTful service for managing books and handling sales operations. This project implements CRUD operations for books, as well as sales transactions (including sale creation, cancellation with stock restoration, and filtering sales by buyer email). The project is built using a layered architecture with SOLID principles in mind and includes global exception handling to ensure consistent HTTP status codes for errors.

## Features

- **Book Management**
  - Create a new book
  - Retrieve a single book by ID
  - List all books
  - Update book information
  - Delete a book

- **Sales Operations**
  - Create a new sale
  - Cancel a sale (restores the deducted stock)
  - Retrieve sales by buyer email
  - Calculate total earnings

- **Global Exception Handling**
  - `ResourceNotFoundException` returns HTTP 404 Not Found
  - `InsufficientStockException` returns HTTP 400 Bad Request
  - All other exceptions return HTTP 500 Internal Server Error

- **Other Features**
  - Transactional update and cancellation operations ensure data consistency.
  - Unit tests using JUnit 5 and Mockito verify service and controller logic.
  - Docker integration allows the application to run in a container.
  - Uses H2 in-memory database for minimal configuration during development.

## Technologies

- **Java 11/17**
- **Spring Boot** (REST API, Dependency Injection, Transaction Management)
- **Spring Data JPA** (database operations)
- **H2 Database** (in-memory database)
- **JUnit 5 & Mockito** (testing framework)
- **Maven** (project management and dependency resolution)
- **Docker** (containerization)

## Project Structure

src └── main ├── java │ └── com.example.bookstore │ ├── controller │ │ ├── BookController.java │ │ └── SaleController.java │ ├── dto │ │ ├── BookDTO.java │ │ └── SaleDTO.java │ ├── exception │ │ ├── GlobalExceptionHandler.java │ │ ├── ErrorResponse.java │ │ ├── InsufficientStockException.java │ │ └── ResourceNotFoundException.java │ ├── model │ │ ├── Book.java │ │ └── Sale.java │ ├── repository │ │ ├── BookRepository.java │ │ └── SaleRepository.java │ └── service │ ├── BookService.java │ ├── BookServiceImpl.java │ ├── SaleService.java │ └── SaleServiceImpl.java └── resources ├── application.properties └── data.sql (optional: initial data)


## Setup and Running

### Requirements

- JDK 11 or 17
- Maven
- (Optional) Docker

### Running Locally

1. **Clone the Repository:**

   ```bash
   git clone https://github.com/yourusername/bookstore-api.git
   cd bookstore-api

    Build the Project and Run Tests:

mvn clean install

Start the Application:

    mvn spring-boot:run

    Test the API with Postman or your browser:
        The application runs on http://localhost:8080 by default.

API Endpoint Documentation
Book Endpoints

    Add a New Book
        Endpoint: POST /api/v1/books
        Request Body:

        {
          "title": "Clean Code",
          "author": "Robert C. Martin",
          "price": 49.99,
          "isbn": "978-0132350884",
          "publishDate": "2020-01-15",
          "description": "A book about writing cleaner code.",
          "stock": 10
        }

        Response: HTTP 201 Created

    Get a Book by ID
        Endpoint: GET /api/v1/books/{id}
        Response: HTTP 200 OK with book details

    List All Books
        Endpoint: GET /api/v1/books
        Response: HTTP 200 OK with a list of books

    Update a Book
        Endpoint: PUT /api/v1/books/{id}
        Request Body: Updated book details
        Response: HTTP 200 OK with updated book details

    Delete a Book
        Endpoint: DELETE /api/v1/books/{id}
        Response: HTTP 204 No Content

Sales Endpoints

    Create a New Sale
        Endpoint: POST /api/v1/sales
        Request Body:

        {
          "bookId": 1,
          "buyerName": "John Doe",
          "buyerEmail": "john.doe@example.com",
          "quantity": 2
        }

        Response: HTTP 201 Created

    Cancel a Sale
        Endpoint: PUT /api/v1/sales/{id}/cancel
        Response: HTTP 200 OK
        (On cancellation, the deducted stock is restored)

    List All Sales
        Endpoint: GET /api/v1/sales
        Response: HTTP 200 OK with a list of sales

    Find Sales by Buyer Email
        Endpoint: GET /api/v1/sales/search?buyerMail=john.doe@example.com
        Response: HTTP 200 OK with the list of sales for the specified email

    Calculate Total Earnings
        Endpoint: GET /api/v1/sales/totalEarnings
        Response: HTTP 200 OK with total earnings (double value)

Global Exception Handling

The Global Exception Handler ensures that exceptions thrown in the service layer are caught and returned with consistent HTTP status codes:

    ResourceNotFoundException → 404 Not Found
    InsufficientStockException → 400 Bad Request
    All other exceptions → 500 Internal Server Error

Example error response:

{
  "status": 404,
  "message": "Book not found: 1"
}

Testing

Unit tests have been written using JUnit 5 and Mockito to verify the business logic in the service and controller layers. To run tests:

mvn test

Future Improvements

    Swagger/OpenAPI:
    Integrate Swagger for automatic API documentation.
    Security:
    Add JWT or Basic Authentication for API security.
    Database:
    Transition from H2 in-memory to a persistent database for production.
    Logging:
    Enhance logging to support better error tracking and monitoring.

Conclusion

This project provides a comprehensive API for managing books and handling sales transactions. With a layered architecture, global exception handling, and robust test coverage, the application is both extensible and maintainable. Feel free to contribute or contact the maintainers with any questions or feedback.

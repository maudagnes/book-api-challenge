# Book API Challenge

REST API for storing and retrieving books using Play Framework (Java).

## Technologies
- Play Framework (Java)
- H2 Database (in-memory)
- JPA/Hibernate (ORM)
- JUnit 5

## Setup Instructions

### Prerequisites
- Java 21
- SBT 1.9+

### Running the Application

### Installation Steps

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd book-api-challenge
   ```

2. **Run the application**
   ```bash
   sbt run
   ```
   
   The application will start on `http://localhost:9000`

### Running Tests

Run all tests:
```bash
sbt test
```

## API Endpoints

### POST /books
Create a new book.

**Request:**
```bash
POST http://localhost:9000/books
Content-Type: application/json
```

**Request Body:**
```json
{
  "isbn": "978-0-123456-78-9",
  "title": "Example Book",
  "subtitle": "A Subtitle (optional)",
  "copyrightYear": "2024",
  "status": "available"
}
```

### Example using cURL

**Create a book:**
```bash
curl -X POST http://localhost:9000/books \
  -H "Content-Type: application/json" \
  -d '{
    "isbn": "978-0-123456-78-9",
    "title": "The Great Book",
    "subtitle": "An Amazing Story",
    "copyrightYear": "2024",
    "status": "available"
  }'
```
### GET /books
Retrieve all books from the database.

**Request:**
```bash
GET http://localhost:9000/books
```
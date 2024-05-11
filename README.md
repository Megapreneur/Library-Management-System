# maids.cc-task
A demo Library Management System for maids.cc

# Library Management System using Spring Boot

The Library Management System is a comprehensive web application developed using Spring Boot, aimed at providing users with a feature-rich platform to efficiently manage and organize books in the library, patron details, book borrowing record and book returning records. This README provides an overview of the system, installation instructions and other details about the project.

## Table of Contents

- [Features](#features)
- [Technologies Used](#technologies-used)
- [Getting Started](#getting-started)
    - [Prerequisites Requirement](#prerequisites-requirement)
    - [Installation](#installation)
- [API Endpoints](#api-endpoints)
- [Documentation](#swagger-ui-docs)


## Features

This Library Management System is equipped with robust reporting features that can provide valuable insights into book inventory, borrowing patterns, patron engagement, and other relevant metrics.


- **Book Management**: Store and manage books, including their titles, authors, publication years, ISBNs, and other relevant details.
- **Patron Management**: Manage patrons' information, such as their names, contact details, and borrowing history.
- **Book Borrowing Management**: Track and manage book borrowings, including recording borrowing and return dates for each patron-book transaction.
- **User Access and Security**: Control user access levels and permissions to ensure data security and confidentiality.

## Technologies Used

The Library Management System leverages modern technologies to deliver a robust and efficient experience:

- **Java**: The core programming language for developing the application logic.
- **Spring Boot**: A powerful framework for building robust and scalable applications.
- **Spring Data JPA**: Provides data access and manipulation capabilities using the Java Persistence API.
- **Spring Web**: Facilitates the creation of web APIs and interfaces.
- **Spring Security**: Ensures secure authentication and authorization within the application, guarding against unauthorized access and protecting sensitive data.
- **JSON Web Token (JWT)**: Enables stateless authentication by securely transmitting user information as JSON objects, facilitating secure communication between the client and server.
- **MSQL**: A popular relational database management system used for storing and retrieving structured data efficiently.
- **Maven**: Manages project dependencies and provides a structured build process.
- **Caffeine Cache**: A high-performance, near-zero-latency caching library for Java 8.
- **Git**: Version control for collaborative development.

## Getting Started

### Prerequisites Requirement

Before getting started, ensure you have the following components installed:

1. **This project was built using JDK 17, you would need JDK 17 installed on you local machine.**

- [Java Development Kit (JDK 17)](https://www.oracle.com/java/technologies/javase-downloads.html)
- [Maven](https://maven.apache.org/download.cgi)


## Build and Run the Application:

Execute the following command to build and run the application:

````bash
mvn clean package install
mvn spring-boot:run
````


### Installation

1. **Clone the Repository:**

   ```bash
   git clone https://github.com/Megapreneur/Library-Management-System
   cd Library-Management-System
   ```

2. **Configure the Database:**

   Modify the `src/main/resources/application.properties` file to include your database connection details:

   ```properties
   spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
   spring.datasource.url=your_database_jdbc_url
   spring.datasource.username=your_database_username
   spring.datasource.password=your_database_password
   ```

3. **Token Requirements:**

   Modify the `src/main/resources/application.properties` file to include your JWT token:
   
    ```properties
   library.app.SecretKey=your_JWT_token
    ```


## API Endpoints

The Sales Management System offers the following API endpoints:

- User's Registration: `POST /api/register`
- User's login: `POST /api/login`
- Add a new book to the library: `POST /api/books`
- Retrieve details of a specific book by ID: `GET /api/books/{id}`
- Update an existing book's information: `PUT /api/books/{id}`
- Retrieve a list of all books: `GET /api/books`
- Remove a book from the library: `DELETE /api/books/{id}`
- Add a new patron to the system: `POST /api/patrons`
- Retrieve details of a specific patron by ID: `GET /api/patrons/{id}`
- Update an existing patron's information: `PUT /api/patrons/{id}`
- Retrieve a list of all patrons: `GET /api/patrons`
- Remove a patron from the system: `DELETE /api/patrons/{id}`
- Allow a patron to borrow a book: `POST /api/borrow/{bookId}/patron/{patronId}`
- Record the return of a borrowed book by a patron: `PUT /api/return/{bookId}/patron/{patronId}`


## Documentation
After starting the application, here is the swagger-ui doc link

swagger-ui:  http://localhost:8080/swagger-ui/index.html#/

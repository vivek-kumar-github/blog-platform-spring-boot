# Blog Platform

A full-stack web application built with Spring Boot that provides a platform for users to read blog posts and leave comments. The application includes a secure, role-based admin dashboard for managing posts.

## Problem Statement

This project serves as a comprehensive example of building a full-stack application using the Spring Boot ecosystem. The core functionality allows administrators to perform CRUD (Create, Read, Update, Delete) operations on blog posts, while registered users can engage with the content by commenting. Guests can browse and read all published posts. The goal is to create a portfolio-ready application that demonstrates best practices in backend development, security, and real-world feature implementation.

## Features

- **Public-Facing Blog:**
  - Guests can view a paginated list of all published posts.
  - Guests can view the full content and comments for any individual post.
  - A dynamic search feature allows users to find posts by title.
- **User Authentication & Authorization:**
  - Users can register for an account.
  - Registered users can log in and log out.
  - Registered users can post comments on blog posts.
- **Admin Dashboard (Role-Based Security):**
  - A secure `/admin/**` area accessible only to users with the `ROLE_ADMIN`.
  - Full CRUD functionality for blog posts (Create, view list, edit, delete).
- **Robust Backend:**
  - Server-side validation to ensure data integrity for posts and comments.
  - Global exception handling for a user-friendly experience (custom 404 and generic error pages).

## Technology Stack

- **Backend:**
  - Java 21
  - Spring Boot 3.x
  - Spring MVC (for web controllers)
  - Spring Data JPA (for database interaction)
  - Spring Security (for authentication & authorization)
  - Hibernate (as the JPA provider)
- **Frontend:**
  - Thymeleaf (for server-side template rendering)
  - HTML5 & CSS3 {Bootstrap}
- **Database:**
  - H2 Database (for local development)
  - MySQL (for production)
- **Build & Deployment:**
  - Apache Maven (for dependency management and build)
  - Docker (for containerization)

---

## Prerequisites

- Java JDK 21 or later
- Apache Maven 3.6 or later
- Docker (for running in production mode)
- A running MySQL instance (for running in production mode)

---

## How to Run

There are two primary ways to run this application:

### 1. Running Locally (Development Mode)

This method uses the embedded H2 in-memory database, which is perfect for quick development and testing. The database is reset every time the application restarts.

1.  **Clone the repository:**
    ```bash
    git clone https://github.com/vivek-kumar-github/blog-platform-spring-boot.git
    cd blog-platform-spring-boot
    ```

2.  **Run the application using the Maven wrapper:**
    ```bash
    # On macOS/Linux
    ./mvnw spring-boot:run

    # On Windows
    mvnw.cmd spring-boot:run
    ```

3.  The application will start on `http://localhost:3000`.

### 2. Running with Docker (Production Mode)

This method uses Docker to run the application in a container and connects to a persistent MySQL database. This is the recommended way to simulate a production deployment.

1.  **Set up MySQL:**
    - Ensure you have a running MySQL server.
    - Create a database for the application (e.g., blog_prod_db).
    - Create a MySQL user with appropriate privileges on that database.

2.  **Configure the application:**
    - Open `src/main/resources/application-prod.properties`.
    - Update `spring.datasource.url`, `spring.datasource.username`, and `spring.datasource.password` to match your MySQL setup.

3.  **Package the application:**
    - Use the Maven wrapper to build the executable `.jar` file.
    ```bash
    # On macOS/Linux
    ./mvnw clean package

    # On Windows
    mvnw.cmd clean package
    ```

4.  **Build the Docker image:**
    - Use the provided `Dockerfile` to create a Docker image.
    ```bash
    docker build -t blog-platform-spring-boot .
    ```

5.  **Run the Docker container:**
    - Run the image as a container, mapping the port and activating the `prod` profile.
    ```bash
    docker run -p 3000:3000 -e "SPRING_PROFILES_ACTIVE=prod" --name blog-app blog-platform-spring-boot
    ```
    - **`-p 3000:3000`**: Maps port 3000 on your host machine to port 3000 in the container.
    - **`-e "SPRING_PROFILES_ACTIVE=prod"`**: Sets the environment variable to activate the `prod` Spring profile, which makes the application use your `application-prod.properties` file and connect to MySQL.
    - **`--name blog-app`**: Gives your container a memorable name.

6.  The application will be accessible at `http://localhost:3000`. The data will now persist in your MySQL database between restarts.
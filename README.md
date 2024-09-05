# User Management Application

## Overview

This is a Spring Boot application for managing user registration, authentication, and role-based access control. The project includes:
- **User Registration** with input validation.
- **Authentication and Authorization** using Spring Security with two roles: `USER` and `ADMIN`.
- **CRUD Operations** for managing user data.
- **H2 In-Memory Database** for simplicity in development.
- **Global Exception Handling** for managing errors and exceptions.
- **Custom Validation Annotation** for ensuring password strength.

## Table of Contents
- [Features](#features)
- [Technologies Used](#technologies-used)
- [Setup](#setup)
- [How to Run the Application](#how-to-run-the-application)
- [Testing the Application](#testing-the-application)
- [Endpoints](#endpoints)
- [Exception Handling](#exception-handling)
- [Future Enhancements](#future-enhancements)

---

## Features
- **User Registration**: Users can sign up with validation for input fields such as username, email, and password.
- **Custom Password Validation**: Ensures that the password contains at least one uppercase letter, one lowercase letter, one number, and is at least 8 characters long.
- **Authentication & Authorization**: Role-based access control with `USER` and `ADMIN` roles.
- **CRUD Operations**: Admins can create, view, update, and delete users.
- **Database Integration**: Uses H2 in-memory database for easy setup and testing.
- **Global Exception Handling**: Catches common exceptions and returns meaningful error messages.

---

## Technologies Used
- **Spring Boot 3.3.3**
- **Spring Security**
- **Spring Data JPA**
- **H2 Database** (In-Memory)
- **Maven** (For dependency management)
- **Java 22** (Ensure you have Java 22 installed)

---

## Setup

### Prerequisites
Make sure you have the following installed on your machine:
- **Java 22**
- **Maven** (https://maven.apache.org/install.html)

### Clone the Repository
```bash
git clone https://github.com/M3SE/UserApp.git
```


## Testing the Application

Test the application using **Postman**. You will test user registration, viewing user details, updating user details, and admin functionalities.

### 1. User Registration (Open Endpoint)
This endpoint allows anyone to register a new user.

- **Method**: `POST`
- **URL**: `http://localhost:8080/api/users/register`
- **Body (JSON)**:
   ```json
   {
     "username": "john",
     "email": "john@example.com",
     "password": "Password123"
   }
   ```

#### Using Postman:
1. Open Postman.
2. Set the method to `POST`.
3. Use the URL: `http://localhost:8080/api/users/register`.
4. Go to the **Body** tab, select **raw**, and choose **JSON** format.
5. Paste the JSON payload provided above.
6. Click **Send**.

**Expected Response**: The registered user details in JSON format.

---

### 2. Get Current User Details (Authenticated `USER`)
This endpoint allows a logged-in user to view their own profile details.

- **Method**: `GET`
- **URL**: `http://localhost:8080/api/users/me`
- **Authorization**: Basic Auth with the user's credentials.

#### Using Postman:
1. Open Postman.
2. Set the method to `GET`.
3. Use the URL: `http://localhost:8080/api/users/me`.
4. Go to the **Authorization** tab:
    - Select **Basic Auth**.
    - Enter the credentials for the logged-in user (e.g., `john` as the username and `Password123` as the password).
5. Click **Send**.

**Expected Response**: The logged-in user's details in JSON format.

---

### 3. Update Current User Details (Authenticated `USER`)
This endpoint allows a logged-in user to update their profile details.

- **Method**: `PUT`
- **URL**: `http://localhost:8080/api/users/me`
- **Authorization**: Basic Auth with the user's credentials.
- **Body (JSON)**:
   ```json
   {
     "username": "newUsername",
     "email": "newemail@example.com",
     "password": "NewPassword123"
   }
   ```

#### Using Postman:
1. Open Postman.
2. Set the method to `PUT`.
3. Use the URL: `http://localhost:8080/api/users/me`.
4. Go to the **Authorization** tab:
    - Select **Basic Auth**.
    - Enter the credentials for the logged-in user (e.g., `john` as the username and `Password123` as the password).
5. Go to the **Body** tab, select **raw**, and choose **JSON** format.
6. Paste the JSON payload provided above.
7. Click **Send**.

**Expected Response**: The updated user details in JSON format.

---

### 4. Get All Users (Admin Only)
This endpoint allows an `ADMIN` user to retrieve all registered users.

- **Method**: `GET`
- **URL**: `http://localhost:8080/api/users`
- **Authorization**: Basic Auth with the admin credentials.

#### Using Postman:
1. Open Postman.
2. Set the method to `GET`.
3. Use the URL: `http://localhost:8080/api/users`.
4. Go to the **Authorization** tab:
    - Select **Basic Auth**.
    - Enter the credentials for the admin (e.g., `admin` as the username and `admin123` as the password).
5. Click **Send**.

**Expected Response**: A list of all registered users in JSON format.

---

### 5. Delete a User (Admin Only)
This endpoint allows an `ADMIN` to delete a user by their ID.

- **Method**: `DELETE`
- **URL**: `http://localhost:8080/api/users/{id}` (Replace `{id}` with the actual user ID)
- **Authorization**: Basic Auth with the admin credentials.

#### Using Postman:
1. Open Postman.
2. Set the method to `DELETE`.
3. Use the URL: `http://localhost:8080/api/users/1` (replace `1` with the actual user ID you want to delete).
4. Go to the **Authorization** tab:
    - Select **Basic Auth**.
    - Enter the credentials for the admin (e.g., `admin` as the username and `admin123` as the password).
5. Click **Send**.

**Expected Response**: A **200 OK** response confirming the user has been deleted.

Here's the full README.md content:

Markdown

# E-commerce Application (Spring Boot)

This is a robust E-commerce web application built using Spring Boot, focusing on core functionalities like user authentication, product management, shopping cart, wishlist, and a simulated order/payment flow.

## Table of Contents

-   [Features](#features)
-   [Technologies Used](#technologies-used)
-   [Prerequisites](#prerequisites)
-   [Setup and Installation](#setup-and-installation)
    -   [1. Clone the Repository](#1-clone-the-repository)
    -   [2. Database Setup](#2-database-setup)
    -   [3. Configure Application Properties](#3-configure-application-properties)
    -   [4. Build the Project](#4-build-the-project)
    -   [5. Run the Application](#5-run-the-application)
-   [Usage](#usage)
    -   [User Authentication](#user-authentication)
    -   [Product Management](#product-management)
    -   [Shopping Cart](#shopping-cart)
    -   [Wishlist](#wishlist)
    -   [Order & Payment](#order--payment)
    -   [Order History](#order-history)
-   [Project Structure](#project-structure)
-   [Future Improvements](#future-improvements)
-   [Contributing](#contributing)
-   [License](#license)

## Features

* **User Authentication & Authorization:** Secure user registration, login, and role-based access using Spring Security.
* **Product Management:** Add, view, and delete products with details like name, description, price, quantity, category, and images.
* **Category Filtering:** Browse products by category using a sidebar filter on the home page.
* **Shopping Cart:** Add products to cart, view cart items, increase/decrease quantities, and remove items.
* **Wishlist:** Add products to a personal wishlist, view wishlist items, and remove them.
* **Simulated Order Flow:**
    * Proceed from cart to a simulated payment gateway.
    * Automatic stock reduction upon order creation.
    * Simulated payment confirmation and order completion.
* **Order History:** View all past orders with details of purchased items, quantities, and total amounts.
* **Image Handling:** Upload and display product images.
* **Thymeleaf Templates:** Server-side rendered views for a traditional web application experience.
* **Spring Data JPA:** Simplifies database interactions with repositories.

## Technologies Used

* **Java 8**
* **Spring Boot**: 3.x.x
* **Spring Security**: For authentication and authorization.
* **Maven**: Build automation tool.
* **MySQL**: Relational database for persistence.
* **Spring Data JPA**: For database interaction and ORM.
* **Hibernate**: JPA Implementation.
* **Thymeleaf**: Templating engine for dynamic HTML views.
* **HTML5 / CSS3**: Frontend structure and styling.
* **Lombok (Optional but Recommended)**: For reducing boilerplate code (getters, setters, constructors).

## Prerequisites

Before you begin, ensure you have the following installed on your system:

* **Java Development Kit (JDK) 8**
* **Maven 3.6+**
* **MySQL Server** (and a MySQL client like MySQL Workbench or DBeaver for database setup)
* An IDE like **IntelliJ IDEA** or **Eclipse STS** (Spring Tool Suite) is recommended.

## Setup and Installation

Follow these steps to get the project up and running on your local machine.

### 1. Clone the Repository

```bash
git clone <your-repository-url>
cd E-commerce
Replace <your-repository-url> with the actual URL of your Git repository.

2. Database Setup
Create a MySQL Database:
Open your MySQL client and execute the following SQL command to create a new database for your application:

SQL

CREATE DATABASE IF NOT EXISTS `ecommerce_db`;
Create a MySQL User (Optional but Recommended):
You might want to create a dedicated user for your application with specific privileges:

SQL

CREATE USER 'ecommerce_user'@'localhost' IDENTIFIED BY 'your_password';
GRANT ALL PRIVILEGES ON `ecommerce_db`.* TO 'ecommerce_user'@'localhost';
FLUSH PRIVILEGES;
Replace 'your_password' with a strong password.

3. Configure Application Properties
Navigate to src/main/resources/application.properties and update the database connection details.

Properties

# Database Configuration (for MySQL)
spring.datasource.url=jdbc:mysql://localhost:3306/ecommerce_db?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
spring.datasource.username=ecommerce_user # Or your MySQL username (e.g., root)
spring.datasource.password=your_password # Or your MySQL password

# Hibernate/JPA Configuration
spring.jpa.hibernate.ddl-auto=update # Use 'update' to automatically create/update tables. For production, consider 'none' or 'validate'.
spring.jpa.show-sql=true # Log SQL queries
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect

# Image Upload Directory
# This will create an 'uploads/images' folder relative to your project's root directory.
# Ensure your application has write permissions to this directory.
# For production, consider a more robust external storage solution.
# No direct property needed here, it's configured in WebConfig.java
# The path is derived from System.getProperty("user.dir") + "/uploads/images/"

# Server Port (Optional)
server.port=8080
Important: Replace your_password with the actual password for your MySQL user.

4. Build the Project
Open your terminal or command prompt in the project's root directory (E-commerce) and run the Maven clean and install command:

Bash

mvn clean install
This will compile the code, run tests, and package the application into a .jar file in the target/ directory.

5. Run the Application
You can run the application using Maven Spring Boot plugin or by executing the generated JAR file:

Using Maven:

Bash

mvn spring-boot:run
Using JAR file:

Bash

java -jar target/E-commerce-0.0.1-SNAPSHOT.jar # Adjust the version number if different
Once the application starts successfully, you can access it in your web browser at:
http://localhost:8080 (or your configured port).

Usage
User Authentication
Register: Navigate to /register to create a new user account.

Login: Access /login to log in with your credentials.

Product Management
View Products: The home page (/ or /home) displays all available products.

Add Product: Click on "Add Product" (usually available after login) to add new products. Images will be stored in uploads/images/ within your project directory.

Remove Product: Products can be removed directly from the home page (requires proper authorization, currently anyone can remove after login).

Shopping Cart
Add to Cart: Click "Add to Cart" button on product cards.

View Cart: Click "View Cart" link.

Manage Quantity: Use '+' and '-' buttons to increase/decrease item quantities in the cart.

Remove from Cart: Click "Remove" button for a specific item.

Cart Total: The cart page displays the total amount of all items.

Wishlist
Add to Wishlist: Click "Add to Wishlist" button on product cards.

View Wishlist: Click "View Wishlist" link.

Remove from Wishlist: Click "Remove" button for a specific item.

Add to Cart from Wishlist: Move items from wishlist to cart directly.

Order & Payment (Simulated)
Proceed to Buy: From the cart page, click "Proceed to Buy".

This will initiate an order, reduce product stock, clear your cart, and redirect to a simulated payment gateway.

Click "Confirm Payment (Simulated)" on the payment page to complete the order.

You will be redirected to an Order Successful page.

Order History
View Past Orders: Click "Order History" link to see a list of all your placed orders with details.

Project Structure
src/main/java/com/ecommerce/E_commerce/
├── config/                  # Spring Security and Web MVC configurations
│   ├── SecurityConfig.java
│   └── WebConfig.java
├── controller/              # Handles HTTP requests and returns views/data
│   ├── AuthController.java
│   ├── CartController.java
│   ├── HomeController.java
│   ├── OrderController.java
│   ├── PaymentController.java
│   ├── ProductController.java
│   └── WishlistController.java
├── entity/                  # JPA Entities (Database Models)
│   ├── Cart.java
│   ├── CartItem.java
│   ├── Category.java
│   ├── Order.java
│   ├── OrderItem.java
│   ├── Product.java
│   ├── User.java
│   ├── Wishlist.java
│   └── WishlistProduct.java
├── repository/              # Spring Data JPA Repositories for data access
│   ├── CartItemRepository.java
│   ├── CartRepository.java
│   ├── CategoryRepository.java
│   ├── OrderItemRepository.java
│   ├── OrderRepository.java
│   ├── ProductRepository.java
│   ├── UserRepository.java
│   └── WishlistProductRepository.java
└── service/                 # Business logic layer
    ├── CartService.java
    ├── CustomUserDetailsService.java
    ├── OrderService.java
    ├── ProductService.java
    ├── UserService.java
    └── WishlistService.java
src/main/resources/
├── static/                  # Static resources (CSS, JS, images not uploaded by users)
│   └── css/
│       └── style.css
├── templates/               # Thymeleaf HTML templates
│   ├── add_product.html
│   ├── cart.html
│   ├── home.html
│   ├── login.html
│   ├── order_history.html
│   ├── order_success.html
│   ├── payment.html
│   └── register.html
└── application.properties   # Spring Boot application configuration
Future Improvements
User Roles & Permissions: Implement more granular roles (e.g., ADMIN, SELLER, CUSTOMER) and restrict certain actions (e.g., only ADMIN or SELLER can add/delete products).

Product Update/Edit: Add functionality to edit existing product details.

Real Payment Gateway Integration: Integrate with actual payment providers like Stripe, Razorpay, PayPal, etc.

Order Details Page: Create a page to view detailed information for a single order.

User Profile Management: Allow users to update their profile information.

Search Functionality: Implement search for products.

Pagination: For product listings, order history, etc., to handle large datasets.

Error Handling & Validation: More user-friendly error messages and robust form validation.

Frontend Enhancements: Improve UI/UX with more advanced CSS, JavaScript, or consider moving to a decoupled frontend framework (React, Angular, Vue).

Email Notifications: For order confirmations, shipping updates, etc.

Rating and Reviews: Allow users to rate and review products.

Shipping & Address Management: Add shipping addresses to user profiles and orders.

Centralized Image Storage: For production, consider cloud storage (AWS S3, Google Cloud Storage) instead of local file system.

Contributing
Contributions are welcome! If you have suggestions or find issues, please open an issue or submit a pull request.

License
This project is open-source and available under the MIT License.

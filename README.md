🍬 Sweet Shop Management System

A full-stack web application for managing a sweet shop with features for authentication, sweets inventory management, and role-based access (User/Admin).

🚀 Features
🔑 Authentication

User registration and login with JWT-based authentication.

Default role: USER (admin cannot be self-registered).

Admin role can only be assigned one time only.

🍭 Sweets Management (Protected Endpoints)

Add Sweet (Admin) → POST /api/sweets

View All Sweets → GET /api/sweets

Search Sweets by name, category, or price range → GET /api/sweets/search

Update Sweet → PUT /api/sweets/{id}

Delete Sweet (Admin) → DELETE /api/sweets/{id}

📦 Inventory Management (Protected Endpoints)

Purchase Sweet (User) → POST /api/sweets/{id}/purchase (decreases quantity).

Restock Sweet (Admin) → POST /api/sweets/{id}/restock (increases quantity).

🛠 Tech Stack
Backend

Java Spring Boot (REST API)

PostgreSQL (Database)

Spring Security + JWT (Authentication & Authorization)

JUnit + Mockito (Testing)

Frontend

React (Vite)

Bootstrap 5 (UI Styling)

Tools

Maven (backend build tool)

npm + Vite (frontend build tool)

Postman (API testing)

GitHub (version control & collaboration)

⚙️ Setup Instructions
1️⃣ Backend (Spring Boot)
# Go to backend folder
cd sweetshop-management

# Update PostgreSQL credentials in application.properties
spring.datasource.url=jdbc:postgresql://localhost:5432/sweetshop
spring.datasource.username=your_username
spring.datasource.password=your_password

# Run backend
mvn spring-boot:run

2️⃣ Frontend (React + Bootstrap)
# Go to frontend folder
cd sweetshop-frontend

# Install dependencies
npm install

# Run frontend
npm run dev


Now open 👉 http://localhost:5173

📮 API Testing with Postman

Register User → POST /api/auth/register

{
  "username": "Adarsh",
  "password": "password123"
}


Login User → POST /api/auth/login

{
  "username": "Adarsh",
  "password": "password123"
}


✅ Returns JWT token for authorization.

Add Sweet (Admin only)

Use Bearer token in Authorization header.

{
  "name": "Ladoo",
  "category": "Indian",
  "price": 50.0,
  "quantity": 20
}

👑 Admin Setup

Username: admin;
Password=admin123;



🧪 Testing

Run all backend tests:

Postman Link: https://www.postman.com/adarsh8987/sweetshop/collection/t5zz5rn/sweet-shop-api?action=share&creator=34759469

🍬 Sweet Shop Management System

A full-stack web application for managing a sweet shop with features for authentication, sweets inventory management, and role-based access (User/Admin).

🚀 Features
🔑 Authentication

User registration and login with JWT-based authentication.

Default role: USER (admin cannot be self-registered).

Admin role can only be assigned manually via the database.

🍭 Sweets Management (Protected Endpoints)

Add Sweet (Admin) → POST /api/sweets

View All Sweets → GET /api/sweets

Search Sweets by name, category, or price range → GET /api/sweets/search

Update Sweet → PUT /api/sweets/{id}

Delete Sweet (Admin) → DELETE /api/sweets/{id}

📦 Inventory Management (Protected Endpoints)

Purchase Sweet (User) → POST /api/sweets/{id}/purchase (decreases quantity).

Restock Sweet (Admin) → POST /api/sweets/{id}/restock (increases quantity).

🛠 Tech Stack
Backend

Java Spring Boot (REST API)

PostgreSQL (Database)

Spring Security + JWT (Authentication & Authorization)

JUnit + Mockito (Testing)

Frontend

React (Vite)

Bootstrap 5 (UI Styling)

Tools

Maven (backend build tool)

npm + Vite (frontend build tool)

Postman (API testing)

GitHub (version control & collaboration)

⚙️ Setup Instructions
1️⃣ Backend (Spring Boot)
# Go to backend folder
cd sweetshop-management

# Update PostgreSQL credentials in application.properties
spring.datasource.url=jdbc:postgresql://localhost:5432/sweetshop
spring.datasource.username=your_username
spring.datasource.password=your_password

# Run backend
mvn spring-boot:run

2️⃣ Frontend (React + Bootstrap)
# Go to frontend folder
cd sweetshop-frontend

# Install dependencies
npm install

# Run frontend
npm run dev


Now open 👉 http://localhost:5173

📮 API Testing with Postman


Register User → POST /api/auth/register

{
  "username": "john",
  "password": "password123"
}


Login User → POST /api/auth/login

{
  "username": "john",
  "password": "password123"
}


✅ Returns JWT token for authorization.

Add Sweet (Admin only)

Use Bearer token in Authorization header.

{
  "name": "Ladoo",
  "category": "Indian",
  "price": 50.0,
  "quantity": 20
}

👑 Admin Setup

To create an admin user, insert manually in PostgreSQL:

INSERT INTO users (username, password, role)
VALUES ('admin', '$2a$10$EXAMPLE_HASHED_PASSWORD', 'ROLE_ADMIN');


👉 The password can be admin123 (bcrypt-hashed).

🧪 Testing

Run all backend tests:

mvn test

🤖 My AI Usage

In this project, I used AI tools to support development, problem-solving, and documentation.

✅ Tools Used

ChatGPT (OpenAI GPT-5)

GitHub Copilot

✅ How I Used Them

ChatGPT:

Helped design the backend API structure.

Guided Spring Boot implementation (services, controllers, repositories).

Assisted in debugging (fixing role assignment, JWT errors, Tailwind setup).

Suggested Postman test cases & Git commands.

Helped build frontend setup with React + Bootstrap.

GitHub Copilot:

Suggested boilerplate code for entities, repositories, service methods.

Assisted with JSX snippets for UI components.

✅ Reflection on AI in Workflow

AI accelerated development by generating repetitive code.

Acted like a tutor, explaining concepts and errors.

I reviewed and customized all AI outputs — ensuring learning + correctness.

Saved significant time, especially in debugging and testing.

📌 Future Enhancements

Add Order History for users.

Implement Payment Gateway Integration.

Deploy project to Docker + Cloud (AWS/Render).

mvn test

🤖 My AI Usage

In this project, I used AI tools to support development, problem-solving, and documentation.

✅ Tools Used

ChatGPT (OpenAI GPT-5)

GitHub Copilot

✅ How I Used Them

ChatGPT:

Helped design the backend API structure.

Helped in designing test unit for red 

Helped build frontend setup with React + Bootstrap.

GitHub Copilot:

Suggested boilerplate code for entities, repositories, service methods.

Help me to importing

✅ Reflection on AI in Workflow

AI accelerated development by generating repetitive code.

Acted like a tutor, explaining concepts and errors.

I reviewed and customized all AI outputs — ensuring learning + correctness.

Saved significant time, especially in debugging and testing.

In short period of time we can do more work.

It suggestion helps me to write and complete the code fast

Itsh help me to focus on logic no syntax error,code error etc

📌 Future Enhancements

Add Order History for users.

Remove UI of the Project 

Implement Payment Gateway Integration.

Deploy project to Docker + Cloud (AWS/Render).

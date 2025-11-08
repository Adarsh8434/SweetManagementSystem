🍬 Sweet Shop Management System

🔗 GitHub: https://github.com/Adarsh8434/SweetManagementSystem

🔗 Postman API Collection: https://www.postman.com/adarsh8987/sweetshop/collection/t5zz5rn/sweet-shop-api

🔗 ✅ Live Frontend: http://54.253.94.114

🔗 ✅ Live Backend (EC2 + Docker): http://54.253.94.114:8080

A full-stack web application to manage sweets inventory with secure authentication, JWT login, and admin-controlled access.

🚀 Features
🔐 Authentication

JWT-based Login & Registration

Default role → USER

ADMIN cannot self-register

Admin created automatically only once

🍭 Sweets Management (Protected APIs)
Feature	Role	Endpoint
Add Sweet	ADMIN	POST /api/sweets
View All Sweets	ALL	GET /api/sweets
Search Sweets	ALL	GET /api/sweets/search
Update Sweet	ADMIN	PUT /api/sweets/{id}
Delete Sweet	ADMIN	DELETE /api/sweets/{id}
📦 Inventory Management
Action	Role	Endpoint
Purchase Sweet (–qty)	USER/ADMIN	POST /api/sweets/{id}/purchase
Restock Sweet (+qty)	ADMIN	POST /api/sweets/{id}/restock
🧑‍💻 Tech Stack
✅ Backend

Java Spring Boot

Spring Security + JWT

PostgreSQL

Hibernate & JPA

JUnit + Mockito

Dockerized + deployed on AWS EC2

✅ Frontend

React (Vite)

Bootstrap 5

Deployed via Nginx on AWS EC2

✅ Tools

Docker

Maven

Git & GitHub

Postman

AWS EC2, Nginx

⚙️ Setup Instructions
✅ 1️⃣ Backend (Spring Boot)

Navigate:

cd sweetshop-management


Update DB:

spring.datasource.url=jdbc:postgresql://localhost:5432/sweetshop
spring.datasource.username=your_username
spring.datasource.password=your_password


Run:

mvn spring-boot:run


Runs at → http://localhost:8080

✅ 2️⃣ Frontend (React + Bootstrap)
cd sweetshop-frontend
npm install
npm run dev


Runs at → http://localhost:5173

📮 Postman API Testing
Register
POST /api/auth/register
{
  "username": "Adarsh",
  "password": "password123"
}

Login
POST /api/auth/login
{
  "username": "Adarsh",
  "password": "password123"
}


Receive → JWT Token

👑 Default Admin
username: admin  
password: admin123

🧪 Testing
mvn test

🤖 AI Usage
✅ Tools

ChatGPT (GPT-5)

GitHub Copilot

✅ Helped In

Designing backend architecture

Fixing Spring Security + JWT issues

Docker + EC2 deployment

Debugging & writing queries

Creating login/register UI

Writing documentation

📌 Future Enhancements

User order history

Complete UI redesign

Payment gateway integration

Email & OTP login

Convert into Microservices

✅ Footer

Made with ❤️ by Adarsh Kumar Choubey
Full-Stack Developer | Java | Spring Boot | React | AWS | Docker

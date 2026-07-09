# Micro-Commerce Backend System (Modular Microservices Architecture)

This project is a scalable, service-oriented e-commerce backend platform designed using Spring Boot, JWT authentication, RabbitMQ, and Docker. It currently includes core modules like User Service and Inventory Management, with secure login/register APIs and message queue communication for future services.

## ✅ Completed Modules
- **User Service**: Handles registration, login (JWT-based), product subscription with notification and user management. Ready for role/permission expansion.
- **Authentication**: OAuth2 and JWT-based token generation and validation with protected API access.
- **Product Service**: Manages product stock levels (initial implementation). To be split from Product module in future refactoring.
- **RabbitMQ**: Set up for async event-driven communication (e.g., notification trigger after user creation).
- **Dockerized**: RabbitMQ and app environments containerized.
- **Notification Service (Email/SMS)**
- **S3 Image Storage**: (for product media uploads)
- **ElasticSearch**: (for full-text product search)
- **Migrate toward microservices using Spring Cloud Gateway and Eureka**

## 🛠 In Progress / Planned Modules
- **Inventory Service**
- **Order Service**
- **Cart Service**
- **Payment Gateway Integration**
- **Shipping & Delivery Service**
- **Redis Cache** (for session and product caching)
- **S3 Image Storage** (user media uploads)

## 💡 Future Goals
- Enable Redis caching and search optimization using ElasticSearch.
- Set up CI/CD pipelines with GitHub Actions.
- Enhance security with rate limiting, token revocation, and audit logging.

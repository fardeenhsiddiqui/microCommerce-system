# Micro-Commerce Backend System (Modular Microservices Architecture)

This project is a scalable, service-oriented e-commerce backend platform designed using Spring Boot, JWT authentication, RabbitMQ, and Docker. It currently includes core modules like User Service and Inventory Management, with secure login/register APIs and message queue communication for future services.

## ✅ Completed Modules
- **User Service**: Handles registration, login (JWT-based), product subscription with notification and user management. Ready for role/permission expansion.
- **Authentication**: OAuth2 and JWT-based token generation and validation with protected API access.
- **Inventory Service (under Product module)**: Manages product stock levels (initial implementation). To be split from Product module in future refactoring.
- **RabbitMQ**: Set up for async event-driven communication (e.g., notification trigger after product is in stock).
- **Dockerized**: RabbitMQ and app environments containerized.

## 🛠 In Progress / Planned Modules
- **Product Service** (currently combined with Inventory, will separate it for clean domain boundary)
- **Order Service**
- **Cart Service**
- **Payment Gateway Integration**
- **Notification Service (Email/SMS)**
- **Shipping & Delivery Service**
- **Redis Cache** (for session and product caching)
- **ElasticSearch** (for full-text product search)
- **S3 Image Storage** (for product and user media uploads)

## 💡 Future Goals
- Migrate toward microservices using Spring Cloud Gateway and Eureka.
- Enable Redis caching and search optimization using ElasticSearch.
- Set up CI/CD pipelines with GitHub Actions.
- Enhance security with rate limiting, token revocation, and audit logging.

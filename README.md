# Digital Life Insurance Policy Management System

A secure, scalable, and modular **Digital Life Insurance Policy Management System** built using a **Spring Boot Microservices Architecture**.

The application digitizes the insurance policy lifecycle from customer registration and authentication to insurance-plan selection, premium calculation, policy creation, payment, policy activation, notifications, and renewal processing.

---

## 📌 Project Overview

The Digital Life Insurance Policy Management System provides a centralized platform for managing insurance-related operations.

### Core Business Flow

```text
Customer Registration
        ↓
Customer Login
        ↓
JWT Authentication
        ↓
Browse Insurance Plans
        ↓
Calculate Illustrative Premium
        ↓
Create Policy
        ↓
PAYMENT_PENDING
        ↓
Make Premium Payment
        ↓
Payment Successful
        ↓
Policy ACTIVE
        ↓
RabbitMQ Event
        ↓
Notification Service
        ↓
Policy & Payment History
        ↓
Renewal Request
        ↓
JMS
        ↓
Renewal Processing
```

---

# 🏗️ System Architecture

```text
                              ┌──────────────────┐
                              │      CLIENT      │
                              │  Postman / UI    │
                              └────────┬─────────┘
                                       │
                                       ▼
                              ┌──────────────────┐
                              │   API GATEWAY    │
                              │      :8080       │
                              └────────┬─────────┘
                                       │
                    ┌──────────────────┼──────────────────┐
                    │                  │                  │
                    ▼                  ▼                  ▼
             ┌────────────┐     ┌────────────┐     ┌──────────────┐
             │    AUTH    │     │   POLICY   │     │ NOTIFICATION │
             │  SERVICE   │     │  SERVICE   │     │   SERVICE    │
             │   :8081    │     │   :8082    │     │    :8083     │
             └─────┬──────┘     └─────┬──────┘     └──────┬───────┘
                   │                   │                    │
                   ▼                   ▼                    │
             ┌────────────┐      ┌──────────────┐           │
             │ PostgreSQL │      │  PostgreSQL  │           │
             │  Auth DB   │      │  Policy DB   │           │
             └────────────┘      └──────┬───────┘           │
                                        │                    │
                              ┌─────────┼─────────┐          │
                              │         │         │          │
                              ▼         ▼         ▼          ▼
                           Redis    RabbitMQ     JMS    Notification
                           Cache     Events     Artemis   Processing
```

### Service Discovery

```text
                         ┌──────────────────┐
                         │      EUREKA      │
                         │ SERVICE REGISTRY │
                         │      :8761       │
                         └────────┬─────────┘
                                  │
                 ┌────────────────┼────────────────┐
                 ▼                ▼                ▼
              AUTH            POLICY         NOTIFICATION
             SERVICE           SERVICE          SERVICE
                 │                │                │
                 └────────────────┼────────────────┘
                                  ▼
                             API GATEWAY
```

---

# 📦 Microservices

| Service | Port | Responsibility |
|---|---:|---|
| Eureka Server | `8761` | Service discovery and registration |
| API Gateway | `8080` | Single entry point and request routing |
| Auth Service | `8081` | Registration, login, JWT and authentication |
| Policy Service | `8082` | Plans, premiums, policies and payments |
| Notification Service | `8083` | Policy event notifications |
| Batch/Processing Service | `8084` | Customer import and processing operations |

---

# 🔐 Auth Service

**Port:** `8081`

The Auth Service manages customer identity, authentication, authorization, and user-related operations.

### Responsibilities

- Customer registration
- Customer login
- Password hashing
- JWT generation
- JWT validation
- Authentication
- Role-based authorization
- Customer profile retrieval
- Google OAuth2 / OIDC integration

### Supported Roles

```text
CUSTOMER
ADMIN
EMPLOYEE
```

### APIs

| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/auth/register` | Register a customer |
| POST | `/api/auth/login` | Authenticate a customer |
| GET | `/api/auth/me` | Get authenticated user |

---

# 🛡️ Security Architecture

Protected APIs require a valid JWT.

```text
Client
  │
  │ Authorization: Bearer <JWT>
  ▼
API Gateway
  │
  ▼
Security Filter Chain
  │
  ├── Read Authorization Header
  ├── Extract JWT
  ├── Validate Signature
  ├── Validate Expiration
  ├── Extract User Information
  ├── Create Authentication
  └── Set SecurityContext
  │
  ▼
Authorization
  │
  ├── CUSTOMER
  ├── ADMIN
  └── EMPLOYEE
  │
  ▼
Controller
```

### Authentication vs Authorization

**Authentication** determines who the user is.

**Authorization** determines what the authenticated user is allowed to access.

Example:

```text
CUSTOMER → /api/policies
         → Allowed

CUSTOMER → /api/admin/plans
         → 403 Forbidden

ADMIN → /api/admin/plans
      → Allowed
```

### Typical HTTP Responses

```text
401 Unauthorized
→ Missing or invalid authentication

403 Forbidden
→ User is authenticated but does not have the required role
```

---

# 📋 Policy Service

**Port:** `8082`

The Policy Service contains the core insurance business functionality.

### Responsibilities

- Insurance plan management
- Premium calculation
- Policy creation
- Policy retrieval
- Policy history
- Premium management
- Payment processing
- Policy activation
- Policy renewal
- Redis caching
- RabbitMQ event publishing
- JMS renewal messaging

---

# 🏦 Insurance Plan Management

Customers can browse active insurance plans.

### Example Plan

```text
Plan
--------------------------------
Name          : Secure Life Plus
Coverage      : ₹50,00,000
Duration      : 20 Years
Base Premium  : ₹15,000
Status        : ACTIVE
```

### Customer APIs

```http
GET  /api/plans
GET  /api/plans/{id}
POST /api/plans/calculate-premium
```

### Admin APIs

```http
POST   /api/admin/plans
PUT    /api/admin/plans/{id}
DELETE /api/admin/plans/{id}
```

---

# 💰 Premium Calculation

The system provides an **illustrative premium calculation** based on configured business rules.

```text
Customer Details
       +
Insurance Plan
       +
Age / Coverage / Term
       ↓
Premium Calculation
       ↓
Illustrative Premium
```

> The project does not implement real actuarial pricing or medical underwriting.

---

# 📄 Policy Management

A customer can create a policy after selecting an insurance plan.

### Policy Creation Flow

```text
Customer
   ↓
Select Insurance Plan
   ↓
Validate Customer
   ↓
Validate Plan
   ↓
Check Plan Status
   ↓
Calculate Premium
   ↓
Create Policy
   ↓
Create Premium Record
   ↓
PAYMENT_PENDING
```

### Policy Status Lifecycle

```text
PAYMENT_PENDING
       │
       │ Payment Successful
       ▼
     ACTIVE
       │
       ├──────────► EXPIRED
       │
       ├──────────► CANCELLED
       │
       └──────────► RENEWAL
```

---

# 💳 Payment Management

The payment module handles simulated premium payments.

### Payment Flow

```text
Payment Request
      ↓
Validate Policy
      ↓
Validate Amount
      ↓
Process Payment
      ↓
   ┌──┴─────┐
   │        │
SUCCESS   FAILED
   │        │
   ▼        ▼
ACTIVE   PAYMENT_PENDING
```

### APIs

```http
POST /api/payments
GET  /api/payments/{id}
GET  /api/policies/{policyId}/payments
```

> Payment processing is simulated and does not integrate with a real banking system or external payment gateway.

---

# 🔔 Notification Service

**Port:** `8083`

The Notification Service handles asynchronous policy-related notifications.

### Communication Flow

```text
Policy Service
      │
      │ Policy Activated Event
      ▼
   RabbitMQ
      │
      ▼
Notification Service
      │
      ▼
Customer Notification
```

### RabbitMQ Configuration

```text
Exchange:
insurance.exchange

Queue:
policy.activation.queue

Routing Key:
policy.activated
```

### Why RabbitMQ?

RabbitMQ is used for event-driven asynchronous communication.

For example, after a successful payment:

```text
Payment Successful
        ↓
Policy becomes ACTIVE
        ↓
Publish PolicyActivatedEvent
        ↓
RabbitMQ
        ↓
Notification Service
        ↓
Customer Notification
```

The Policy Service does not need to wait for the notification processing to complete.

---

# 🔄 Renewal Processing

Renewal operations use asynchronous JMS messaging.

```text
Customer
   ↓
Renew Policy
   ↓
Policy Service
   ↓
Renewal Request
   ↓
JMS Queue
   ↓
Processing Service
   ↓
Renewal Processing
```

### JMS Queue

```text
insurance.renewal.queue
```

### ActiveMQ Artemis

Apache ActiveMQ Artemis is used as the JMS message broker.

Development management console:

```text
http://localhost:8161
```

Default development credentials:

```text
Username: admin
Password: admin
```

---

# ⚡ Redis Caching

Redis is used to cache frequently accessed information.

### Cache Flow

```text
Client
  ↓
Policy Service
  ↓
Redis
  │
  ├── Cache HIT
  │      ↓
  │   Return Data
  │
  └── Cache MISS
         ↓
      PostgreSQL
         ↓
        Redis
         ↓
      Return Data
```

### Benefits

- Faster response time
- Reduced database queries
- Better performance for frequently accessed data

---

# 🌐 API Gateway

**Port:** `8080`

The API Gateway acts as the single entry point for client requests.

```text
Client
  ↓
API Gateway
  ↓
Required Microservice
```

### Gateway Routes

| Path | Target Service |
|---|---|
| `/api/auth/**` | AUTH-SERVICE |
| `/api/plans/**` | POLICY-SERVICE |
| `/api/policies/**` | POLICY-SERVICE |
| `/api/customers/**` | POLICY-SERVICE |
| `/api/payments/**` | POLICY-SERVICE |
| `/api/premiums/**` | POLICY-SERVICE |
| `/api/notifications/**` | NOTIFICATION-SERVICE |
| `/api/admin/customers/import/**` | PROCESSING SERVICE |
| `/api/renewals/**` | POLICY-SERVICE |

### Gateway URL

```text
http://localhost:8080
```

---

# 🔎 Eureka Service Discovery

**Port:** `8761`

Eureka acts as the service registry.

```text
                   Eureka
                     │
        ┌────────────┼────────────┐
        │            │            │
        ▼            ▼            ▼
      AUTH         POLICY     NOTIFICATION
     SERVICE       SERVICE       SERVICE
        │            │            │
        └────────────┼────────────┘
                     │
                     ▼
                API Gateway
```

Services register themselves with Eureka, allowing service-to-service communication without relying on hardcoded service locations.

### Eureka Dashboard

```text
http://localhost:8761
```

---

# 🗄️ Database Architecture

Each business service owns its own database.

```text
AUTH SERVICE
     │
     ▼
insurance_auth_db
     ├── users
     └── customers


POLICY SERVICE
     │
     ▼
insurance_policy_db
     ├── insurance_plans
     ├── policies
     ├── premiums
     └── payments


PROCESSING SERVICE
     │
     ▼
insurance_batch_db
```

### Database Ownership Principle

A service should not directly access another service's database.

Instead:

```text
Policy Service
      │
      │ REST API
      ▼
Auth Service
      │
      ▼
Customer Information
```

This maintains proper microservice boundaries.

---

# 🧩 Core Domain Model

```text
User
 │
 └── Customer
       │
       ▼
  Insurance Plan
       │
       ▼
     Policy
      /   \
     /     \
    ▼       ▼
Premium   Payment
```

### Main Entities

- `User`
- `Customer`
- `InsurancePlan`
- `Policy`
- `Premium`
- `Payment`
- `AuditLog`

---

# 🔗 Service Communication

The system uses different communication mechanisms according to the business requirement.

```text
                SERVICE COMMUNICATION
                         │
          ┌──────────────┼──────────────┐
          │              │              │
          ▼              ▼              ▼
       REST API       RabbitMQ         JMS
          │              │              │
          ▼              ▼              ▼
    Synchronous      Event-Based    Asynchronous
    Communication   Communication    Processing
```

### REST

Used when an immediate response is required.

Example:

```text
Policy Service
      │
      │ REST
      ▼
Auth Service
      │
      ▼
Customer Details
```

### RabbitMQ

Used for event-driven communication.

```text
Policy Activated
      ↓
RabbitMQ
      ↓
Notification Service
```

### JMS

Used for asynchronous renewal processing.

```text
Renewal Request
      ↓
JMS Queue
      ↓
Processing Service
```

---

# 📡 API Reference

## Authentication

```http
POST /api/auth/register
POST /api/auth/login
GET  /api/auth/me
```

## Insurance Plans

```http
GET  /api/plans
GET  /api/plans/{id}
POST /api/plans/calculate-premium
```

## Admin Plans

```http
POST   /api/admin/plans
PUT    /api/admin/plans/{id}
DELETE /api/admin/plans/{id}
```

## Customers

```http
GET /api/customers/{id}
PUT /api/customers/{id}
```

## Policies

```http
POST /api/policies
GET  /api/policies
GET  /api/policies/{id}
GET  /api/customers/{customerId}/policies
POST /api/policies/{id}/renew
```

## Premiums

```http
GET /api/policies/{policyId}/premiums
GET /api/premiums/{id}
```

## Payments

```http
POST /api/payments
GET  /api/payments/{id}
GET  /api/policies/{policyId}/payments
```

## Customer Import

```http
POST /api/admin/customers/import
```

## Renewal

```http
POST /api/renewals/send
```

---

# 🔄 Complete End-to-End Business Scenario

### Step 1 — Customer Registration

```http
POST /api/auth/register
```

The customer account is created.

### Step 2 — Customer Login

```http
POST /api/auth/login
```

The Auth Service validates the credentials and generates a JWT.

### Step 3 — Browse Insurance Plans

```http
GET /api/plans
```

The customer views available plans.

### Step 4 — Calculate Premium

```http
POST /api/plans/calculate-premium
```

The system calculates an illustrative premium.

### Step 5 — Create Policy

```http
POST /api/policies
```

The policy is created with:

```text
PAYMENT_PENDING
```

### Step 6 — Make Payment

```http
POST /api/payments
```

The payment is processed.

### Step 7 — Activate Policy

```text
PAYMENT_PENDING
       ↓
Payment SUCCESS
       ↓
ACTIVE
```

### Step 8 — Publish Event

```text
Policy Service
      ↓
RabbitMQ
      ↓
PolicyActivatedEvent
```

### Step 9 — Notification

```text
RabbitMQ
    ↓
Notification Service
    ↓
Customer Notification
```

### Step 10 — Renewal

```text
Renewal Request
      ↓
JMS
      ↓
Renewal Processing
```

---

# 🧪 Testing Strategy

The APIs can be tested using **Postman**.

Testing includes:

- Customer registration
- Duplicate registration
- Customer login
- Invalid login
- JWT authentication
- Unauthorized requests
- Role-based authorization
- Customer operations
- Insurance plan CRUD
- Premium calculation
- Policy creation
- Payment processing
- Policy activation
- Premium retrieval
- Payment history
- Redis caching
- RabbitMQ events
- Notification processing
- Renewal processing
- Customer import
- Exception handling

---

# 📖 Swagger / OpenAPI

Swagger/OpenAPI documentation is available for the services.

### Auth Service

```text
http://localhost:8081/swagger-ui/index.html
```

### Policy Service

```text
http://localhost:8082/swagger-ui/index.html
```

### Notification Service

```text
http://localhost:8083/swagger-ui/index.html
```

### Processing Service

```text
http://localhost:8084/swagger-ui/index.html
```

---

# 🛠️ Technology Stack

| Category | Technology |
|---|---|
| Programming Language | Java |
| Backend Framework | Spring Boot |
| Architecture | Microservices |
| API | REST |
| Security | Spring Security |
| Authentication | JWT |
| SSO | OAuth2 / OIDC |
| Service Discovery | Eureka |
| API Gateway | Spring Cloud Gateway |
| Database | PostgreSQL |
| ORM | Spring Data JPA / Hibernate |
| Cache | Redis |
| Messaging | RabbitMQ |
| JMS Broker | Apache ActiveMQ Artemis |
| API Documentation | Swagger / OpenAPI |
| Testing | JUnit / Postman |
| Logging | SLF4J |
| AOP | Spring AOP |
| Build Tool | Maven |
| Boilerplate Reduction | Lombok |
| Containerization | Docker |

---

# 📁 Project Structure

```text
Digital-Life-Insurance/
│
├── eureka-server/
│   ├── src/
│   └── pom.xml
│
├── api-gateway/
│   ├── src/
│   └── pom.xml
│
├── auth-service/
│   ├── src/
│   │   └── main/
│   │       ├── java/
│   │       │   └── com/
│   │       │       └── insurance/
│   │       │           └── auth/
│   │       │               ├── controller/
│   │       │               ├── service/
│   │       │               ├── repository/
│   │       │               ├── entity/
│   │       │               ├── dto/
│   │       │               ├── security/
│   │       │               ├── config/
│   │       │               ├── exception/
│   │       │               └── aspect/
│   │       └── resources/
│   │           └── application.yml
│   └── pom.xml
│
├── policy-service/
│   ├── src/
│   │   └── main/
│   │       ├── java/
│   │       │   └── com/
│   │       │       └── insurance/
│   │       │           └── policy/
│   │       │               ├── controller/
│   │       │               ├── service/
│   │       │               ├── repository/
│   │       │               ├── entity/
│   │       │               ├── dto/
│   │       │               ├── client/
│   │       │               ├── security/
│   │       │               ├── messaging/
│   │       │               ├── jms/
│   │       │               ├── config/
│   │       │               ├── exception/
│   │       │               └── aspect/
│   │       └── resources/
│   │           └── application.yml
│   └── pom.xml
│
├── notification-service/
│   ├── src/
│   └── pom.xml
│
├── batch-service/
│   ├── src/
│   └── pom.xml
│
├── docker-compose.yml
└── README.md
```

---

# ⚙️ Prerequisites

Install the following:

- Java 17+ / Java 21
- Maven
- PostgreSQL
- Redis
- RabbitMQ
- Docker Desktop
- IntelliJ IDEA
- Postman

### Verify Java

```bash
java -version
```

### Verify Maven

```bash
mvn -version
```

---

# 🗃️ PostgreSQL Setup

Create the required databases:

```sql
CREATE DATABASE insurance_auth_db;

CREATE DATABASE insurance_policy_db;

CREATE DATABASE insurance_batch_db;
```

Update the database credentials in each service's `application.yml`.

---

# 🚀 Running the Application

Start the infrastructure components first:

```text
1. PostgreSQL
2. Redis
3. RabbitMQ
4. ActiveMQ Artemis
```

Then start the services:

```text
1. Eureka Server
       ↓
2. Auth Service
       ↓
3. Policy Service
       ↓
4. Notification Service
       ↓
5. Processing Service
       ↓
6. API Gateway
```

---

# 🔍 Service URLs

| Component | URL |
|---|---|
| Eureka | `http://localhost:8761` |
| API Gateway | `http://localhost:8080` |
| Auth Service | `http://localhost:8081` |
| Policy Service | `http://localhost:8082` |
| Notification Service | `http://localhost:8083` |
| Processing Service | `http://localhost:8084` |
| RabbitMQ Management | `http://localhost:15672` |
| Artemis Management | `http://localhost:8161` |
| Redis | `localhost:6379` |

---

# 🔑 JWT-Protected Request

For protected APIs, send the JWT using the Authorization header.

```http
Authorization: Bearer <JWT_TOKEN>
```

Example:

```http
GET http://localhost:8080/api/policies/1
```

Header:

```text
Authorization: Bearer eyJhbGciOiJIUzI1Ni...
```

---

# 📝 Logging and AOP

The application uses SLF4J-based logging for important application events.

Examples:

```text
Customer registration started
User authenticated successfully
Policy created
Payment processed
Policy activated
RabbitMQ event published
Renewal request received
```

AOP is used for cross-cutting concerns such as:

- Execution-time monitoring
- Audit logging
- Service-level logging

Conceptual flow:

```text
Controller
    ↓
Service
    ↓
AOP
    ↓
Business Logic
    ↓
Repository
```

---

# 📊 Audit Logging

Important operations can be recorded through audit logging.

Example:

```text
User       : admin@example.com
Action     : CREATE_POLICY
Service    : POLICY-SERVICE
Status     : SUCCESS
Timestamp  : 2026-09-16
```

Audit logging provides traceability for important application operations.

---

# 🔒 Security Considerations

The application follows several security practices:

- Password hashing
- JWT-based authentication
- Role-based authorization
- Protected REST APIs
- JWT validation
- Input validation
- Centralized exception handling
- Service-level security boundaries

The following values should be externalized in production:

```text
Database passwords
JWT secrets
OAuth credentials
RabbitMQ credentials
Artemis credentials
```

---

# 🧯 Exception Handling

The services use centralized exception handling to provide consistent API responses.

Common HTTP responses:

```text
400 Bad Request
401 Unauthorized
403 Forbidden
404 Not Found
409 Conflict
500 Internal Server Error
```

Example:

```json
{
  "timestamp": "2026-09-16T12:00:00",
  "status": 404,
  "error": "Not Found",
  "message": "Policy not found with id: 100",
  "path": "/api/policies/100"
}
```

---

# 🎯 Business Scope

## Included

- Customer registration
- Customer authentication
- JWT security
- OAuth2 / Google SSO
- Role-based authorization
- Insurance plan management
- Illustrative premium calculation
- Policy creation
- Premium management
- Simulated payment
- Policy activation
- Payment history
- Policy history
- Renewal processing
- Asynchronous notifications
- Redis caching
- Customer data import
- Audit logging
- Swagger/OpenAPI documentation
- Postman API testing

## Not Included

The system does not implement:

- Real actuarial pricing
- Medical underwriting
- Claims settlement
- Real KYC verification
- Real banking integration
- Real payment gateway integration
- Real insurance regulatory processing

---

# 🌍 Production Considerations

For production deployment, the following enhancements can be considered:

- Externalized configuration
- Secrets management
- HTTPS/TLS
- Centralized logging
- Distributed tracing
- API rate limiting
- Monitoring and alerting
- Database migrations using Flyway or Liquibase
- Message retry mechanisms
- Dead-letter queues
- High availability
- Load balancing
- Container orchestration
- CI/CD pipeline

---

# 🧭 Complete Project Mind Map

```text
                    DIGITAL LIFE INSURANCE
                    POLICY MANAGEMENT SYSTEM
                              │
          ┌───────────────────┼───────────────────┐
          │                   │                   │
          ▼                   ▼                   ▼
       CUSTOMER             ADMIN              SERVICES
          │                   │                   │
          │                   ├── Manage Plans    ├── Auth Service
          │                   ├── Update Plans    ├── Policy Service
          │                   └── Customer Data   ├── Notification
          │                                       └── Processing
          ▼
      REGISTER
          │
          ▼
       LOGIN
          │
          ▼
      JWT TOKEN
          │
          ▼
    SECURITY CHECK
          │
          ▼
      VIEW PLANS
          │
          ▼
 PREMIUM CALCULATION
          │
          ▼
    CREATE POLICY
          │
          ▼
   PAYMENT_PENDING
          │
          ▼
       PAYMENT
       /     \
      /       \
 SUCCESS      FAILED
    │            │
    ▼            ▼
  ACTIVE     PAYMENT_PENDING
    │
    ├──────────────► RABBITMQ
    │                    │
    │                    ▼
    │               NOTIFICATION
    │
    ├──────────────► REDIS
    │
    ├──────────────► POLICY HISTORY
    │
    ▼
   RENEWAL
      │
      ▼
     JMS
      │
      ▼
RENEWAL PROCESSING
      │
      ▼
POLICY RENEWED
```

---

# ⭐ Project Highlights

- Microservices-based architecture
- Secure JWT authentication
- Role-based authorization
- Service discovery using Eureka
- Centralized API Gateway
- Separate database ownership
- Redis caching
- RabbitMQ event-driven communication
- JMS-based asynchronous processing
- RESTful APIs
- Centralized exception handling
- AOP-based cross-cutting concerns
- Audit logging
- Swagger/OpenAPI documentation
- Postman API testing
- Docker support

---

# 👨‍💻 Development Principles

The project follows these architectural principles:

```text
Separation of Concerns
        ↓
Single Responsibility
        ↓
Loose Coupling
        ↓
Clear Service Boundaries
        ↓
Secure Communication
        ↓
Asynchronous Processing
        ↓
Maintainable Architecture
        ↓
Scalable Application
```

---

# 📌 Project Summary

The **Digital Life Insurance Policy Management System** provides a complete digital workflow for managing insurance policies.

The application starts with **customer registration and secure authentication**, followed by insurance-plan discovery and **illustrative premium calculation**. When a customer creates a policy, it initially remains in **PAYMENT_PENDING** status. After successful payment, the policy becomes **ACTIVE**, and a policy-activation event is published through RabbitMQ for notification processing.

Redis provides caching for frequently accessed information, while JMS supports asynchronous renewal processing. Eureka provides service discovery, and the API Gateway provides a unified entry point to the microservices.

The architecture separates business responsibilities across independent services, making the application modular, maintainable, secure, and suitable for further extension.

---

## 📄 License

This project is intended for educational, training, demonstration, and development purposes.

---

## 👤 Author

**Digital Life Insurance Policy Management System**

Built using Java, Spring Boot, Microservices, PostgreSQL, Redis, RabbitMQ, JMS, and related enterprise technologies.

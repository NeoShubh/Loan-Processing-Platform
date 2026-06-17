# Loan-Processing-Platform
Followed by event driven micro servies architecture

The Loan Processing Platform was developed to address challenges faced by the Risk Control Unit (RCU) during the loan verification process. Traditionally, RCU officers reviewed applicant documents manually and communicated their decisions through emails and spreadsheets. As the number of loan applications increased, managing document verification, tracking decisions, and maintaining visibility across multiple cases became increasingly difficult and error-prone.

To overcome these limitations, the platform introduces a dedicated RCU module within a microservices-based architecture. Whenever a loan application reaches the Credit Evaluation stage, an RCU Case is automatically created. The RCU user can access all documents associated with a loan application, including documents submitted by both primary and secondary applicants. This provides a centralized workspace where verification activities can be performed efficiently without relying on external communication channels.

The RCU officer reviews each document individually and records an approval or rejection decision. Based on these document-level decisions, the system automatically determines the overall verification outcome for the RCU case and updates the corresponding loan application's RCU status. This approach improves traceability, reduces manual effort, accelerates the verification process, and provides a scalable solution for handling a growing volume of loan applications.
# RCU Loan Processing System - Backend Architecture Documentation

## 1. Project Overview

The RCU Loan Processing System is a microservices-based loan processing platform designed to manage loan applications, document verification, risk control review (RCU), and loan lifecycle tracking.

The system follows a distributed architecture using Spring Boot microservices, Spring Cloud API Gateway, Eureka Service Discovery, Apache Kafka Event Streaming, PostgreSQL, MySQL, MongoDB, JWT Authentication, and OpenFeign for inter-service communication.

The architecture is designed to support scalability, loose coupling, secure communication, and efficient handling of both transactional and document-oriented data. Different databases are used based on the nature of the data being stored, while Kafka enables event-driven workflows across services.

---

## 2. Technology Stack

### Backend

* Java 21
* Spring Boot
* Spring Security
* Spring Data JPA
* Spring Data MongoDB
* Spring Cloud Gateway
* OpenFeign
* Apache Kafka
* Eureka Discovery Server

### Databases

* PostgreSQL
* MySQL
* MongoDB

### Authentication

* JWT Authentication
* Role Based Access Control (RBAC)

---

## 3. Microservices

### API Gateway

Responsibilities:

* Single entry point for all client requests
* Request routing to appropriate microservices
* JWT token forwarding and validation support
* Centralized API access management
* Integration with Eureka Service Discovery

Technology:

* Spring Cloud Gateway

---

### Auth User Service

Responsibilities:

* User Registration
* User Login
* JWT Generation
* Role Management
* User Authentication and Authorization

Database:

* PostgreSQL

Roles:

* RM (Relationship Manager)
* CM (Credit Manager)
* RCU (Risk Control Unit)

---

### Loan Service

Responsibilities:

* Create Loan Applications
* Update Loan Stages
* Maintain Loan Stage History
* Publish Loan Lifecycle Events

Database:

* MySQL

Communication:

* Kafka Producer
* Feign Client

---

### Document Service

Responsibilities:

* Upload Documents
* Manage Document Status
* Retrieve Documents
* Associate Documents with Loan Applications and Applicants

Database:

* MongoDB

Communication:

* Feign Client

---

### RCU Service

Responsibilities:

* Create RCU Cases
* Perform Risk Verification
* Review Uploaded Documents
* Track RCU Status

Database:

* MongoDB

Communication:

* Kafka Consumer
* Feign Client

---

## 4. Service Discovery

All microservices register themselves with Eureka Server.

Benefits:

* Dynamic service registration
* Service lookup
* Load balancing support
* Reduced dependency on hardcoded service URLs

Example:

Loan Service → RCU Service

Instead of:

http://localhost:4004

Service Name:

RCU-SERVICE

is used through Eureka Discovery.

---

## 5. Authentication Flow

1. User logs in through the API Gateway.
2. Auth Service validates credentials.
3. JWT token is generated.
4. JWT token is returned to the client.
5. Client sends JWT in the Authorization header for subsequent requests.
6. API Gateway forwards requests to target services.
7. Services validate the JWT using security filters.
8. Access is granted based on assigned roles and permissions.

---

## 6. Synchronous Communication

Technology:
OpenFeign

Used When:

* Immediate response is required.
* Request/Response communication pattern is needed.
* Data must be fetched directly from another service.

Examples:

* RCU Service fetching document details.
* Loan Service calling RCU APIs.
* Inter-service communication requiring real-time responses.

Advantages:

* Simple implementation.
* Real-time data retrieval.
* Easy integration with Eureka Service Discovery.

Disadvantages:

* Service dependency.
* Increased latency.
* Potential cascading failures if dependent services are unavailable.

---

## 7. Asynchronous Communication

Technology:
Apache Kafka

Used When:

* Event-driven processing is required.
* Background operations need to be executed.
* Services should remain loosely coupled.

Topics:

* loan-stage-events
* loan-stage-history-events

Examples:

* Loan stage update triggers automatic RCU case creation.
* Loan stage update creates loan stage history records.

Advantages:

* Loose coupling between services.
* Better scalability.
* Improved fault tolerance.
* Supports asynchronous workflows.

---

## 8. Kafka Event Flow

### Loan Stage History Creation

Loan Service
→ Publish LoanStageHistoryEvent
→ Kafka Topic (loan-stage-history-events)
→ Loan Stage History Consumer
→ Loan Stage History Record Created

---

### RCU Case Creation

Loan Service
→ Update Stage to CREDIT_EVALUATION
→ Publish LoanStageChangedEvent
→ Kafka Topic (loan-stage-events)
→ RCU Consumer
→ Create RCU Case Automatically

---

## 9. Databases

### PostgreSQL

Stores:

* User Information
* Authentication Data
* Role Information

Used By:

* Auth User Service

Reason:

PostgreSQL provides strong transactional consistency and is well-suited for user management and authentication-related data.

---

### MySQL

Stores:

* Loan Applications
* Loan Stage History

Used By:

* Loan Service

Reason:

Loan-related data is highly structured and transactional, making a relational database an ideal choice.

---

### MongoDB

Stores:

* Documents
* RCU Cases

Used By:

* Document Service
* RCU Service

Reason:

Document and RCU data are schema-flexible and suitable for document-oriented storage.

---

## 10. Current Status

Completed:

* API Gateway Setup
* Eureka Service Discovery
* Authentication and Authorization
* JWT Security
* Auth User Service
* Loan Service
* Document Service
* RCU Service
* OpenFeign Integration
* Kafka Integration
* Loan Stage History Tracking
* Automatic RCU Case Creation
* Multi-Database Architecture (PostgreSQL, MySQL, MongoDB)

Pending:

* Frontend UI Development
* API Gateway Enhancements
* Additional Workflow Automation
* Monitoring and Logging Improvements
* Production Deployment
* Containerization and CI/CD Setup

Important Integration Flows

**Loan Creation
**
Loan Service
     ↓
Create Loan Application
     ↓
Kafka Event Published
     ↓
Loan Stage History Consumer
     ↓
Initial Loan Stage History Created

**Loan Stage Change**

RM Changes Stage
     ↓
Loan Service
     ↓
Publish LoanStageChangedEvent
     ↓
Kafka Topic
     ↓
RCU Consumer
     ↓
Create RCU Case


**Document Verification Flow**

RM Uploads Documents
     ↓
Document Service Stores Documents
     ↓
RCU User Opens RCU Case
     ↓
RCU Service Calls Document Service (Feign)
     ↓
Documents Displayed
     ↓
RCU Verifies Each Document
     ↓
Document Status Updated
     ↓
RCU Decision Engine
     ↓
Final Case Decision
     ↓
Loan Application Updated



| Method | Endpoint                                   | Role | Purpose                                                              |
| ------ | ------------------------------------------ | ---- | -------------------------------------------------------------------- |
| POST   | `/api/loans/applicants`                    | RM   | Create a new applicant (Primary or Secondary) for a loan application |
| PUT    | `/api/loans/applicants/{ApplicantId}`      | RM   | Update applicant details                                             |
| DELETE | `/api/loans/applicants/{ApplicantId}`      | RM   | Delete a specific applicant                                          |
| DELETE | `/api/loans/{LoanId}/applicants`           | RM   | Delete all applicants linked to a loan                               |
| GET    | `/api/loans/applicants/{ApplicantId}`      | RM   | Fetch applicant details by Applicant ID                              |
| GET    | `/api/loans/{loanId}/applicants/primary`   | RM   | Fetch primary applicant of a loan                                    |
| GET    | `/api/loans/{loanId}/applicants/secondary` | RM   | Fetch all secondary applicants of a loan                             |
| GET    | `/api/loans/{loanId}/applicants`           | RM   | Fetch all applicants linked to a loan                                |


| Method | Endpoint                                      | Role | Purpose                                                                                                        |
| ------ | --------------------------------------------- | ---- | -------------------------------------------------------------------------------------------------------------- |
| POST   | `/api/loans`                                  | RM   | Create a new loan application                                                                                  |
| GET    | `/api/loans/user/{userId}`                    | RM   | Get all loan applications created by a user                                                                    |
| GET    | `/api/loans/{loanId}`                         | RM   | Fetch loan application details by Loan ID                                                                      |
| PUT    | `/api/loans/{loanId}`                         | RM   | Update loan application details                                                                                |
| PUT    | `/api/loans/{loanId}/stage?loanStage={stage}` | RM   | Update loan processing stage. Also publishes Kafka events for Loan Stage History and downstream services (RCU) |
| DELETE | `/api/loans/{loanId}`                         | RM   | Delete a loan application                                                                                      |
| GET    | `/api/loans/{loanId}/exists`                  | RM   | Check whether a loan application exists                                                                        |


| Method | Endpoint                                     | Role        | Purpose                                            |
| ------ | -------------------------------------------- | ----------- | -------------------------------------------------- |
| POST   | `/api/loans/{loanId}/{userId}/users/history` | RM, CM, RCU | Create a loan stage history record                 |
| GET    | `/api/loans/{loanId}/history`                | RM, CM, RCU | Fetch complete stage transition history for a loan |
| GET    | `/api/loans/history/{loanStageHistoryId}`    | RM, CM, RCU | Fetch a specific loan stage history record         |
| PUT    | `/api/loans/history/{loanStageHistoryId}`    | RM, CM, RCU | Update a loan stage history record                 |
| DELETE | `/api/loans/history/{loanStageHistoryId}`    | RM, CM, RCU | Delete a specific stage history record             |
| DELETE | `/api/loans/{loanId}/history`                | RM, CM, RCU | Delete all stage history records for a loan        |


| Method | Endpoint                                        | Role        | Purpose                                                     |
| ------ | ----------------------------------------------- | ----------- | ----------------------------------------------------------- |
| POST   | `/api/documents`                                | RM          | Upload a new document for an applicant and loan application |
| GET    | `/api/documents/{documentId}`                   | RM, CM, RCU | Fetch document details by Document ID                       |
| PUT    | `/api/documents/{documentId}`                   | RM          | Update document metadata                                    |
| PUT    | `/api/documents/{documentId}/file`              | RM          | Replace uploaded document file                              |
| PUT    | `/api/documents/{documentId}/status`            | RM, CM, RCU | Update document verification status and remarks             |
| DELETE | `/api/documents/{documentId}`                   | RM          | Delete a document                                           |
| DELETE | `/api/documents/loans/{loanId}`                 | RM          | Delete all documents associated with a loan                 |
| DELETE | `/api/documents/loans/applicants/{applicantId}` | RM          | Delete all documents associated with an applicant           |
| GET    | `/api/documents/loans/{loanId}`                 | RM, CM, RCU | Retrieve all documents belonging to a loan                  |
| GET    | `/api/documents/loans/applicants/{applicantId}` | RM, CM, RCU | Retrieve all documents belonging to an applicant            |


| Method | Endpoint                                               | Role    | Purpose                                                                 |
| ------ | ------------------------------------------------------ | ------- | ----------------------------------------------------------------------- |
| POST   | `/api/rcu/cases/{loanId}`                              | RCU     | Create a new RCU case for a loan                                        |
| GET    | `/api/rcu/cases/{rcuCaseId}/getCase`                   | RCU     | Retrieve RCU case details                                               |
| GET    | `/api/rcu/loans/{loanId}`                              | RM, RCU | Retrieve RCU case using Loan ID                                         |
| DELETE | `/api/rcu/cases/{rcuCaseId}`                           | RCU     | Delete an RCU case                                                      |
| PUT    | `/api/rcu/cases/{rcuCaseId}/status?rcuStatus={status}` | RCU     | Update RCU case status                                                  |
| PUT    | `/api/rcu/cases/{rcuCaseId}/assign/{assignedUserId}`   | RCU     | Assign RCU case to a specific RCU user                                  |
| POST   | `/api/rcu/cases/{rcuCaseId}/decision`                  | RCU     | Calculate and finalize overall RCU decision based on document decisions |


| Method | Endpoint                                     | Purpose                                         |
| ------ | -------------------------------------------- | ----------------------------------------------- |
| GET    | `/api/rcu/documents/{documentId}`            | Fetch a document for review                     |
| PUT    | `/api/rcu/documents/{documentId}/status`     | Update document verification status and remarks |
| GET    | `/api/rcu/documents/applicant/{applicantId}` | Fetch all documents of an applicant             |
| GET    | `/api/rcu/loans/{loanId}/documents`          | Fetch all documents belonging to a loan         |


| Method | Endpoint                   | Purpose                   |
| ------ | -------------------------- | ------------------------- |
| GET    | `/api/users/email/{email}` | Fetch user by email       |
| GET    | `/api/users/{userID}`      | Fetch user by User ID     |
| PUT    | `/api/users/{userID}`      | Update user information   |
| DELETE | `/api/users/email/{email}` | Delete user using email   |
| DELETE | `/api/users/id/{userID}`   | Delete user using User ID |

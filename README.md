# Career Copilot

> AI-powered resume analysis and optimization platform built with Java, Spring Boot, PostgreSQL, Spring AI, Ollama, and a microservices architecture.

Career Copilot helps candidates analyze their resumes against job descriptions, identify matching and missing requirements, calculate an ATS-oriented score, and generate an improved structured version of their resume.

The project is designed as an end-to-end backend engineering project demonstrating microservices, REST APIs, database design, AI/LLM integration, document processing, and structured resume generation.

---

## 🚀 Features

### Resume Management

- Upload resumes in PDF format
- Validate uploaded files
- Store uploaded resume files
- Extract text from PDF resumes
- Persist extracted resume content
- Retrieve resume details
- Retrieve extracted resume text
- Delete resumes

### AI-Powered Job Description Analysis

- Analyze job descriptions using Ollama
- Extract job requirements
- Categorize requirements into:
  - Skills
  - Tools
  - Domain Knowledge
  - Responsibilities
  - Education
  - Experience
  - Certifications
  - Other
- Identify mandatory requirements

### ATS Resume Analysis

Career Copilot compares the resume against a job description and generates:

- ATS score
- Matched requirements
- Missing requirements
- Resume strengths
- Improvement suggestions

Example:

```json
{
  "atsScore": 75,
  "matchedKeywords": [
    "Java",
    "Spring Boot",
    "REST APIs"
  ],
  "missingKeywords": [
    "Docker",
    "Microservices"
  ]
}
```

### AI Resume Improvement

The Intelligence Service uses Ollama to generate an improved structured resume based on:

- Original resume content
- Job description
- Identified job requirements

The improved resume is represented as structured JSON and persisted in PostgreSQL using JSONB.

Example:

```json
{
  "professionalSummary": "...",
  "skills": [
    "Java",
    "Spring Boot",
    "PostgreSQL"
  ],
  "experience": [],
  "projects": [],
  "education": [],
  "certifications": []
}
```

### Resume Versioning

Generated resumes are versioned, allowing multiple optimized versions to be maintained for the same original resume.

```text
Original Resume
     |
     +-- Improved Resume v1
     |
     +-- Improved Resume v2
     |
     +-- Improved Resume v3
```

### LaTeX Generation

The structured resume can be converted into LaTeX.

```text
ImprovedResume JSON
        |
        v
LaTeX Generator
        |
        v
LaTeX Source
```

The generated LaTeX can be copied into Overleaf for PDF compilation.

---

# 🏗️ Architecture

Career Copilot follows a microservices-oriented architecture.

```text
                         +----------------+
                         |     Client     |
                         +-------+--------+
                                 |
                                 v
                     +----------------------+
                     |     Auth Service     |
                     +----------+-----------+
                                |
                                |
              +-----------------+-----------------+
              |                                   |
              v                                   v
    +-------------------+              +----------------------+
    |   Resume Service  |              | Intelligence Service |
    +---------+---------+              +----------+-----------+
              |                                   |
              v                                   |
        +-----------+                             |
        | PostgreSQL|                             |
        | Resume DB |                             |
        +-----------+                             |
                                                  |
                                                  v
                                          +---------------+
                                          |    Ollama     |
                                          |   Llama 3.1   |
                                          +---------------+
                                                  |
                                                  v
                                          +---------------+
                                          |  PostgreSQL   |
                                          | Intelligence  |
                                          |      DB       |
                                          +---------------+
```

### Service Responsibilities

```text
Auth Service
     |
     +-- Authentication
     +-- User management
     +-- JWT generation


Resume Service
     |
     +-- Resume upload
     +-- PDF processing
     +-- Text extraction
     +-- Resume persistence
     +-- Resume retrieval


Intelligence Service
     |
     +-- Job analysis
     +-- ATS analysis
     +-- Resume matching
     +-- AI resume improvement
     +-- Generated resume persistence
     +-- LaTeX generation
```

---

# 🔧 Microservices

## 1. Auth Service

Responsible for authentication-related functionality.

### Responsibilities

- User registration/login
- Authentication
- JWT generation
- User identity management

Authentication and authorization integration across all services is planned for a future iteration.

---

## 2. Resume Service

Responsible for resume management and document processing.

### Responsibilities

- Resume upload
- PDF validation
- File storage
- PDF text extraction
- Resume persistence
- Resume retrieval
- Resume deletion
- Exposing extracted resume text to the Intelligence Service

### Technology

- Java 17
- Spring Boot
- Spring Data JPA
- PostgreSQL
- Apache PDFBox
- Flyway

---

## 3. Intelligence Service

Responsible for AI-powered resume intelligence.

### Responsibilities

- Job description analysis
- Requirement extraction
- Resume-to-job matching
- ATS scoring
- Resume improvement
- Generated resume persistence
- Resume versioning
- LaTeX generation

### Technology

- Java 17
- Spring Boot
- Spring AI
- Ollama
- Llama 3.1
- PostgreSQL
- Hibernate
- Flyway

---

# 🛠️ Technology Stack

| Technology | Purpose |
|---|---|
| Java 17 | Backend development |
| Spring Boot | Microservices framework |
| Spring Data JPA | Database access |
| Spring AI | AI integration |
| Ollama | Local LLM execution |
| Llama 3.1 | AI model |
| PostgreSQL | Persistent storage |
| Flyway | Database migrations |
| Maven | Build and dependency management |
| Docker | Containerization |
| Git | Version control |
| REST APIs | Service communication |
| Apache PDFBox | PDF text extraction |
| Lombok | Boilerplate reduction |

---

# 📂 Project Structure

```text
career-copilot/
│
├── auth-service/
│
├── resume-service/
│
├── intelligence-service/
│
├── docker/
│
└── README.md
```

Typical service structure:

```text
src/
└── main/
    ├── java/
    │   └── com.careercopilot/
    │       ├── controller/
    │       ├── service/
    │       ├── repository/
    │       ├── entity/
    │       ├── dto/
    │       ├── analyzer/
    │       ├── client/
    │       ├── exception/
    │       └── config/
    │
    └── resources/
        ├── application.yml
        └── db/
            └── migration/
```

---

# 🔄 Application Flow

## 1. Resume Upload

```text
Client
  |
  | PDF
  v
Resume Service
  |
  +-- Validate PDF
  |
  +-- Store file
  |
  +-- Extract text
  |
  +-- Save metadata + extracted text
  |
  v
Resume Response
```

---

## 2. Resume Analysis

```text
Client
  |
  | Resume ID + Job Description
  v
Intelligence Service
  |
  +----------------------+
  |                      |
  v                      v
Resume Service       Ollama
  |                      |
  | Resume Text          | Job Requirements
  |                      |
  +----------+-----------+
             |
             v
      Requirement Matcher
             |
             v
        ATS Analysis
             |
             +-- ATS Score
             +-- Matched Requirements
             +-- Missing Requirements
             +-- Strengths
             +-- Suggestions
             |
             v
       PostgreSQL
```

---

# 🤖 AI Resume Improvement

The improvement pipeline is:

```text
Original Resume
       |
       v
Resume Service
       |
       | Extracted Text
       v
Intelligence Service
       |
       +----------------+
       |                |
       v                v
Job Analysis        Resume Content
       |                |
       +-------+--------+
               |
               v
             Ollama
               |
               v
       ImprovedResume
        Structured JSON
               |
               v
          PostgreSQL
             JSONB
```

The structured resume contains:

```text
ImprovedResume
├── professionalSummary
├── skills
├── experience
├── projects
├── education
└── certifications
```

---

# 📡 API Endpoints

## Resume Service

### Upload Resume

```http
POST /api/v1/resumes
Content-Type: multipart/form-data
```

Uploads a PDF resume.

### Get Resume

```http
GET /api/v1/resumes/{resumeId}
```

### Get All Resumes

```http
GET /api/v1/resumes
```

### Get Resume Text

```http
GET /api/v1/resumes/{resumeId}/text
```

Used by the Intelligence Service to retrieve extracted resume text.

### Delete Resume

```http
DELETE /api/v1/resumes/{resumeId}
```

---

# Intelligence Service APIs

## Analyze Resume

```http
POST /api/v1/analyses
```

Request:

```json
{
  "resumeId": "6c2d290c-a2df-4ece-8ef6-9ce29074759e",
  "jobDescription": "Java Backend Developer with Spring Boot, PostgreSQL, Docker and Microservices."
}
```

The response contains:

- ATS score
- Matched requirements
- Missing requirements
- Strengths
- Suggestions

---

## Get Analysis

```http
GET /api/v1/analyses/{analysisId}
```

Returns the persisted resume analysis.

---

## Improve Resume

```http
POST /api/v1/analyses/{analysisId}/improve
```

No request body is required.

The endpoint:

1. Loads the existing analysis
2. Retrieves resume text from the Resume Service
3. Sends the resume content and job context to Ollama
4. Generates an `ImprovedResume`
5. Stores the structured resume as PostgreSQL JSONB
6. Creates a generated resume version

---

## Get Generated Resume

```http
GET /api/v1/generated-resumes/{generatedResumeId}
```

Returns a generated resume and its structured content.

---

## Get Generated Resume Versions

```http
GET /api/v1/generated-resumes/resume/{resumeId}
```

Returns generated resume versions associated with the resume.

Versions are returned in descending version order.

Example:

```text
Version 3
Version 2
Version 1
```

---

## Generate LaTeX

```http
POST /api/v1/generated-resumes/{generatedResumeId}/latex
```

No request body is required.

The endpoint converts the structured resume into LaTeX and persists the generated LaTeX.

---

# 🗄️ Database Design

Each service owns its database.

```text
Resume Service
      |
      v
resume_db


Intelligence Service
      |
      v
intelligence_db
```

The services do not share database tables.

## Resume Database

The Resume Service stores:

- Resume ID
- User ID
- Original filename
- Stored filename
- File path
- Content type
- File size
- Extracted text
- Created timestamp
- Updated timestamp

Primary table:

```text
resumes
```

## Intelligence Database

The Intelligence Service stores:

```text
resume_analyses
```

and:

```text
generated_resumes
```

The improved structured resume is stored as PostgreSQL `JSONB`.

---

# 🐳 Docker

PostgreSQL can be started using Docker Compose.

```bash
docker compose up -d
```

Check running containers:

```bash
docker ps
```

Stop containers:

```bash
docker compose down
```

---

# 🧠 Ollama

Career Copilot uses Ollama for local AI processing.

Verify installed models:

```bash
ollama list
```

Pull the configured model:

```bash
ollama pull llama3.1
```

Run the model:

```bash
ollama run llama3.1
```

The Intelligence Service communicates with Ollama using Spring AI.

---

# ⚙️ Configuration

Example Resume Service configuration:

```yaml
server:
  port: 8082

spring:
  application:
    name: resume-service

  datasource:
    url: jdbc:postgresql://localhost:5432/resume_db
    username: postgres
    password: ${POSTGRES_PASSWORD}
    driver-class-name: org.postgresql.Driver

  jpa:
    hibernate:
      ddl-auto: validate
    show-sql: true

  flyway:
    enabled: true
    locations: classpath:db/migration
```

Example Intelligence Service configuration:

```yaml
server:
  port: 8083

spring:
  application:
    name: intelligence-service

  datasource:
    url: jdbc:postgresql://localhost:5432/intelligence_db
    username: postgres
    password: ${POSTGRES_PASSWORD}
    driver-class-name: org.postgresql.Driver

  jpa:
    hibernate:
      ddl-auto: validate
    show-sql: true

  flyway:
    enabled: true
    locations: classpath:db/migration
```

Ollama configuration depends on the local Ollama installation and Spring AI configuration used by the Intelligence Service.

---

# 🚀 Running the Project

## Prerequisites

Install:

- Java 17+
- Maven
- Docker
- PostgreSQL or Docker
- Ollama
- Git

## 1. Clone the Repository

```bash
git clone <your-repository-url>
cd career-copilot
```

## 2. Start PostgreSQL

```bash
docker compose up -d
```

Verify:

```bash
docker ps
```

## 3. Start Ollama

Verify the model:

```bash
ollama list
```

If the model is not available:

```bash
ollama pull llama3.1
```

## 4. Start Resume Service

```bash
cd resume-service
mvn spring-boot:run
```

## 5. Start Intelligence Service

Open another terminal:

```bash
cd intelligence-service
mvn spring-boot:run
```

---

# 🧪 Testing

The APIs can be tested using:

- Postman
- IntelliJ HTTP Client
- cURL

Recommended end-to-end flow:

```text
1. Upload Resume
       ↓
2. Get Resume
       ↓
3. Submit Job Description
       ↓
4. Analyze Resume
       ↓
5. Get Analysis
       ↓
6. Improve Resume
       ↓
7. Get Generated Resume
       ↓
8. Get Generated Resume Versions
       ↓
9. Generate LaTeX
```

---

# 🔐 Error Handling

The Intelligence Service includes centralized exception handling.

Handled cases include:

- Analysis not found
- Generated resume not found
- Resume Service unavailable
- AI/Ollama unavailable
- Invalid request
- Invalid UUID
- Malformed JSON

Example:

```json
{
  "timestamp": "2026-08-12T18:00:00",
  "status": 404,
  "message": "Generated resume not found",
  "data": null
}
```

External service failures are translated into appropriate API responses rather than exposing internal stack traces.

---

# 🧩 Design Principles

The project follows several backend engineering principles:

- Microservices architecture
- Database-per-service
- Separation of concerns
- Layered architecture
- DTO-based API contracts
- Repository pattern
- Service layer abstraction
- Centralized exception handling
- Database migrations with Flyway
- RESTful API design
- Structured AI output
- PostgreSQL JSONB for flexible AI-generated data
- Resume versioning
- Service-to-service REST communication

---

# 📈 Current Architecture

The current implementation focuses on building the core resume intelligence pipeline:

```text
Microservices
      +
REST APIs
      +
PostgreSQL
      +
PDF Processing
      +
AI/LLM Integration
      +
Structured Resume Generation
      +
LaTeX Generation
```

The system is being developed incrementally, with security, infrastructure, and production hardening planned as subsequent iterations.

---

# 🔮 Future Improvements

## Authentication & Authorization

- Complete JWT-based authentication across services
- Resume ownership enforcement
- Resource-level authorization
- JWT propagation between services
- Secure service-to-service communication

## AI Reliability

- Stronger structured-output validation
- AI response validation
- Retry mechanisms
- Timeout handling
- Better hallucination prevention
- Resume fact validation
- Improved prompt management

## Resume Generation

- Deterministic LaTeX templates
- Multiple resume templates
- ATS-friendly formatting
- PDF generation
- Resume preview
- Direct PDF download
- Customizable resume layouts

## Architecture

- API Gateway
- Service discovery
- Centralized configuration
- Kafka-based asynchronous processing
- Redis caching
- Distributed tracing
- Centralized logging
- Observability

## DevOps

- Fully containerized services
- CI/CD pipeline
- Automated integration tests
- Test environments
- Cloud deployment
- Production monitoring

---

# 🧪 Testing Strategy

Planned testing layers include:

```text
Unit Tests
    |
    +-- Service layer
    +-- ATS scoring
    +-- Requirement matching
    +-- Utility classes
          |
          v
Integration Tests
    |
    +-- Database
    +-- Resume Service
    +-- Intelligence Service
          |
          v
API Tests
    |
    +-- Resume APIs
    +-- Analysis APIs
    +-- Generated Resume APIs
```

---

# 📌 Project Status

## Completed

- [x] Resume Service
- [x] PDF resume upload
- [x] PDF validation
- [x] PDF text extraction
- [x] Resume persistence
- [x] Resume retrieval
- [x] Resume text extraction endpoint
- [x] Resume deletion
- [x] Job description analysis
- [x] AI requirement extraction
- [x] Requirement categorization
- [x] ATS matching
- [x] ATS scoring
- [x] Missing requirement detection
- [x] Resume strengths
- [x] Resume improvement suggestions
- [x] AI-powered resume improvement
- [x] Structured resume JSON
- [x] PostgreSQL JSONB persistence
- [x] Generated resume versioning
- [x] Generated resume retrieval
- [x] Generated resume history
- [x] LaTeX generation
- [x] Global exception handling
- [x] Input validation
- [x] Resume Service communication error handling
- [x] Ollama/AI error handling

## Planned

- [ ] Authentication integration across services
- [ ] Resource-level authorization
- [ ] JWT propagation between services
- [ ] AI output validation and hallucination prevention
- [ ] Deterministic LaTeX templates
- [ ] PDF generation
- [ ] Automated test suite
- [ ] API Gateway
- [ ] CI/CD
- [ ] Centralized logging and observability
- [ ] Production deployment

---

# 🎯 End-to-End Use Case

A typical Career Copilot workflow looks like:

```text
                    USER
                      |
                      v
               Upload Resume
                      |
                      v
               Resume Service
                      |
                      v
              Extract PDF Text
                      |
                      v
             Submit Job Description
                      |
                      v
            Intelligence Service
                      |
            +---------+---------+
            |                   |
            v                   v
      Resume Content       Job Description
            |                   |
            +---------+---------+
                      |
                      v
                    Ollama
                      |
                      v
                Job Analysis
                      |
                      v
              Requirement Matching
                      |
                      v
                 ATS Score
                      |
                      v
                Resume Analysis
                      |
                      v
                Improve Resume
                      |
                      v
              ImprovedResume JSON
                      |
                      v
              PostgreSQL JSONB
                      |
                      v
             Generated Resume
                      |
                      v
               LaTeX Generation
```

---

# 💡 Why Career Copilot?

Career Copilot is built to demonstrate more than basic CRUD operations.

The project combines:

```text
Backend Engineering
        +
Microservices
        +
REST APIs
        +
Database Design
        +
PDF Processing
        +
AI/LLM Integration
        +
Structured Data
        +
Resume Optimization
        +
Document Generation
```

It demonstrates how an AI capability can be integrated into a backend system while maintaining clear service boundaries, persistence, API contracts, and extensibility.

---

# 👨‍💻 Author

**Abhishek Dubey**

Computer Science Engineer | Java | Spring Boot | Backend Development | SDET

---

## ⭐ Project Highlights

- Java 17 backend
- Spring Boot microservices
- PostgreSQL database-per-service architecture
- REST-based service communication
- Spring AI + Ollama integration
- Local LLM processing
- PDF text extraction
- ATS-oriented resume analysis
- Structured AI-generated resume data
- PostgreSQL JSONB persistence
- Resume versioning
- LaTeX generation
- Centralized exception handling
- Incremental production-oriented architecture

---

## 📄 License

This project is currently intended as a personal portfolio and learning project.

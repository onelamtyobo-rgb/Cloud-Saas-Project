# ☁️ Cloud Observe

**Cloud Observe** is a Cloud Readiness and Compliance Assessment SaaS application. It allows companies to capture their infrastructure details, run cloud readiness or compliance assessments, and receive automated scoring and migration recommendations.

---

## 🚀 Features

- **Company Management** — Create and manage company profiles (industry, employee count, current setup).
- **Cloud Assessments**
  - **Readiness Assessment** — Evaluates a company's readiness for cloud migration.
  - **Compliance & Governance** — Evaluates regulatory and governance compliance.
- **Automated Scoring & Recommendations** — Generates a score (0–100) with tailored recommendations based on assessment answers.
- **Infrastructure Tracking** — Log current physical/virtual infrastructure resources.
- **RESTful API** — Fully documented backend API with Swagger/OpenAPI integration.
- **Comprehensive Test Suite** — Includes both integration (acceptance) and unit tests.

---

## 🛠️ Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 17 |
| Framework | Spring Boot 3.2.0 |
| Build Tool | Maven |
| Database | H2 (in-memory relational database) |
| ORM | Spring Data JPA |
| API Documentation | Swagger / OpenAPI (SpringDoc) |
| Frontend | Vanilla JavaScript, HTML5, CSS3 |

---

## ⚙️ Prerequisites

Before running the application, ensure you have the following installed:

- Java Development Kit (JDK) 17 or higher
- Apache Maven 3.6+

---

## 🏃 How to Run the Application

**1. Clone the repository**

```bash
git clone <your-repository-url>
cd Cloud-Saas-Project
```

**2. Build and run the backend**

```bash
mvn clean install
mvn spring-boot:run
```

**3. Access the application**

- Frontend UI: [http://localhost:8080](http://localhost:8080)
- Swagger API Docs: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html) — view and test API endpoints interactively.

---

## 🔌 API Endpoints

### Companies

| Method | Endpoint | Description |
|---|---|---|
| `GET` | `/api/companies` | Get all companies |
| `GET` | `/api/companies/{id}` | Get a specific company |
| `POST` | `/api/companies` | Create a new company |
| `PUT` | `/api/companies/{id}` | Update a company |
| `DELETE` | `/api/companies/{id}` | Delete a company |
| `POST` | `/api/companies/{id}/infrastructure` | Add infrastructure to a company |

### Assessments

| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/api/assessments/company/{companyId}?type={READINESS\|COMPLIANCE}` | Create a new assessment |
| `POST` | `/api/assessments/{id}/complete` | Submit answers and complete the assessment |
| `GET` | `/api/assessments/{id}` | Get assessment details |
| `GET` | `/api/assessments/company/{companyId}` | Get all assessments for a company |

---

## 🧪 Running Tests

The project includes a comprehensive test suite covering both business logic (unit tests) and API endpoints (acceptance/integration tests).

To run all tests:

```bash
mvn test
```

**Test coverage includes:**

- `CreateCompanyAssessmentTest` — End-to-end company and assessment creation
- `CloudReadinessAssessmentTest` — Validates low-score readiness logic
- `MigrationRoadmapTest` — Validates high-score readiness logic
- `CloudProductRecommendationTest` — Validates compliance assessment logic
- `InfrastructureApiTest` — Validates infrastructure attachment
- `AssessmentServiceTest` — Unit tests for scoring and recommendation algorithms

---

## 👤 Author

- **Name**: Onela Zandile Mtyobo
- **Student Number**: onmtyjhb025
- **Institution**: WeThinkCode_

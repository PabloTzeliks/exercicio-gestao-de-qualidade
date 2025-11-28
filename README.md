# Equipment & Failure Management System

A backend application built with **pure JDBC** for Equipment and Failure management, featuring advanced SQL techniques and clean architecture.

## 📋 Description

This project is a **practical academic activity** focused on mastering core Java concepts including:

- **JDBC (Java Database Connectivity)** - Direct database access without ORM frameworks
- **Clean Code Principles** - Well-structured, readable, and maintainable code
- **Complex SQL Manipulations** - Advanced queries involving JOINs, aggregations, and filtering

The primary goal was to implement a complete backend solution that satisfies a **rigorous suite of Unit Tests** provided by the instructor, demonstrating proficiency in database operations and software design patterns.

---

## 🛠️ Technologies Used

| Technology | Purpose |
|------------|---------|
| **Java (Core)** | Primary programming language |
| **JDBC** | Java Database Connectivity for direct SQL operations |
| **MySQL** | Relational database management system |
| **SQL** | Advanced queries (JOINs, GROUP BY, HAVING, Aggregations) |
| **JUnit 5** | Unit and integration testing framework |
| **Maven** | Project build and dependency management |

---

## 📁 Project Structure

```
src/
├── main/java/org/example/
│   ├── database/
│   │   └── Conexao.java              # Database connection management
│   ├── dto/
│   │   ├── EquipamentoContagemFalhasDTO.java   # Equipment failure count DTO
│   │   ├── FalhaDetalhadaDTO.java              # Detailed failure DTO
│   │   └── RelatorioParadaDTO.java             # Downtime report DTO
│   ├── model/
│   │   ├── AcaoCorretiva.java        # Corrective action entity
│   │   ├── Equipamento.java          # Equipment entity
│   │   └── Falha.java                # Failure entity
│   ├── repository/
│   │   ├── acaocorretiva/
│   │   │   └── AcaoCorretivaRepository.java
│   │   ├── equipamento/
│   │   │   └── EquipamentoRepository.java
│   │   ├── falha/
│   │   │   └── FalhaRepository.java
│   │   └── relatorio/
│   │       └── RelatorioRepository.java
│   └── service/
│       ├── acaocorretiva/
│       │   ├── AcaoCorretivaService.java
│       │   └── AcaoCorretivaServiceImpl.java
│       ├── equipamento/
│       │   ├── EquipamentoService.java
│       │   └── EquipamentoServiceImpl.java
│       ├── falha/
│       │   ├── FalhaService.java
│       │   └── FalhaServiceImpl.java
│       └── relatorioservice/
│           ├── RelatorioService.java
│           └── RelatorioServiceImpl.java
└── test/java/
    ├── AcaoCorretivaServiceIntegrationTest.java
    ├── EquipamentoServiceIntegrationTest.java
    ├── FalhaServiceIntegrationTest.java
    ├── RelatorioServiceTest.java
    └── TestUtils.java
```

### Architecture Overview

| Layer | Description |
|-------|-------------|
| **Model** | Entity classes representing database tables (Equipamento, Falha, AcaoCorretiva) |
| **DTO** | Data Transfer Objects for complex query results and reports |
| **Repository** | Data access layer implementing the Repository pattern with pure JDBC |
| **Service** | Business logic layer with interfaces and implementations |
| **Database** | Connection management using JDBC DriverManager |

---

## ⚡ Key Features & Implementation Details

### 🔐 Advanced JDBC

- **PreparedStatement Usage**: All database operations use `PreparedStatement` for:
  - **SQL Injection Prevention**: Parameterized queries protect against injection attacks
  - **Performance Optimization**: Statement caching and efficient parameter binding
  - **Type Safety**: Proper Java-to-SQL type mapping

### 🔗 Complex SQL Queries

- **One-to-Many Mapping**: Fetching Equipment with associated Failures and Corrective Actions
- **JOIN Operations**: Complex queries joining multiple tables
- **Aggregation Functions**: SUM, COUNT for statistical reports
- **GROUP BY with HAVING**: Filtering equipment by failure count thresholds
- **Subqueries**: Finding equipment without failures in specific periods

Example of advanced query implementation:
```sql
SELECT e.id, e.nome, COUNT(f.id) as totalFalhas
FROM Equipamento e
JOIN Falha f ON e.id = f.equipamentoId
GROUP BY e.id, e.nome
HAVING totalFalhas >= ?;
```

### 🧹 Clean Code Practices

- **Interface Segregation**: Service interfaces define contracts, implementations handle logic
- **Single Responsibility**: Each class has a focused purpose
- **Method Separation**: Complex operations broken into manageable methods
- **Exception Handling**: Proper error handling with meaningful exception messages
- **Consistent Naming**: Clear, descriptive variable and method names in Portuguese

### 📊 Business Logic Highlights

- **Equipment Status Management**: Automatic status updates based on failure criticality
- **Critical Failure Handling**: Equipment marked as `EM_MANUTENCAO` when critical failures occur
- **Resolution Workflow**: Corrective actions automatically resolve failures and restore equipment status

---

## ✅ Unit Tests

This project was built to pass a comprehensive **Unit Test suite** provided by the instructor. The tests validate:

| Test Class | Coverage |
|------------|----------|
| `EquipamentoServiceIntegrationTest` | Equipment CRUD operations and validation |
| `FalhaServiceIntegrationTest` | Failure registration and status management |
| `AcaoCorretivaServiceIntegrationTest` | Corrective action workflow and cascading updates |
| `RelatorioServiceTest` | Report generation with complex queries |

### ✨ Test Results

The implementation achieves a **100% pass rate** on all provided tests, demonstrating:

- Correct business logic implementation
- Proper exception handling
- Accurate database operations
- Valid status transitions

---

## 🚀 How to Run

### Prerequisites

- **Java 22** or higher
- **MySQL 8.0** or higher
- **Maven 3.6** or higher

### Database Setup

1. Create a MySQL database:
```sql
CREATE DATABASE sql_db;
```

2. Configure the connection in `src/main/java/org/example/database/Conexao.java`:
```java
private static String URL = "jdbc:mysql://localhost:3306/sql_db?useSSL=false&serverTimezone=UTC";
private static String USER = "your_username";
private static String PASSWORD = "your_password";
```

> **Note**: The test suite automatically creates and manages the required tables (Equipamento, Falha, AcaoCorretiva).

### Running the Tests

```bash
# Clone the repository
git clone https://github.com/PabloTzeliks/exercicio-gestao-de-qualidade.git
cd exercicio-gestao-de-qualidade

# Run all tests
mvn test

# Run specific test class
mvn test -Dtest=EquipamentoServiceIntegrationTest
```

### Building the Project

```bash
# Compile the project
mvn compile

# Package as JAR
mvn package
```

---

## 👨‍💻 Credits

* **Development & Implementation**: [PabloTzeliks](https://github.com/PabloTzeliks) - Responsible for the entire JDBC implementation, logic solution, and ensuring all tests pass with clean code.
* **Academic Guidance & Test Suite**: [Prof. Vinicius Trindade](https://github.com/profviniciustrindade) - Provided the project specifications and the Unit Test suite to validate the solution.

---

## 📄 License

This project is for educational purposes as part of a Quality Management course.

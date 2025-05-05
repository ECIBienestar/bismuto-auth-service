# ECI-Bienestar Authentication Service

Authentication microservice for the ECI-Bienestar platform. Handles user authentication, authorization, JWT token management, and security for Escuela Colombiana de Ingeniería Julio Garavito's wellness system.

## 👥 Authors

* Andrés Felipe Chavarro Plazas
* Andrés Jacobo Sepúlveda Sánchez
* Camilo Andrés Fernández Días
* Jesús Alfonso Pinzón Vega

## 📌 Project Overview
This microservice is part of the ECI-Bienestar integrated platform designed for Escuela Colombiana de Ingeniería Julio Garavito. The Authentication Service is built using **Spring Boot** and provides secure user authentication, JWT token management, and role-based access control for the wellness system.

## 🛠️ Technologies Used
- **Java 17**
- **Spring Boot 3.x** (Spring Security, Spring Web)
- **MongoDB** (NoSQL Database)
- **Maven**
- **Lombok**
- **JUnit 5 & Mockito** (for testing)
- **JaCoCo** (for code coverage)
- **SonarCloud** (for code quality)
- **JWT** (for secure authentication)

## 📂 Project Structure

```
bismuto-auth-service/
├── pom.xml
├── .gitignore
├── README.md
├── assets/
└── src/
    ├── main/
    │   ├── java/
    │   │   └── edu/eci/cvds/auth/
    │   │       ├── AuthServiceApplication.java
    │   │       ├── config/            # Configuration classes
    │   │       ├── controller/        # REST Controllers
    │   │       ├── dto/               # Data Transfer Objects
    │   │       │   └── enum/          # Enumerations
    │   │       ├── exception/         # Custom Exception Handling
    │   │       ├── model/             # Entity Classes
    │   │       ├── security/          # Security configurations and JWT handling
    │   │       ├── service/           # Business Logic Services
    │   │       └── util/              # Utility classes
    │   └── resources/
    │       ├── application.properties       # Default configuration
    │       ├── application-dev.properties   # Development configuration
    │       ├── application-prod.properties  # Production configuration
    │       ├── static/                      # Static resources
    │       └── templates/                   # Templates
    └── test/
        └── java/
            └── edu/eci/cvds/auth/
                ├── AuthServiceApplicationTest.java
                ├── controller/       # Controller Tests
                ├── dto/              # DTO Tests
                │   └── enum/         # Enum Tests
                ├── exception/        # Exception Tests
                ├── model/            # Model Tests
                ├── security/         # Security Tests
                ├── service/          # Service Tests
                └── util/             # Utility Tests
```

## 🚀 How to Run the Project

### Prerequisites
- Install **Java 17**
- Install **Maven**
- Set up a **MongoDB** database

### Steps to Run

1. Clone the repository:
   ```bash
   git clone https://github.com/ECIBienestar/bismuto-auth-service.git
   cd bismuto-auth-service
   ```

2. Configure database connection in `application.properties`:
   ```properties
   spring.data.mongodb.uri=mongodb://localhost:27017/eci_bienestar_auth
   spring.data.mongodb.database=eci_bienestar_auth
   ```

3. Build and run the application:
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```


## 📑 Diagrams
Some of the diagrams proposed for this module are the following, which will help us to have a clear idea of ​​how the system works and is constructed:


### Data
Shows the structure of data within the system, including entities, attributes, and their relationships. It is useful for database design and understanding how data is organized and linked.

![DiagramData](DiagramaEndebido.png)


### Components
Illustrates the physical and software components of a system and how they are grouped and connected. It helps visualize the architecture and module distribution of the application.

![DiagramComponents](DiagramaComponentesAutentificacion.png)


### Secuence
Describes how objects interact in a specific sequence of events over time. It focuses on the order of messages exchanged between objects to accomplish particular use cases or functions.

![DiagramSecuence](DiagramaSecuenciaEstadisticas.png)


## 📌 API Endpoints

### Authentication

| Method | Endpoint               | Description                               |
|--------|------------------------|-------------------------------------------|
| POST   | /api/auth/login        | Authenticate user and generate JWT token  |
| POST   | /api/auth/register     | Register a new user                       |
| POST   | /api/auth/refresh      | Refresh an existing JWT token             |
| POST   | /api/auth/logout       | Invalidate user's JWT token               |

### Authorization

| Method | Endpoint                 | Description                             |
|--------|--------------------------|-----------------------------------------|
| GET    | /api/auth/validate       | Validate a JWT token                    |
| GET    | /api/auth/permissions    | Get user permissions                    |
| GET    | /api/auth/roles          | Get available roles                     |

## 🧪 Running Tests

To run the unit tests:
```bash
mvn test
```

To generate test coverage report:
```bash
mvn test jacoco:report
```

## 🔄 CI/CD

This project uses GitHub Actions to automate building, testing, and deployment:

- **Development Pipeline**: Triggered by commits to `develop` and `feature/*` branches, automatically deploying to the development environment.
- **Production Pipeline**: Triggered by commits to the `main` branch, deploying to the production environment after successful tests.

Each pipeline consists of three stages:
1. **Build**: Compiles the application and creates the JAR package
2. **Test**: Runs unit tests and generates code coverage reports
3. **Deploy**: Deploys the application to the AWS environment

Configuration files are located in the `.github/workflows/` directory.

## 🔄 Integration with Other Modules

This authentication service integrates with other ECI-Bienestar modules:
- **User Management Service**: For user profile information and role management
- **Statistics Service**: For logging authentication events
- **All other services**: For validating user access and permissions

## 🏗️ Future Improvements

- Multi-factor authentication
- OAuth2 integration for third-party authentication
- Enhanced security auditing and monitoring
- Password policy management

## 📝 License

This project is licensed under the MIT License.

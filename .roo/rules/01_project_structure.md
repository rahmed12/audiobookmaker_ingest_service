# Spring Boot Folder Structure Prompt (Best Practices)

You are a senior Java Spring Boot architect. Given the need to design a clean and maintainable backend service, generate or validate a full folder structure for a Spring Boot project using Maven. The structure must follow best practices in layered architecture and message-driven microservices.

---

## Folder Structure Rules

### 1. `@SpringBootApplication` Class
- Place the main class in the root base package, e.g., `com.company.project`
- Ensures component scanning picks up all sub-packages

### 2. `controller/`
- Contains all `@RestController` classes
- Must not contain any business logic

### 3. `service/impl`
- Contains business logic in `@Service` classes
- May depend on repositories or utility classes
- Must not call or depend on controllers
- `service` cointains the interface of the impl classes if it uses an interface


### 4. `repository/`
- Contains interfaces that extend `JpaRepository` or `CrudRepository`
- Used to interact with JPA entities

### 5. `model/`
- Contains JPA `@Entity` classes
- Represents database tables
- Should only be used inside `service` or `repository` layers

### 6. `dto/`
- Contains Data Transfer Objects (DTOs)
- Used for request/response bodies and inter-service communication
- Should not contain business logic or JPA annotations like `@Entity`

### 7. `config/`
- Contains `@Configuration` and `@Enable...` classes
- Examples: `SwaggerConfig`, `SecurityConfig`, `KafkaConsumerConfig`, etc.

### 8. `util/`
- Contains stateless utility/helper classes shared across layers

### 9. `messaging/consumer/`
- Contains message listener classes (e.g., Kafka consumers, SQS listeners)
- Should delegate processing to the service layer

### 10. `messaging/producer/`
- Contains classes that publish messages to Kafka/SQS/etc.

---

## Required Files in `src/main/resources/`

- `application.yml`
- `application-local.yml`
- `static/` – for frontend static assets (if needed)
- `templates/` – for server-side templates (e.g., Thymeleaf, if used)
- `messages.properties` – for i18n/validation messages

---

## Summary

Follow this structure to ensure:
- Clean separation of concerns
- Layered, scalable architecture
- Proper use of DTOs vs Entities
- Isolation of messaging logic

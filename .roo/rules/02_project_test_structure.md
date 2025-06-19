## JUnit Test Folder Structure

JUnit test classes should be placed in `src/test/java` and mirror the package structure of `src/main/java`. This makes it easier to maintain test coverage and traceability.

### Guidelines

- Test class names should match the class under test (e.g., `UserService` → `UserServiceTest`).
- Place test classes in the corresponding package in `src/test/java`, such as `service/`, `controller/`, or `messaging/consumer/`.
- Separate integration tests under an `integration/` subpackage or in a separate module like `project-integration-test`.

### Test Resources

Place test configurations and mock data in `src/test/resources`, including:
- `application-test.yml`
- Static JSON or YAML files under `data/` and `mocks/`

### Annotations

- Use `@SpringBootTest` for integration tests
- Use `@WebMvcTest` and `@MockBean` for controller tests
- Use `@DataJpaTest` for repository tests

---

## Summary

This structure ensures:
- Clean separation of concerns
- Layered, scalable and modular architecture
- Clear use of DTOs vs Entities
- Isolation of messaging concerns
- Testable and maintainable codebase

## Best Practices
- Each test should be isolated and independent to avoid side effects between runs.
- Use descriptive test method names that clearly express the scenario and expected outcome.
- Mock external dependencies such as REST clients, databases, or message queues using `@MockBean`, `Mockito`, or `TestContainers` where appropriate.
- Avoid testing internal implementation details; focus on public behavior and business outcomes.
- Treat the system-under-test like a black box: verify its inputs and outputs, not its internal steps.
- Do not mock or override the Logger. Let it behave naturally to preserve logging semantics and aid debugging.
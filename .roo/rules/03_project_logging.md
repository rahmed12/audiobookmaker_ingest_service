
# Spring Boot Logging Best Practices (RooCode Rule)

This guideline defines standardized logging practices for Spring Boot applications using SLF4J with:
private static final Logger logger = LoggerFactory.getLogger(YourClass.class);


## Logger Usage

- Use SLF4J's `LoggerFactory.getLogger(...)` with `private static final Logger` declaration.
- Do **not** use `System.out.println`, `System.err.println`, or `e.printStackTrace()`.
- Log messages should be **parameterized** and **free of sensitive data** (e.g., passwords, secrets).

## Log Levels and When to Use Them

### `debug`
- **Purpose**: Detailed internal information for debugging during development.
- **When to use**:
  - Logging variable values
  - Method entry/exit tracing
  - Payloads for non-sensitive test data

**Examples**:

logger.debug("User object before saving: {}", user);
logger.debug("Received headers: {}", headers);


### `info`
- **Purpose**: High-level lifecycle events or business-relevant milestones.
- **When to use**:
  - Application startup
  - Successful business operations (e.g., order processed, user registered)
  - Scheduled job run summaries

**Examples**:

logger.info("Application started successfully");
logger.info("User registration completed for userId={}", userId);


### `warn`
- **Purpose**: Recoverable issues or unusual conditions that don't stop program execution.
- **When to use**:
  - Fallbacks in config or data
  - Deprecated API usage
  - Unexpected values handled gracefully

**Example**:

logger.warn("Fallback to default configuration due to missing key: {}", configKey);


### `error`
- **Purpose**: Serious problems or exceptions that affect system functionality.
- **When to use**:
  - Catch blocks for unrecoverable exceptions
  - Transaction failures
  - Data corruption or missing dependencies

**Examples**:

logger.error("Failed to connect to Kafka", ex);
logger.error("Payment processing failed for orderId={} due to {}", orderId, ex.getMessage());


## Anti-Patterns to Avoid
`System.out.println(...)` - bad because Not configurable or structured 
`System.err.println(...)` - bad because Lacks log levels 
`e.printStackTrace()`     - bad because Avoids structured logging 
Logging passwords/tokens  - bad because Major security risk 



## Required Logger Declaration
Each class should declare:
private static final Logger logger = LoggerFactory.getLogger(ClassName.class);


## Additional Recommendations

- **Avoid logging inside tight loops or recursion** — it kills performance.
- **Mask or omit PII** (personally identifiable info) in logs.
- **Use parameterized logging**, not string concatenation:

  // Good
  logger.info("User ID: {}", userId);

  // Bad
  logger.info("User ID: " + userId);

## When to Use `logger.isDebugEnabled()`

Normally, you can just call:
logger.debug("User ID: {}", userId);

and SLF4J will efficiently skip the log message formatting if debug logging is turned off.

However, if the log message includes expensive computation, you should check first with `logger.isDebugEnabled()`:

### Why?

In this line:
logger.debug("Debug info: {}", computeExpensiveDebugInfo());

Even if debug logging is disabled, `computeExpensiveDebugInfo()` still gets executed.

This is because Java evaluates all method arguments before calling the method — so the expensive method runs before SLF4J decides to skip the log.

### Better Pattern:
if (logger.isDebugEnabled()) {
    logger.debug("Debug info: {}", computeExpensiveDebugInfo());
}


This avoids unnecessary computation when debug logging is turned off.
 Don't Use `isDebugEnabled()` when Simple variables / constants
 Use `isDebugEnabled()` when Heavy computation or side effects


## Summary
By following these logging rules, your application will maintain secure, consistent, and insightful logs across all layers of the system.



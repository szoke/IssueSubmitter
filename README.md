# IssueSubmitter

Reads lines from a CSV file, converts them into imaginary Jira issues
and submits them to a mock Jira using multiple threads.

Paid special attention to
* Clean Code
* SOLID principles
* Maintainability
* Testability

# Building and Running

Build: `mvn clean install`

Run: `java -jar target/issuesubmitter-0.0.1-SNAPSHOT.jar`

Run with parameters: `java -jar target/issuesubmitter-0.0.1-SNAPSHOT.jar <thread-pool-size> <csv-filter-regex>`

Run with parameters example: `java -jar target/issuesubmitter-0.0.1-SNAPSHOT.jar 4 DEMO-9.*`

## Notes

There are a few test-unfriendly constructs here:
* File access
* Timestamps related to task execution (created at, started at, finished at)
* Execution of tasks on separate threads
* Regex matching
* Random sleep intervals and HTTP status codes

Tried to abstract away / decouple these things to promote testability.

However, didn't want to over-engineer and the app to look like enterprise code taken to the extreme in the bad sense.

Applied design patterns where appropriate.

Adhered to the single responsibility principle in order to
* Keep the size of classes small
* Keep the classes testable
* Keep complexity low, maintainabilty high

Defensive programming on public APIs.

Promote dependency injection by depending on interfaces.

# Improvement Options

This is a demo application with some flaws that we would need to improve to make it production ready:
* Reading and filtering lines from the CSV file is not robust
* Logging is basic, in real life we would probably use a logger tool
* CSV filename hardcoded
* Manual DI in the Application class
* Application class not tested
* No API / integration tests
* No JavaDoc
* No robust exception handling
* Header are not read from the CSV (Should the app support any number of headers? If so, the Issue domain object is too
rigid and a more dynamic solution should be implemented. E.g. putting headers and corresponding values into a map and 
pass that map to the JSON serializer. Another question is to what extent the content in the CSV should be validated 
semantically? The current file contains some garbage lines that are not CSV.)
* Had issues with Jackson and Gson when running the application in command-line (no problems in IntelliJ IDEA)
** Exception in thread "main" java.lang.NoClassDefFoundError: com/fasterxml/jackson/databind/ObjectMapper
** Caused by: java.lang.ClassNotFoundException: com.fasterxml.jackson.databind.ObjectMapper
Tried to google and solve it but didn't manage so resorted to a simple working solution (cheated?)
* Look for TODOs in the code to find out what issues still remain

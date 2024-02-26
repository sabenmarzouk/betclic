# Betclic Project

The Betclic project is a Kotlin-based application designed following the Hexagonal Architecture principles, and Domain Driven Development. It divides the application into two main modules: `betclic-application` and `betclic-infrastructure`, to cleanly separate the domain and business logic from the technical details of the application infrastructure.

## Project Structure

### `betclic-application` Module

This module encapsulates the core domain logic and business rules of the application. It is framework-agnostic and focuses on expressing the domain model, use cases, and ports necessary for the application's functionality. Specifically, the module contains:

- **Domain Objects**: Entities and value objects that represent the core concepts of the domain.
- **Use Cases**: Application-specific business rules.
- **Ports**: Interfaces for communicating with external actors or services, both for incoming and outgoing interactions.

Additionally, the module includes acceptance tests and unit tests to ensure the correctness of the domain logic.

### `betclic-infrastructure` Module

The infrastructure module implements the technical details that support the application. It leverages Spring for dependency injection and includes:

- **Adapters**: Concrete implementations of the ports defined in the application module, providing the connection between the application core and external services or frameworks.
- **REST Controller**: Exposes the application's functionality through HTTP endpoints.
- **H2 Database**: An in-memory database used for persisting application data.
- **Integration and Unit Tests**: Tests to ensure the infrastructure components interact correctly with the application core and external services.

## Building the Project

The project uses Gradle as its build system. Within the `settings.gradle` (or `settings.gradle.kts` for Kotlin DSL) file

## Prerequisites

Before you begin, ensure you have met the following requirements:

- **Java Development Kit (JDK)**: JDK 21 or newer must be installed on your machine. You can verify your JDK installation by running `java -version` in your terminal.

## Build Commands

To build the project, follow these steps:

1. **Clone the repository**:

    ```shell
    git clone https://github.com/sabenmarzouk/betclic.git
    cd betclic
    ```

2. **Build the entire project**:

    ```shell
    ./gradlew build
    ```

3. **Build a specific module**:

    ```shell
    ./gradlew :betclic-application:build
    ```


## Running the Application with Gradle

To run the application directly using Gradle, use the `bootRun` task:

```shell
./gradlew :betclic-infrastructure:bootRun
```
## Running Tests

The project includes both unit tests and integration tests. To run the tests, you can use the following Gradle commands:

- **Run all tests in the project**: This command executes all tests across all modules.

    ```shell
    ./gradlew test
    ```

- **Run tests in a specific module**: To run tests in a specific module, specify the module path. For example, to run tests in the `betclic-application` module, use:

    ```shell
    ./gradlew :betclic-application:test
    ```

    Similarly, to run tests in the `betclic-infrastructure` module, use:

    ```shell
    ./gradlew :betclic-infrastructure:test
    ```

These instructions will guide contributors and developers through the process of building and testing the Betclic project.

## Run with Docker :
You can build and run your Docker container with the following commands:

```shell
docker build -t betclic-app .
docker run -d -p 8080:8080 --name betclic-app-container betclic-app
 ```
## Deploying to AWS

We can use Amazon Elastic Container Service (ECS) to deploy the app for scalable and managed application hosting.


## Using IntelliJ HTTP Client for API Testing
There is an http intellij client to facilitate interacting with rest service 
```shell
betclic.http
 ```
```shell
betclic.http
### Add Player 1
POST http://localhost:8080/game/players
Content-Type: application/json

playerName1

### Add Player 2
POST http://localhost:8080/game/players
Content-Type: application/json

playerName2

###

### Get Player Info
GET http://localhost:8080/game/players/5c56fce3-bfec-4ddd-9eb7-fd2ca0e03382
Accept: application/json

###

### Update Player Points
PUT http://localhost:8080/game/players/0c0003da-2b4b-481c-a603-d3d6cd707ca1/points
Content-Type: application/json

1000

###

### Get Game Ranking
GET http://localhost:8080/game/players/rank
Accept: application/json

###

### Change Game State
PUT http://localhost:8080/game/state
Content-Type: application/json

{
  "state": "ended"
}
 ```


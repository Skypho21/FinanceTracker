# Finance Tracker

This is a simple financial tracker application built with Java and JavaFX. It allows users to track their expenses and incomes, and provides basic financial reports.

## Features

- Add and view expenses
- Add and view incomes
- Generate financial reports

## Dependencies

This project requires the following dependencies:

- **Gson**: A Java library for JSON serialization and deserialization.
- **JavaFX**: A set of graphics and media packages for creating rich client applications.

### Maven Dependencies

If you are using Maven, you can include the dependencies in your `pom.xml` file:

```xml
<dependencies>
    <!-- Gson dependency -->
    <dependency>
        <groupId>com.google.code.gson</groupId>
        <artifactId>gson</artifactId>
        <version>2.10.1</version>
    </dependency>
    <!-- JavaFX dependencies -->
    <dependency>
        <groupId>org.openjfx</groupId>
        <artifactId>javafx-controls</artifactId>
        <version>17</version>
    </dependency>
    <dependency>
        <groupId>org.openjfx</groupId>
        <artifactId>javafx-fxml</artifactId>
        <version>17</version>
    </dependency>
</dependencies>
```

## Installation

1. Clone the repository:
    ```sh
    git clone https://github.com/yourusername/financetracker.git
    ```

2. Navigate to the project directory:
    ```sh
    cd financetracker
    ```

3. Build the project using Maven:
    ```sh
    mvn clean install
    ```

4. Run the application:
    ```sh
    mvn javafx:run
    ```

## Usage

1. Launch the application.
2. Enter the amount and category for expenses or incomes.
3. Click "Add Expense" or "Add Income" to save the entry.
4. View the current financial data and generate reports.

## License

This project is licensed under the MIT License.
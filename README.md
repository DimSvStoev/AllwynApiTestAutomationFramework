Overview
This project is designed to automate the testing of RESTful APIs for the Books and Authors services using the FakeRestAPI. The main goal is to ensure the API is working as expected by executing a series of automated tests for different endpoints and validating the responses.
The project follows the Page Object Model (POM) design pattern and uses JUnit for test management, with external test data stored in JSON files to avoid hard-coded values. Additionally, the project is configured to run automated tests in a CI/CD pipeline using GitHub Actions.
Technologies Used
• Java: The primary programming language.
• JUnit: For unit testing and managing the test lifecycle.
• RestAssured: To simplify API testing.
• Maven: For managing project dependencies and building the project.
• GitHub Actions: For CI/CD integration and running tests automatically in the pipeline.
• JSON: For external test data storage.
• Allure Reports: For generating test reports.

Key Components
1. ApiHelper: A utility class that handles common API request actions such as GET, POST, PUT, and DELETE.
2. BooksApiTests: Contains test cases for verifying different Books API endpoints, including creating, updating, and deleting books.
3. AuthorsApiTests: Contains test cases for testing the Authors API, validating CRUD operations.
4. testData.json: Holds dynamic test data used across multiple test cases to keep them flexible and maintainable.
How to Run the Tests
1. Clone the repository:
```bash
git clone https://github.com/your-repo/allwyn-api-automation.git
```
2. Install dependencies:
Make sure you have Maven installed, and then run:
```bash
mvn clean install
```
3. Run the tests:
To execute the tests locally, use:
```bash
mvn test
```
4. Generate reports:
After running the tests, generate Allure reports by executing:
```bash
mvn allure:serve
```
CI/CD Integration
This project is configured to run automatically using GitHub Actions. The pipeline:
• Runs tests on every commit.
• Automatically generates reports.
• Ensures that the API is functioning as expected with every change.
How to Contribute
1. Fork the repository.
2. Create a new branch (`git checkout -b feature/new-feature`).
3. Make your changes and commit (`git commit -m 'Add new feature'`).
4. Push to the branch (`git push origin feature/new-feature`).
5. Open a Pull Request.
License
This project is licensed under the MIT License.

Overview
This project is designed to automate the testing of RESTful APIs for the Books and Authors services using the FakeRestAPI. The main goal is to ensure the API is working as expected by executing a series of automated tests for different endpoints and validating the responses.
The project follows the Page Object Model (POM) design pattern and uses JUnit for test management, with external test data stored in JSON files to avoid hard-coded values. Additionally, the project is configured to run automated tests in a CI/CD pipeline using GitHub Actions.
Technologies Used
• Java: The primary programming language.
• JUnit: For unit testing and managing the test lifecycle.
• RestAssured: To simplify API testing.
• Maven: For managing project dependencies and building the project.
• GitHub Actions: For CI/CD integration and running tests automatically in the pipeline.
• JSON: For external test data storage.
• Allure Reports: For generating test reports.
Project Structure

src/
│
├── main/
│   ├── java/
│   │   └── com/allwyn/helpers/
│   │       └── ApiHelper.java  # Contains reusable methods for API requests
│   ├── com/allwyn/models/
│   │       └── Book.java       # Book model representing the API response structure
│   │       └── Author.java     # Author model representing the API response structure
│
├── test/
│   ├── java/
│   │   └── com/allwyn/tests/
│   │       └── BooksApiTests.java   # API tests for the Books service
│   │       └── AuthorsApiTests.java # API tests for the Authors service
│   └── resources/
│   

Key Components
1. ApiHelper: A utility class that handles common API request actions such as GET, POST, PUT, and DELETE.
2. BooksApiTests: Contains test cases for verifying different Books API endpoints, including creating, updating, and deleting books.
3. AuthorsApiTests: Contains test cases for testing the Authors API, validating CRUD operations.
4. testData.json: Holds dynamic test data used across multiple test cases to keep them flexible and maintainable.
How to Run the Tests
1. Clone the repository:
```bash
git clone https://github.com/your-repo/allwyn-api-automation.git
```
2. Install dependencies:
Make sure you have Maven installed, and then run:
```bash
mvn clean install
```
3. Run the tests:
To execute the tests locally, use:
```bash
mvn test
```
4. Generate reports:
After running the tests, generate Allure reports by executing:
```bash
mvn allure:serve
```
CI/CD Integration
This project is configured to run automatically using GitHub Actions. The pipeline:
• Runs tests on every commit.
• Automatically generates reports.
• Ensures that the API is functioning as expected with every change.
How to Contribute
1. Fork the repository.
2. Create a new branch (`git checkout -b feature/new-feature`).
3. Make your changes and commit (`git commit -m 'Add new feature'`).
4. Push to the branch (`git push origin feature/new-feature`).
5. Open a Pull Request.
License
This project is licensed under the MIT License.

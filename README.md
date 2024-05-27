# TODO APP
This basic todo app created by using Java and Spring Boot. It uses Couchbase as its database.

## Technology Stack
- Java 17
- Spring Boot
- Spring Data Couchbase
- Swagger
- Maven
- Docker
- JUnit and Mockito

## Build and Test App

1- **Compiling the Application:**
  
  mvn clean install

2. **Running the Test Suite:**
   
  mvn test

3. **Running the Application with its Dependencies:**
  - Go to the project directory.

  - Run docker-compose up in the terminal. The project will be ready for testing and running. The application will be available at http://localhost:8080.

  - Create a user account via the register API:

  - Endpoint: http://localhost:8080/api/v1/auth/register
Request body:
json
Copy code
{
  "firstName": "",
  "lastName": "",
  "email": "",
  "password": ""
}
  - After registration, the response will include a token for accessing other endpoints.

  - After registration, you can use the authentication endpoint to obtain a token:

  - Endpoint: http://localhost:8080/api/v1/auth/authenticate

  ## API Documentation
  I used swagger for api documentation. After starting app you can check the all endpoinds from;
   "http://localhost:8080/swagger-ui/index.html#/"
  Please note: you should be use token for testing endpoints on the swagger. 
     

# TODO APP
This basic todo app created by using Java and Spring Boot. It uses Couchbase as its database.

## Technology Stack
- Java 17
- Spring Boot
- Spring Data Couchbase
- Swagger
- Maven
- Docker
- JUnit ve Mockito

## Build and Test App

1- **Compiling the Application:**
  
  mvn clean install

2. **Running the Test Suite:**
   
  mvn test

3. **Running the Application with its Dependencies:**
  * Go to the project directory.
  * run the "docker-compose up" on terminal
  The project will be ready for testing and running.
  The application will be available at [http://localhost:8080]

  You should be create a user acount from register Api "localhost:8080/api/v1/auth/register"
   request body should be 
  " {
    "fistName": "",
    "lastName": "",
    "email": "",
    "password": ""
   }"
   after reqistration it will return a token for access for other endpoind 

   after registiration you can use the localhost:8080/api/v1/auth/authentication endpoind for token 

  ## API Documentation
  I used swagger for api documentation. After starting app you can check the all endpoinds from;
   "http://localhost:8080/swagger-ui/index.html#/"
  Please note: you should be use token for testing endpoints on the swagger. 
     

# Spring Boot Server Application Example

## Introduction
It's a simple project project that has few REST endpoints to modify a .json file at a local file system which acts like a database. The endpoints are create, update, delete and filterByAge to respectively creating, updating, deleting and getting filtered records from the .json file. The .json file data structure is list of objects. The object is Employee type with following properties:

* id (unique) 
* fullName 
* age 
* salary


## Instructions
The could be started application by executing com.sbapp.ServerApplication, which starts a webserver on port 8080 (http://localhost:8080) and serves SwaggerUI where can inspect and try existing endpoints.

The project is based on a small web service which uses the following technologies:

* Java 1.8
* Spring MVC with Spring Boot
* Maven

The conventions used in the project:

 * Entity have an ID with type of int(unique) and as such was used to compare the employee objects.
 * The architecture of the web service is built with the following components:
 	* DataTransferObjects: Objects which are used for outside communication via the API
   * Controller: Implements the processing logic of the web service, parsing of parameters and validation of in- and outputs.
   * Service: Implements the business logic and handles the access to the DataAccessObjects.
   * DataAccessObjects: Interface for the database. Inserts, updates, deletes and reads objects from the database (in our case json file).
   * DomainObjects: Functional Objects which might be persisted in the database (json file).
 * Swager can be used to generate API documentation by clicking or visiting the URL(http://localhost:8080/v2/api-docs) on SwaggerUI.
---

## Approach Used / Important Notes
* CRUD functionality is demonstrated for the employee end point where a JSON file serves as a database.
* The path of both the directory as well as the fileName for the JSON file is configurable in application.properties. The path could be relative as well as absolute. Currently the configured path is relative to application's root directory i.e. spring-boot-application/JSON/employees.json.
* The application checks the JSON file at the startup and if it doesn't exist it creates it based on the configured directory and file name.
* Note that EmployeeDO class has an overriden EqualsTo method which compares objects based only on ID since for the purpose of our simple app, ID is our unique dentifier. 
* Transactional functionality is also emulated in the EmployeeRepository.java though for real world applications this functionality could be provided by the JPA.
* Publish/Subscribe or in our case a simple Observer pattern is implemented using java.util's Observable and Observer to publish a message whenever an employee is successfully deleted. For real world applications this particular functionality usually provided via Queues/Topic's but for the purpose of simplicity it's avoided for this application.
* Unit tests are included to cover all requests, so far they are more integrated tests to demonstrate the working. They can be extended to cover individual services, components by mocking dependency, however due to time constraints only required end points are covered by unit tests in sufficient detail. Could be done in future.
* The project is also configured to be integrated with CircleCI and Circle CI could be used for its CI/CD. It can be further customized using .circleci/config.yml.
---

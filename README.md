## Example of a CRUD using Spring Boot with Thymeleaf and Spring Security.
The application is a customer and invoice management tool that allows users to create, read, update, and delete (CRUD) information in a database. 
The application uses Spring Boot as the framework to develop the business logic and Thymeleaf to generate HTML views.
The application is protected with Spring Security to ensure that only authenticated users can access the functionality. Users can log in to the application and access the functionality.
Users can create customers and associate invoices with them. Each customer has a name, last name, and email, as well as an optional image that the user can upload. 
Invoices have a unique identifier, date, description, and observation. Users can view the list of all customers and invoices in the application.
The application allows users to upload images for customers. When creating a new customer, the user can select an image from their computer and upload it to the application. The application stores the image in the server's file system and displays it on the customer details page.
The application also allows users to update and delete customers.


![](https://github.com/damian-git-99/Spring-CRUD/blob/master/example-images/crud1.PNG)
![](https://github.com/damian-git-99/Spring-CRUD/blob/master/example-images/crud3.PNG)
![](https://github.com/damian-git-99/Spring-CRUD/blob/master/example-images/crud2.PNG)

## Requirements
```
* Java 11
* Mysql 8.0.26
```

## Usage
- Clone the project: git clone https://github.com/damian-git-99/Spring-CRUD.git
- Install MySQL
- `CREATE DATABASE db_springboot`;
- Change the user and password in the application.properties file
- Start the application with the Spring Boot plugin: `mvn spring-boot:run`

## Roles
The application has two roles: USER and ADMINISTRATOR.
The USER role only has permission to perform certain actions, while the ADMINISTRATOR role has all permissions (delete user, edit user, create invoice).

Default user accounts.
- ADMIN: username: admin , password: 1234
- USER: username: damian , password: 1234


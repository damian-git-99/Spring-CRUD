## Example of a CRUD using Spring boot with Thymeleaf and Spring Security

![](https://github.com/damian-git-99/Spring-CRUD/blob/master/example-images/crud1.PNG)
![](https://github.com/damian-git-99/Spring-CRUD/blob/master/example-images/crud3.PNG)
![](https://github.com/damian-git-99/Spring-CRUD/blob/master/example-images/crud2.PNG)



## Requirements
```
* Java 11
* Maven 3
* Mysql 8.0.26
```

## Usage
```
1- clone project: git clone https://github.com/damian-git-99/Spring-CRUD.git
2- Install Mysql 
3- CREATE DATABASE db_springboot;
4- Change mysql user and password from application.properties
5- start the application with the Spring Boot maven plugin (mvn spring-boot:run).
```

## Roles
```
The application has two roles USER and ADMIN, 
The USER only has permission to perform certain actions while the ADMIN has all the permissions (Delete user, Edit user, Create invoice)

Default user accounts
* ADMIN: username: admin , password: 1234
* USER: username: damian , password: 1234
```

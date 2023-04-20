## Ejemplo de un CRUD usando Spring boot con Thymeleaf y Spring Security

![](https://github.com/damian-git-99/Spring-CRUD/blob/master/example-images/crud1.PNG)
![](https://github.com/damian-git-99/Spring-CRUD/blob/master/example-images/crud3.PNG)
![](https://github.com/damian-git-99/Spring-CRUD/blob/master/example-images/crud2.PNG)

## Requisitos
```
* Java 11
* Mysql 8.0.26
```

## Usage
- Clonar el projecto: `git clone https://github.com/damian-git-99/Spring-CRUD.git
- Instalar Mysql 
- `CREATE DATABASE db_springboot`;
- Cambiar el usuario y password del archivo application.properties
- Comenzar la aplicacion con el plugin de spring boot: `mvn spring-boot:run`

## Roles
La aplicación tiene dos roles USUARIO y ADMINISTRADOR,
El USUARIO solo tiene permiso para realizar ciertas acciones mientras que el ADMINISTRADOR tiene todos los permisos (Eliminar usuario, Editar usuario, Crear invoice)

Cuentas de usuario predeterminadas
- ADMIN: username: admin , password: 1234
- USER: username: damian , password: 1234


# Expense Manager API!

The purpose of this project is to present a **stateless API** for data consumption using HTTP requests for different types of applications.
The available services will be secured by an authentication server provided through the usage of the **Spring OAuth2** plugin.

> **Note:**
> 
> - This project is directly linked to the application at the [ExpenseManager-APP](https://github.com/NickChecan/Expense-Manager-APP) repository.

----------

Project Components
-------------

This project uses the [Spring Boot](https://spring.io/projects/spring-boot) framework and its plugins to serve data from a MongoDB local instance.

```
Expense-Manager-API
	├── .idea
	│    ├── compiler.xml
	│    ├── encodings.xml
	│    ├── misc.xml
	│    └── vcs.xml
	├── gradle
	│    └── wrapper
	│         ├── gradle-wrapper.jar
	│         └── gradle-wrapper.properties
	├── src
	│    └── main
	│         ├── java
	│         │    └── com
	│         │         └── expense
	│         │              ├── controller
	│         │              │    ├── AccessController.java
	│         │              │    ├── ExpenseController.java
	│         │              │    ├── GroupController.java
	│         │              │    ├── RoleController.java
	│         │              │    └── UserController.java
	│         │              ├── exception
	│         │              │    ├── NoAuthorizationException.java
	│         │              │    ├── ResourceNotFoundException.java
	│         │              │    └── RestExceptionHandler.java
	│         │              ├── model
	│         │              │    ├── Expense.java
	│         │              │    ├── Group.java
	│         │              │    ├── Role.java
	│         │              │    └── User.java
	│         │              ├── repository
	│         │              │    ├── ExpenseRepository.java
	│         │              │    ├── GroupRepository.java
	│         │              │    ├── RoleRepository.java
	│         │              │    └── UserRepository.java
	│         │              ├── security
	│         │              │    ├── AuthorizationServerConfiguration.java
	│         │              │    ├── ResourceServerConfiguration.java
	│         │              │    ├── SecurityConfiguration.java
	│         │              │    └── UserSecurityDetails.java
	│         │              ├── service
	│         │              │    ├── ExpenseService.java
	│         │              │    ├── GroupService.java
	│         │              │    ├── RoleService.java
	│         │              │    ├── UserSecurityService.java
	│         │              │    └── UserService.java
	│         │              ├── setting
	│         │              │    ├── EnvironmentVariables.java
	│         │              │    ├── Initialization.java
	│         │              │    └── View.java
	│         │              └── Application.java
	│         └── resources
	│              └── application.yml
	├── .gitignore
	├── README.md
	├── build.gradle
	├── gradlew
	├── gradle.bat
	└── settings.gradle
```

The routes for HTTP services can be found in the classes available at the **controller** folder. 

> **Note:**
> 
> - The mentioned package does not include the routes enabled by the Spring OAuth2 plugin.

The authentication server and the security options were set at the **security** package classes.

All exceptions will be properly handled at the **RestExceptionHandler.java** class.

The environment variables class at the settings package, will serve information previously configured at the **application.yml** file, available at the **resources** folder. This approach intends to organize static data and simplify constants usage through the entire application.


![Screenshot](assets/class_diagram.png)

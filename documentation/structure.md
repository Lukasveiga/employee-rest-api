### Code structure:

The code structure follows the layers pattern dividing the application into **resository**, **service** and **controller** where each part has its well-defined single responsibility.

Controller --> Service --> Repository

 - Repository: This layer is responsible for communicating with the database without actually specifying the business rule. To connect to the database it is necessary to configure spring by providing the url, user and password of the database, which in our case MySQL was used (the setup section will show how to configure this step).<br>
 By extending the spring framework's **JpaRepository** class it is possible to obtain most of the methods used in communicating with the database, if it is necessary to use a specific method that the class does not provide, just add the interface.
 
 - Service: It is the layer responsible for the business logic, where the CRUD actions (create, read, update and delete) will be implemented. The service injects the repository interface to access the database communication methods and establish the connection between the controller layer and the repository. <br>
To signal to the framework that it is a service, just use the @Service annotation at the top of the class that implements the service interface. <br>
`Obs`: Some methods that make changes to the database need to be marked as transactional, to guarantee the atomicity of the process and prevent external disturbances from interrupting the action and not completely ending the process. For that, just annotate with @Transactional from spring the method that modifies the data in the database.

- Controller: This layer is responsible for receiving HTTP requests and sending responses according to the application model. It injects the service interface to access the business logic. To signal to the framework that this layer is a controller, just annotate it with @RestController at the top of the class.

#### Database (MySQL):

- For the application, the employee_db database and the employee table were created, following the following command within mysql:
```
create database if not exist 'employee_db';
use 'employee_db';

drop table if exists 'employee';

create table 'employee' (
	'id' int not null auto_increment,
	'first_name' varchar(45) default null,
	'last_name' varchar(45) default null,
	'email' varchar(45) default null,
	
	primary key('id')
) engine=InnoDB auto_increment=1 default charset=utf8mb4;
``` 


[Back](https://github.com/Lukasveiga/employee-rest-api)

* ## **Employee's Rest API** ##

### Overview:

This API consists of a simple employee management application, containing information about first name, last name, and email. It is possible to perform all CRUD operations (Create, Read, Update, and Delete) on the database through the endpoints.

The main focus of this API is to develop skills in back-end structure development using the **Spring Boot** framework, including **clean code** best practices, **SOLID** principles, layer pattern, and **unit** and **integration testing**.

-----

### Tools:

- Java 17;
- Spring Boot;
- MySQL;
- Maven;

-----

### Available methods:

| HTTP Methods  | Path               | Resource(Type)      | Response          |
| ------------- | -------------      | -------------       | ------------- |
| GET           | /api/v1/employees  |                     | List of Employees  |
| GET           | /api/v1/employees/ | id (integer)        | Employee Json body  |
| GET           | /api/v1/employees/first-name?name= | name (string)        | Employee Json body  |
| POST           | /api/v1/employees |                     | Employee Json body  |
| PUT           | /api/v1/employees/ | id (integer)        | Employee Json body  |
| DELETE           | /api/v1/employees/ | id (integer)        | Delete message  |

#### - Examples:
1. Get a list of all employees:
```
  http://localhost:8080/api/v1/employees
```

2. Get employee by id (e.g. id = 1):
```
  http://localhost:8080/api/v1/employees/1
```

3. Get employee by first name (e.g. name = "John"):
```
  http://localhost:8080/api/v1/employees/first-name?name=John
```

4. Add new employee:
```
  http://localhost:8080/api/v1/employees
  
  Request json body:
  
  {
    "firstName": "Carl",
    "lastName": "Sagan",
    "email": "carl@email.com"
  }
```

5. Update existing employee (e.g. id = 1):
```
  http://localhost:8080/api/v1/employees/1
  
  Request json body:
  
  {
    "firstName": "Carl",
    "lastName": "Sagan",
    "email": "carl@email.com"
  }
```

6. Delete existing employee (e.g. id = 1):
```
  http://localhost:8080/api/v1/employees/1
  
  Response:
  
  Employee Carl Sagan was deleted.
```

------

### Code structure:

A estrutura do código segue o padrão de camadas dividindo a aplicação em **resository**, **service** and **controller** onde cada parte possui sua responsabilidade bem definida.

Controller --> Service --> Repository

 - Repository: Essa camada é responsável pela comunicação com o banco de dados sem especificar de fato a regra do negócio. Para a coneçxão com o banco de dados é necessário configurar o spring fornecendo a url, usuário e senha do banco de dados que no nosso caso foi utilizado o MySQL (na seção setup será demonstrado como realizar as configurações dessa etapa).<br>
 Extendendo a classe JpaRepository do spring framework é possível obter grande parte dos métodos utilizados na comunicação com o bando de dados, caso seja necessário utilizar um método específico que a classe não fornece basta adicionar a interface.
 
 - Service: 


------

### Autor:

- [Linkedin](https://www.linkedin.com/in/lukas-veiga-79371b20a/);
- e-mail: lukas.veiga10@gmail.com;

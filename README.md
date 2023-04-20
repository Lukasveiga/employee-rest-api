* ## **Employee's Rest API** ##

### Overview:

Essa API consiste em uma simples aplicação de gestão de funcionários, contendo informações
sobre o primeiro nome, último nome e email.
Onde é possível realizar todas as operações do CRUD (Create, Read, Update e Delete) no banco
de dados por meio dos endpoints. <br>
O foco principal dessa API é desenvolver habilidades no desenvolvimento de estruturas do back-end
com o framework Spring Boot incluindo as boas práticas do clean code, princípios SOLID, padrão de camadas
e os testes unitário e de integração.

-----

### Tools:

- Java 17;
- Spring Boot;
- MySQL;
- Maven;
- Docker;

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

### Autor:

- [Linkedin](https://www.linkedin.com/in/lukas-veiga-79371b20a/);
- e-mail: lukas.veiga10@gmail.com;

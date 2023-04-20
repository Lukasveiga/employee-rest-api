### Code structure:

A estrutura do código segue o padrão de camadas dividindo a aplicação em **resository**, **service** and **controller** onde cada parte possui sua responsabilidade bem definida.

Controller --> Service --> Repository

 - Repository: Essa camada é responsável pela comunicação com o banco de dados sem especificar de fato a regra do negócio. Para a coneçxão com o banco de dados é necessário configurar o spring fornecendo a url, usuário e senha do banco de dados que no nosso caso foi utilizado o MySQL (na seção setup será demonstrado como realizar as configurações dessa etapa).<br>
 Extendendo a classe **JpaRepository** do spring framework é possível obter grande parte dos métodos utilizados na comunicação com o bando de dados, caso seja necessário utilizar um método específico que a classe não fornece basta adicionar a interface.
 
 - Service: É a camada responsável pela lógico de négocio, onde serão implementados as ações do CRUD (create, read, update and delete). O service realiza a injeção da interface repository para ter acesso aos métodos de comunição com o banco de dados e estabelecer a conexão entre a camada controller e a respository. <br>
Para sinalizar ao framework que se trata de uma cada service basta utilizar a anotação @Service no topo da classe que implementa a interface do service. <br>
`Obs`: Alguns métodos que realizam alterações no banco de dados precisam ser marcados como transacionais, para garantir a atomicidade do processo e evitar que perturbações externas possam interromper a ação e não finalizar por completo o processo. Para isso basta anotar com @Transactional do spring o método que modifica os dados no banco de dados.     

- Controller: Essa camada é responsável por receber as requestes HTTP e enviar as respostas de acordo com modelo da aplicação. Ela realiza a injeção da interface service para ter acesso a lógica de negócio. Para sinalizar ao framework que essa camada é um controller basta anotar com @RestController no topo da classe.

#### Database (MySQL):

- Para a aplicação foi criado o banco de dados employee_db e a tabela employee, seguindo o seguinte comando dentro do mysql:
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

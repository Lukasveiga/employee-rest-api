### Unit and Integrations tests:

  #### Unit tests:
  - Para testar a camada respository foi utilizada o banco de dados em mémoria h2 para evitar alterações no banco de dados original. 
  É necessário então usar a anotação @DataJpaTest do framework onde os testes com essa anotação are transactional and roll back at the end of each test.
  
  Configuração do banco de dados h2:
  ```
spring.datasource.url=jdbc:h2:mem:db;DB_CLOSE_DELAY=-1
spring.datasource.username=sa
spring.datasource.password=sa
spring.datasource.driver-class-name=org.h2.Driver

jpa.properties.hibernate.dialect=org.hibernate.dialect.H2Dialect
jpa.properties.show-sql=true

jpa.hibernate.ddl-auto=create-drop
  ```
 `Obs`: É necessário criar uma pasta resource no diretório de teste, criar um arquivo .properties e inserir as informações acima para configurar o banco
 de dados em memória.
 
 - Já na camada de service para realizar os testes foi utilizado o framework mockito possibilitado mockar a camada repository necessária para os testes
 da camada service. O propósito de utilizar esse framework é que não existe necessidade de utilizar um banco de dados concreto uma vez que a camada repository
 já foi testada. O objetivo dos testes unitários é isolar ao máximo cada seção a ser testada.<br>
 Para realizar os testes é preciso utilizar a anotação @ExtendWith do framework Junit e passar como parâmetro a classe MockitoExtension, assim será possível
 realizar o mock da camada service e simular seu comportamento.
 
- 

[Back](https://github.com/Lukasveiga/employee-rest-api)
 

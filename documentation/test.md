### Unit and Integration tests:

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
 
- Para testar a camada controller é necessário utilizar a anotação @WebMvcTest que se encarregará to fire up an application context that contains only the beans needed for testing a web controller e passar como parâmetro a classe controller que deseja ser testada. <br>
Um dos recursos que vem junto do contexto gerado pelo spring é o MockMvc responsável por simular as requests HTTP; e por fim realizar o mock da camada service para ter acesso a lógica de négocio da aplicação.

##### Unit Test Coverage:
| Layer         | Coverage      |
| ------------- | ------------- |
| Repository    |      100%     | 
| Service       |      100%     | 
| Controller    |      100%     |

#### Integration Test:
- No teste de integração é necessário carregar todo o contexto em volta da camada controller, com excessão do banco de dados que não pode ser alterado durante os testes, para isso pode-se criar uma nova tabela com a mesma estrutura da tabela original apenas com o objetivo de teste ou utilizar novamenta o banco de dados em memória como foi demonstrado no tópico de teste unitário da camada repository. <br>
- Para isso é preciso anotar a classe de teste com @SpringBootTest, por padrão o framework não carrega um ambiente de aplicação web para isso é necessário passar como parâmetro um ambiente web com o mock, it doesn’t load a real http server, just mocks the entire web server behavior.<br>
- Por fim, é preciso realizar as injeções de dependências necessárias e realizar os testes.

 ##### Integration Test Coverage:
| Components        | Coverage      |
| ------------- | ------------- |
| Class    |      87%     | 
| Method    |      81%     |
| Line    |      78%     |


[Back](https://github.com/Lukasveiga/employee-rest-api)
 

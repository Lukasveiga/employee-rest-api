### Code structure:

A estrutura do código segue o padrão de camadas dividindo a aplicação em **resository**, **service** and **controller** onde cada parte possui sua responsabilidade bem definida.

Controller --> Service --> Repository

 - Repository: Essa camada é responsável pela comunicação com o banco de dados sem especificar de fato a regra do negócio. Para a coneçxão com o banco de dados é necessário configurar o spring fornecendo a url, usuário e senha do banco de dados que no nosso caso foi utilizado o MySQL (na seção setup será demonstrado como realizar as configurações dessa etapa).<br>
 Extendendo a classe **JpaRepository** do spring framework é possível obter grande parte dos métodos utilizados na comunicação com o bando de dados, caso seja necessário utilizar um método específico que a classe não fornece basta adicionar a interface.
 
 - Service: 


[Back](https://github.com/Lukasveiga/employee-rest-api)

# Desafio Spring

### Desenvolvimento

A aplicação foi criada usando o framework Spring, na versão 4.0. Foram criados dois endpoints principais ("/cidades" e "/clientes"), possuindo vários métodos para criação, deleção, busca e atualização os dados dos clientes e cidades.
Para o desenvolvimento foi usado o Eclipse com IDE e Java 8 como linguagem. Foram criados testes unitários para a validação das funcionalidades criadas. O projeto buscou seguir boas práticas de programação e técnicas de clean code 

### Execução da API usando Docker
#### Gerando o jar
Para criar a imagem da aplicação é necessário antes de qualquer coisa, gerar o arquivo **.jar**. Para isso, basta executar o comando **mvn clean package** na pasta raiz do projeto. 
#### Criando a imagem
Após gerar o arquivo .jar, ainda na pasta raiz, execute o comando **docker build -t [nome_imagem] .** para gerar a imagem do projeto. 

#### Subindo o Container
Para que o container da aplicação possa ser executado, execute o comando **docker run -it -p 8080:8080 [nome_imagem]**. Ao fim da exeção do comando o container estará pronto para ser usado, basta acessar o endereço do [Swagger UI](http://localhost:8080/swagger-ui.html#)

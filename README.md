Desenvolvido por Mateus Pereira da Silva Fernandes

Login Spring Java

Sistema de login desenvolvido em Java com Spring Boot.

Tecnologias utilizadas:

Java 17
Spring Boot
Spring Security
Thymeleaf
MongoDB Atlas
Spring Data MongoDB
Spring Session
BCrypt
Bean Validation

IDEs:
VScode
Intelijj(Recomendo para trabalhar com java spring)

Requisitos para rodar

Java 17
Maven
MongoDB Atlas

Configure as seguintes variáveis de ambiente:

MONGODB_URI=mongodb+srv://USUARIO:SENHA@SEU_CLUSTER.mongodb.net/login_spring
APP_ADMIN_EMAIL=admin@exemplo.com
APP_ADMIN_PASSWORD=SUA_SENHA
PORT=8080
 
O sistema estará disponível em:

http://localhost:8080

MongoDB

O projeto utiliza o MongoDB Atlas para armazenamento dos dados da aplicação e das sessões.

A conexão é realizada através da variável de ambiente MONGODB_URI.

Projeto desenvolvido para fins acadêmicos.

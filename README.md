Desenvolvido por MAteus Pereira da Silva Fernandes

Login Spring Java

Sistema de login desenvolvido em Java com Spring Boot.

Funcionalidade do sistema:

Autenticação de usuarios.
Senhas criptografadas com BCrypt
Dados no MongoDB Atlas
Controle de Logs 
ACesso ao painel de logs somente pelo administrador do sistema

Tecnologias utilizadas

Java 17
Spring Boot
Spring Security
Thymeleaf
MongoDB Atlas
Spring Data MongoDB
Spring Session
BCrypt
Bean Validation

IDEs
VScode
Intelijj(Recomendo para trabalhar com java spring)

Requisitos para rodar

Java 17
Maven
MongoDB Atlas

Configurar o MongoDB Atlas com segurança

1. No MongoDB Atlas, crie um cluster e um usuário de banco com permissões mínimas necessárias.
3. Copie a connection string.
4. Troque os marcadores pela credencial do usuário do banco e pelo nome do cluster/banco.

Configure as seguintes variáveis de ambiente:

MONGODB_URI=mongodb+srv://USUARIO:SENHA@SEU_CLUSTER.mongodb.net/login_spring
APP_ADMIN_EMAIL=admin@exemplo.com
APP_ADMIN_PASSWORD=SUA_SENHA
PORT=8080

Não adicione credenciais reais ao repositório.
 
O sistema estará disponível em:

http://localhost:8080

MongoDB

O projeto utiliza o MongoDB Atlas para armazenamento dos dados da aplicação e das sessões.

A conexão é realizada através da variável de ambiente MONGODB_URI.

Projeto desenvolvido para fins acadêmicos.

# Portal de Estágios

Um sistema completo para gerenciamento de estágios, conectando estudantes, empresas e administradores em um portal web.

## Funcionalidades

### Backend (Spring Boot)
- **Autenticação e Autorização**: JWT-based authentication com Spring Security
- **Gerenciamento de Usuários**: Perfis para Estudante, Empresa e Admin
- **CRUD Completo**: Para áreas de interesse, empresas, estudantes, vagas, inscrições e entrevistas
- **Regras de Negócio**: Controle de inscrições, encerramento de vagas
- **Agendamento de Entrevistas**: Com calendário integrado e notificações por email
- **Documentação API**: Swagger/OpenAPI

### Frontend (React)
- **Interface Responsiva**: Material-UI para design moderno
- **Dashboards Personalizados**: Diferentes views para cada perfil de usuário
- **CRUD Interfaces**: Para todas as entidades do sistema
- **Calendário de Entrevistas**: Integração com react-big-calendar
- **Notificações**: Sistema de alertas para usuários

## Tecnologias Utilizadas

### Backend
- Java 17
- Spring Boot 3.2.0
- Spring Data JPA
- PostgreSQL
- Spring Security + JWT
- Spring Mail (para notificações)
- Swagger/OpenAPI

### Frontend
- React 18
- Material-UI (MUI)
- Axios (para API calls)
- React Router DOM
- React Big Calendar
- Moment.js

## Pré-requisitos

- Java 17 ou superior
- Node.js 16 ou superior
- PostgreSQL 12 ou superior
- Maven 3.6 ou superior

## Instalação e Configuração

### 1. Clonagem do Repositório
```bash
git clone <url-do-repositorio>
cd ProjetJPA
```

### 2. Configuração do Banco de Dados

#### Instalar PostgreSQL (se necessário)
```bash
# Ubuntu/Debian
sudo apt update
sudo apt install postgresql postgresql-contrib

# macOS (com Homebrew)
brew install postgresql
brew services start postgresql

# Windows: Baixar do site oficial
```

#### Criar Banco de Dados
```sql
-- Conectar ao PostgreSQL como superusuário
sudo -u postgres psql

-- Criar banco de dados
CREATE DATABASE portal_estagios;

-- Criar usuário (opcional, mas recomendado)
CREATE USER portal_user WITH PASSWORD 'portal_password';
GRANT ALL PRIVILEGES ON DATABASE portal_estagios TO portal_user;
```

### 3. Configuração do Backend

#### Arquivo application.properties
Edite o arquivo `backend/src/main/resources/application.properties`:

```properties
# Configuração do banco de dados PostgreSQL
spring.datasource.url=jdbc:postgresql://localhost:5432/portal_estagios
spring.datasource.username=postgres  # ou portal_user se criou
spring.datasource.password=123456    # ou portal_password

# Configuração JPA
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect

# Configuração JWT
jwt.secret=chaveSecretaParaJWT
jwt.expiration=86400000

# Configuração de email (para notificações)
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=seuemail@gmail.com
spring.mail.password=suasenha
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true

# Configuração do servidor
server.port=8080
```

#### Configuração do Email (Opcional)
Para habilitar notificações por email, configure uma conta Gmail:

1. Ative a verificação em duas etapas
2. Gere uma senha de aplicativo
3. Use a senha de aplicativo no campo `spring.mail.password`

#### Executar o Backend
```bash
cd backend
mvn clean install
mvn spring-boot:run
```

O backend estará disponível em: http://localhost:8080

### 4. Configuração do Frontend

#### Instalar Dependências
```bash
cd frontend
npm install
```

#### Executar o Frontend
```bash
npm start
```

O frontend estará disponível em: http://localhost:3000

## Uso do Sistema

### 1. Primeiro Acesso
- Acesse http://localhost:3000
- Como não há usuários iniciais, você precisará criar contas via API ou banco de dados

### 2. Criação de Usuário Admin (via SQL)
```sql
INSERT INTO usuario (email, senha, perfil, nome, ativo) VALUES
('admin@portal.com', '$2a$10$encrypted_password', 'ADMIN', 'Administrador', true);
```

### 3. Fluxo de Uso

#### Para Estudantes:
1. Criar conta como Estudante
2. Completar perfil (áreas de interesse, etc.)
3. Buscar vagas disponíveis
4. Se candidatar a vagas
5. Agendar entrevistas quando convocado

#### Para Empresas:
1. Criar conta como Empresa
2. Completar perfil da empresa
3. Criar vagas de estágio
4. Revisar inscrições recebidas
5. Agendar entrevistas com candidatos

#### Para Administradores:
1. Gerenciar usuários
2. Moderar vagas e inscrições
3. Visualizar dashboards e relatórios

## API Documentation

A documentação completa da API está disponível via Swagger em:
http://localhost:8080/swagger-ui.html

## Estrutura do Projeto

```
ProjetJPA/
├── backend/
│   ├── src/main/java/com/portalestagios/
│   │   ├── controller/     # REST Controllers
│   │   ├── model/          # JPA Entities
│   │   ├── repository/     # JPA Repositories
│   │   ├── service/        # Business Logic
│   │   ├── security/       # JWT & Security Config
│   │   └── dto/            # Data Transfer Objects
│   ├── src/main/resources/
│   │   └── application.properties
│   └── pom.xml
├── frontend/
│   ├── src/
│   │   ├── components/     # React Components
│   │   ├── App.js          # Main App Component
│   │   └── index.js        # Entry Point
│   ├── package.json
│   └── public/
└── README.md
```

## Testes

### Backend
```bash
cd backend
mvn test
```

### Frontend
```bash
cd frontend
npm test
```

## Desenvolvimento

### Executar em Modo Desenvolvimento
```bash
# Backend
cd backend && mvn spring-boot:run

# Frontend (nova aba)
cd frontend && npm start
```

### Build para Produção
```bash
# Backend
cd backend && mvn clean package

# Frontend
cd frontend && npm run build
```

## Hospedagem

### Backend (Railway, Heroku, etc.)
1. Configurar variáveis de ambiente para produção
2. Usar PostgreSQL na nuvem
3. Configurar CORS para o domínio do frontend

### Frontend (Vercel, Netlify, etc.)
1. Build do projeto: `npm run build`
2. Deploy da pasta `build/`
3. Configurar variáveis de ambiente

## Contribuição

1. Fork o projeto
2. Crie uma branch para sua feature (`git checkout -b feature/nova-feature`)
3. Commit suas mudanças (`git commit -am 'Adiciona nova feature'`)
4. Push para a branch (`git push origin feature/nova-feature`)
5. Abra um Pull Request

## Licença

Este projeto está sob a licença MIT. Veja o arquivo LICENSE para mais detalhes.

## Suporte

Para dúvidas ou problemas, abra uma issue no repositório ou entre em contato com a equipe de desenvolvimento.

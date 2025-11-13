# TODO - Portal de Estágios

## Backend (Spring Boot)
- [x] Criar estrutura do projeto (pom.xml, pastas)
- [x] Configurar JPA e PostgreSQL
- [x] Criar entidades: Usuario, AreaInteresse, Empresa, Estudante, Vaga, Inscricao, Entrevista
- [x] Implementar autenticação com Spring Security e JWT
- [x] Criar repositories, services, controllers para CRUD
- [x] Documentar com Swagger
- [x] Implementar regras de negócio (inscrições, encerramento de vagas, etc.)
- [x] Funcionalidade inovadora: Agendamento de entrevistas com calendário e notificações

## Frontend (React)
- [x] Criar projeto React
- [x] Implementar autenticação (login/logout)
- [x] Criar dashboards para cada perfil (Estudante, Empresa, Admin)
- [x] CRUD para entidades (áreas, empresas, estudantes, vagas)
  - [x] Create AreaInteresseCRUD.js component
  - [x] Create EmpresaCRUD.js component
  - [x] Create EstudanteCRUD.js component
  - [x] Create VagaCRUD.js component (expand existing vagas listing to full CRUD)
  - [x] Edit Dashboard.js to add tabs for CRUD components and conditional rendering
- [x] Listagem e inscrição em vagas
- [x] Calendário para agendamento de entrevistas
- [x] Notificações

## Integração e Testes
- [x] Conectar frontend ao backend (corrigir bugs na integração)
- [x] Testes unitários e de integração
- [x] Hospedagem opcional

## Documentação
- [x] README com instruções de instalação e uso

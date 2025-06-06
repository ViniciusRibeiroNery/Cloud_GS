# 🌡️ SafeHeat - Backend em Container

Repositório da API SafeHeat, desenvolvida para alertar e monitorar a população em casos de calor extremo, com foco em **resiliência climática** e **uso de tecnologias em nuvem**. Este projeto faz parte do desafio **"Protech the Future" da FIAP**, e foi containerizado utilizando **Docker e Oracle XE**.

---

## 📝 Visão Geral

A API do SafeHeat foi criada para:

- 📍 Cadastrar locais monitorados
- 🌡️ Registrar alertas de calor extremo
- 👤 Gerenciar usuários e suas relações com os locais monitorados
- 📦 Persistir os dados em um banco Oracle
- 🐳 Executar via containers com Docker

---

## 🛠️ Requisitos para Executar

- [Docker](https://www.docker.com/) instalado na máquina
- [SQL Developer](https://www.oracle.com/database/sqldeveloper/) (opcional, para visualizar os dados no banco)
- Terminal (cmd, bash ou PowerShell)

---

## 🚀 Como Rodar o Projeto Localmente

### 1. Subir o Banco de Dados Oracle XE (Container)

```bash
docker run -d \
  --name oracle-xe \
  -p 1521:1521 \
  -e ORACLE_PWD=Oracle123 \
  -v oracle-data:/opt/oracle/oradata \
  -v $(pwd)/init.sql:/opt/oracle/scripts/setup/01-init.sql \
  -v $(pwd)/CriacaoBD.sql:/opt/oracle/scripts/setup/02-CriacaoBD.sql \
  container-registry.oracle.com/database/express:21.3.0
⚠️ Observação: pode ser necessário executar docker login container-registry.oracle.com para usar essa imagem oficial da Oracle.

2. Build da Imagem da Aplicação Java
bash
Copiar
Editar
docker build -f Dockerfile-java.dockerfile -t safeheat-api .
3. Rodar a Aplicação
bash
Copiar
Editar
docker run -d --name safeheat-api -p 8080:8080 safeheat-api
📫 Acesso à API
Após a execução, a API estará disponível em:

arduino
Copiar
Editar
http://localhost:8080
Você poderá acessar os endpoints utilizando ferramentas como:

Postman

Insomnia

Navegador (para testes simples com GET)

Ou via Swagger: http://localhost:8080/swagger-ui/index.html (se configurado)

💾 Acesso ao Banco Oracle via SQL Developer
Para visualizar os dados persistidos:

Campo	Valor
Host	localhost
Porta	1521
Service	XEPDB1
Usuário	system
Senha	Oracle123

🔄 Teste de Persistência (para vídeo ou prints)
Crie um registro via API (POST)

Confirme via SQL Developer com SELECT

Reinicie o banco: docker restart oracle-xe

Execute o SELECT novamente e comprove que os dados continuam no banco ✅

🎥 Entrega em Vídeo
Demonstre o uso do Docker para subir banco e aplicação

Mostre as operações CRUD funcionando

Confirme a persistência dos dados no Oracle após reiniciar

Utilize o SQL Developer e/ou Swagger/Postman para validação

👥 Integrantes
Felipe Ulson Sora – RM555462 – @felipesora

Augusto Lope Lyra – RM558209 – @lopeslyra10

Vinicius Ribeiro Nery Costa – RM559165 – @ViniciusRibeiroNery

✅ Requisitos Atendidos
 API Java funcional

 Container do banco Oracle com volume e script SQL

 Dockerfile com usuário não-root

 Variáveis de ambiente configuradas

 CRUD com persistência confirmada

 Documentação clara e vídeo explicativo

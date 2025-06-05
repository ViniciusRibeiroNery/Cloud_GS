#Build usando Maven e Java 21
FROM eclipse-temurin:21-jdk AS build

# Instalando Maven manualmente
RUN apt-get update && apt-get install -y maven

# Criando um diretório build
WORKDIR /app

# Copia de arquivos necessários para o build
COPY pom.xml .                # Copia o pom.xml
COPY src ./src                # Copia o código-fonte

# Executando o build e gerando o .jar (sem usar os testes)
RUN mvn clean install -DskipTests

# Runtime com imagem leve do Java 21
FROM eclipse-temurin:21-jdk-jammy

# Criação de usuário não-root
RUN groupadd -r safegroup && useradd -r -g safegroup safeuser

# Definindo um diretório personalizado
WORKDIR /appsafe

# Define uma variável de ambiente
ENV APP_NAME=SafeHeatBackend

# Copiando o .jar da etapa de build
COPY --from=build /app/target/*.jar app.jar

# Expondo a porta da aplicação
EXPOSE 8080

# Executando com usuário não-root
USER safeuser

# Comando de execução da aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]
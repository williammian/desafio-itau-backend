# Base de runtime com Java 17 (imagem leve)
FROM eclipse-temurin:17-jdk-alpine

# Diretório de trabalho dentro do container
WORKDIR /app

# Copia o .jar para dentro do container
COPY target/*.jar app.jar

# Expõe a porta (ajustável via application.properties ou env)
EXPOSE 8080

# Comando de inicialização
ENTRYPOINT ["java", "-jar", "app.jar"]
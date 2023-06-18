# Define a imagem base do contêiner
FROM openjdk:17

# Define o diretório de trabalho dentro do contêiner
WORKDIR /app

# Copia o arquivo JAR da sua aplicação para o diretório de trabalho
COPY /target/api-pagamentos-0.0.1-SNAPSHOT.jar api-pagamentos.jar

# Expõe a porta em que sua aplicação está executando
EXPOSE 8080

# Comando para executar sua aplicação quando o contêiner for iniciado
CMD ["java", "-jar", "api-pagamentos.jar"]
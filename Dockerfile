# Usa una imagen base de OpenJDK
FROM openjdk:17-jdk-alpine

# Configura el directorio de trabajo
WORKDIR /app

# Copia el archivo JAR de tu aplicación al contenedor
COPY build/libs/buensaboruno-0.0.1-SNAPSHOT.jar app.jar

# Expone el puerto en el que se ejecuta la aplicación Spring Boot
EXPOSE 8080

# Define el comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]

# Imagen base
FROM openjdk:17-jdk-slim

# Directorio de trabajo dentro del contenedor
WORKDIR /app

# Copiamos el JAR construido desde el host al contenedor
COPY target/filedivider-1.0.0.jar app.jar

# Expone el puerto del backend (por defecto Spring Boot usa 8080)
EXPOSE 8080

# Comando que ejecuta la app
ENTRYPOINT ["java", "-jar", "app.jar"]

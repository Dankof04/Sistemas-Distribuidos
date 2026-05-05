# 1. Usamos la imagen de Java 25.
# Alpine es una versión muy ligera (ocupa poco espacio)
FROM eclipse-temurin:25-jdk-alpine

# 2. Directorio de trabajo
WORKDIR /app

# 3. Copiamos el archivo .jar
# Recuerda: debes haber hecho './mvnw clean package' antes
COPY target/*.jar app.jar

# 4. Puerto de Spring Boot
EXPOSE 8080

# 5. Ejecución
ENTRYPOINT ["java", "-jar", "app.jar"]
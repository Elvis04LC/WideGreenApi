# Usa una imagen oficial de OpenJDK como base
FROM openjdk:17-jdk-slim

# Establece el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copia el jar construido a la imagen
COPY target/WideGreenApi-0.0.1-SNAPSHOT.jar app.jar

# Expone el puerto que usará tu aplicación (ajusta si tu app usa otro puerto)
EXPOSE 8080

# Comando para ejecutar la app
ENTRYPOINT ["java", "-jar", "app.jar"]

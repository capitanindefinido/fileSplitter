# File Splitter Java - Proyecto Entrevista Técnica

## Descripción
Aplicación desarrollada en Java (Spring Boot) que permite dividir archivos en segmentos configurables.  
Está orientada a facilitar el envío de archivos pesados, dividiéndolos en partes más pequeñas para su distribución por correo electrónico o transferencia manual.

## Funcionalidades
- Carga de cualquier tipo de archivo desde una interfaz web.
- División de archivos en segmentos de tamaño configurable (en bytes o MB).
- Descarga individual de cada segmento generado.
- Envío de los segmentos por correo electrónico desde la aplicación.
- Limpieza automática del directorio de archivos segmentados para optimizar el almacenamiento.
- Reconstrucción sencilla del archivo original a partir de sus segmentos.
- Dockerización del backend para facilitar el despliegue en cualquier entorno.
- Tests unitarios con JUnit para validar la lógica de negocio.

## Requisitos Técnicos
- Java 17 o superior
- Maven 3.6 o superior
- Navegador web moderno (para el frontend)
- Docker (opcional, para desplegar el backend en contenedores)

## Instalación y Ejecución Manual

1. Clonar el repositorio:
   ```bash
   git clone git@github.com:capitanindefinido/fileSplitter.git
   cd fileSplitter
   ```

2. Compilar el proyecto:
   ```bash
   mvn clean install
   ```

3. Ejecutar el backend:
   ```bash
   mvn spring-boot:run
   ```

4. Acceder al frontend:
   - Abrir el archivo `index.html` en un navegador web.
   - Alternativamente, se puede levantar un servidor estático:
     ```bash
     python3 -m http.server 5500
     ```

## Uso de la Aplicación

1. Seleccionar un archivo a dividir desde el frontend.
2. Definir el tamaño de los segmentos en bytes.
3. Pulsar el botón "Procesar" para iniciar la división.
4. Descargar los segmentos generados desde los enlaces que aparecen luego del procesamiento.
5. Enviar los segmentos por correo electrónico mediante el formulario correspondiente (próximamente en frontend, disponible vía API).

## Endpoints Disponibles (API REST)

- `POST /api/upload`  
  Sube el archivo y lo divide en segmentos.

- `GET /api/download/{filename}`  
  Descarga el segmento especificado.

- `POST /api/send-email`  
  Envía por correo electrónico los segmentos generados.
   - Parámetros:
      - `destinatario`: dirección de correo del receptor.
      - `archivoBase`: nombre base del archivo a enviar.

## Limpieza Automática de Archivos Segmentados

- Los archivos en el directorio `uploads/` se eliminan automáticamente cada una hora.
- Esta funcionalidad se implementa en la clase `FileService.java` mediante la anotación `@Scheduled` de Spring Boot.

## Validación de los Segmentos

Para recomponer el archivo original desde sus segmentos, se pueden usar los siguientes comandos según el sistema operativo:

### En Windows
```cmd
copy /b archivo.0.pdf + archivo.1.pdf + archivo.2.pdf archivo_reconstruido.pdf
```

### En Linux/Mac
```bash
cat archivo.0.pdf archivo.1.pdf archivo.2.pdf > archivo_reconstruido.pdf
```

## Estructura del Proyecto
```
src
├── main
│   ├── java
│   │   └── com
│   │       └── example
│   │           └── filedivider
│   │               ├── FileDividerApplication.java
│   │               ├── controller
│   │               │   └── FileController.java
│   │               ├── service
│   │               │   ├── FileService.java
│   │               │   └── EmailService.java
│   └── resources
│       └── application.properties
└── test
    └── java
        └── com
            └── example
                └── filedivider
                    └── service
                        └── FileServiceTest.java
```

## Pruebas Unitarias

- Se realizaron pruebas unitarias sobre la lógica de división de archivos (`FileService`).
- Las pruebas incluyen:
   - Validación de la cantidad de segmentos generados.
   - Manejo de archivos vacíos.
   - Verificación de los nombres generados.

Ejecutar los tests con:
```bash
mvn test
```

## Despliegue con Docker

1. Generar el JAR del proyecto:
   ```bash
   mvn clean package
   ```

2. Construir la imagen de Docker:
   ```bash
   docker build -t file-splitter:latest .
   ```

3. Ejecutar el contenedor Docker:
   ```bash
   docker run -d -p 8080:8080 --name file-splitter-container file-splitter:latest
   ```

4. Acceder al backend en:
   ```
   http://localhost:8080/api
   ```

Notas:
- El contenedor expone el puerto 8080 por defecto.
- Se puede cambiar el puerto con el parámetro `-p <host_port>:8080`.

## Configuración de Correo Electrónico (SMTP)

1. La aplicación utiliza Spring Boot Mail.
2. Configurar los siguientes valores en `src/main/resources/application.properties`:

```
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=tuemail@gmail.com
spring.mail.password=tu_app_password
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
```

3. Se recomienda el uso de contraseñas de aplicación en cuentas Gmail.
4. El envío de correos adjunta los segmentos almacenados en el directorio `uploads/`.

## Notas Finales

- Esta es una prueba técnica desarrollada con enfoque en simplicidad, claridad y cumplimiento del enunciado.
- La aplicación es extensible, contemplando futuras mejoras como integración con servicios de almacenamiento (S3), colas de mensajes (RabbitMQ), y una interfaz frontend más completa.
- El backend es portable mediante Docker para su fácil despliegue en entornos locales o en la nube.

## Desarrollador

Diego Ramirez (Capitan Indefinido)
```


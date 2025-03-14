
# Divisor de Archivos

## Requisitos
- Java 17 o superior
- Maven 3.6+

## Instalación y ejecución

1. Clona el repositorio o descarga el proyecto.
2. Compila el proyecto con Maven:
   ```
   mvn clean install
   ```
3. Ejecuta el servidor:
   ```
   mvn spring-boot:run
   ```
4. Frontend: abre el archivo `index.html` en tu navegador para interactuar.

## Validación
1. Sube un archivo desde la interfaz.
2. Define el tamaño en bytes de los segmentos.
3. Descarga los segmentos generados.
4. En el terminal:
   - **Windows:** `copy /b archivo.0.pdf + archivo.1.pdf archivo.pdf`
   - **Linux/Mac:** `cat archivo.0.pdf archivo.1.pdf > archivo.pdf`

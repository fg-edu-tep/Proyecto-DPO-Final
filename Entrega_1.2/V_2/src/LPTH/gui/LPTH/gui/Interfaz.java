import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class Interfaz {

    public static void main(String[] args) {
        iniciarServidor();
    }

    public static void iniciarServidor() {
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);

            // Panel de inicio de sesión
            server.createContext("/", exchange -> {
                String html = generarInicioSesion();
                enviarRespuesta(exchange, html);
            });

            // Pantalla de registro de usuario
            server.createContext("/register", exchange -> {
                String html = generarRegistroUsuario();
                enviarRespuesta(exchange, html);
            });

            // Pantalla de creación de estudiante
            server.createContext("/register/student", exchange -> {
                String html = generarCrearEstudiante();
                enviarRespuesta(exchange, html);
            });

            // Pantalla de creación de profesor
            server.createContext("/register/teacher", exchange -> {
                String html = generarCrearProfesor();
                enviarRespuesta(exchange, html);
            });

            // Menú del profesor
            server.createContext("/menu/teacher", exchange -> {
                String html = generarMenuProfesor();
                enviarRespuesta(exchange, html);
            });

            // Pantalla para crear "Learning Path"
            server.createContext("/menu/teacher/learning-path", exchange -> {
                String html = generarCrearLearningPath();
                enviarRespuesta(exchange, html);
            });

            // Procesar datos de formularios
            server.createContext("/process", exchange -> {
                if ("POST".equals(exchange.getRequestMethod())) {
                    // Aquí procesaremos los datos del formulario
                    String response = "¡Datos recibidos y procesados!";
                    enviarRespuesta(exchange, response);
                }
            });

            server.start();
            System.out.println("Servidor iniciado en http://localhost:8000");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void enviarRespuesta(HttpExchange exchange, String response) throws IOException {
        exchange.sendResponseHeaders(200, response.getBytes().length);
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    // Generar HTML para cada pantalla
    private static String generarInicioSesion() {
        return """
            <!DOCTYPE html>
            <html lang="en">
            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Iniciar Sesión</title>
            </head>
            <body>
                <h1>Iniciar Sesión</h1>
                <form action="/process" method="post">
                    <label for="email">Email:</label><br>
                    <input type="email" id="email" name="email" required><br>
                    <label for="password">Contraseña:</label><br>
                    <input type="password" id="password" name="password" required><br>
                    <button type="submit">Iniciar sesión</button>
                </form>
                <a href="/register">Registrarse</a>
            </body>
            </html>
        """;
    }

    private static String generarRegistroUsuario() {
        return """
            <!DOCTYPE html>
            <html lang="en">
            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Registro</title>
            </head>
            <body>
                <h1>Crear Usuario</h1>
                <a href="/register/student"><button>Cuenta Estudiante</button></a>
                <a href="/register/teacher"><button>Cuenta Profesor</button></a>
                <a href="/">Volver</a>
            </body>
            </html>
        """;
    }

    private static String generarCrearEstudiante() {
        return """
            <!DOCTYPE html>
            <html lang="en">
            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Crear Estudiante</title>
            </head>
            <body>
                <h1>Crear Estudiante</h1>
                <form action="/process" method="post">
                    <label for="email">Email:</label><br>
                    <input type="email" id="email" name="email" required><br>
                    <label for="password">Contraseña:</label><br>
                    <input type="password" id="password" name="password" required><br>
                    <label for="name">Nombre:</label><br>
                    <input type="text" id="name" name="name" required><br>
                    <button type="submit">Crear Cuenta</button>
                </form>
                <a href="/register">Volver</a>
            </body>
            </html>
        """;
    }

    private static String generarCrearProfesor() {
        return """
            <!DOCTYPE html>
            <html lang="en">
            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Crear Profesor</title>
            </head>
            <body>
                <h1>Crear Profesor</h1>
                <form action="/process" method="post">
                    <label for="email">Email:</label><br>
                    <input type="email" id="email" name="email" required><br>
                    <label for="password">Contraseña:</label><br>
                    <input type="password" id="password" name="password" required><br>
                    <label for="name">Nombre:</label><br>
                    <input type="text" id="name" name="name" required><br>
                    <label for="subject">Materia:</label><br>
                    <input type="text" id="subject" name="subject" required><br>
                    <button type="submit">Crear Cuenta</button>
                </form>
                <a href="/register">Volver</a>
            </body>
            </html>
        """;
    }

    private static String generarMenuProfesor() {
        return """
            <!DOCTYPE html>
            <html lang="en">
            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Menú Profesor</title>
            </head>
            <body>
                <h1>Menú Profesor</h1>
                <a href="/menu/teacher/learning-path"><button>Crear un Learning Path</button></a>
                <a href="/"><button>Salir</button></a>
            </body>
            </html>
        """;
    }

    private static String generarCrearLearningPath() {
        return """
            <!DOCTYPE html>
            <html lang="en">
            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Crear Learning Path</title>
            </head>
            <body>
                <h1>Crear Learning Path</h1>
                <form action="/process" method="post">
                    <label for="title">Título:</label><br>
                    <input type="text" id="title" name="title" required><br>
                    <label for="difficulty">Nivel de Dificultad:</label><br>
                    <input type="text" id="difficulty" name="difficulty" required><br>
                    <label for="duration">Duración (horas):</label><br>
                    <input type="text" id="duration" name="duration" required><br>
                    <label for="description">Descripción:</label><br>
                    <textarea id="description" name="description" required></textarea><br>
                    <button type="submit">Guardar</button>
                </form>
                <a href="/menu/teacher"><button>Volver</button></a>
            </body>
            </html>
        """;
    }
}

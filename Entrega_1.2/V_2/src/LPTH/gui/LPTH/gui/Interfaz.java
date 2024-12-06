package LPTH.gui;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import LPTH.exceptions.ExceptionNoPersistencia;
import LPTH.modelo.UserFactory;
import LPTH.usuarios.Usuario;

public class Interfaz {

    private static UserFactory userFactory; // Instancia de UserFactory para gestionar usuarios

    public static void main(String[] args) {
        try {
            // Inicializar UserFactory
            try {
                userFactory = new UserFactory();
            } catch (ExceptionNoPersistencia e) {
                e.printStackTrace();
            }

            // Iniciar servidor
            HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);

            // Rutas del servidor
            server.createContext("/", exchange -> {
                String html = generarInicioSesion();
                enviarRespuesta(exchange, html);
            });

            server.createContext("/register", exchange -> {
                String html = generarRegistroUsuario();
                enviarRespuesta(exchange, html);
            });

            server.createContext("/register/student", exchange -> {
                String html = generarCrearEstudiante();
                enviarRespuesta(exchange, html);
            });

            server.createContext("/register/teacher", exchange -> {
                String html = generarCrearProfesor();
                enviarRespuesta(exchange, html);
            });

            // Procesar formulario de creación de estudiante
            server.createContext("/process/student", exchange -> {
                if ("POST".equals(exchange.getRequestMethod())) {
                    String requestBody = new String(exchange.getRequestBody().readAllBytes());
                    Map<String, String> inputs = parseFormInputs(requestBody);

                    String email = inputs.get("email");
                    String password = inputs.get("password");
                    String name = inputs.get("name");
                    String fechaRegistro = Date.from(Instant.now()).toString();

                    // Crear estudiante con UserFactory
                    Usuario estudiante = userFactory.crearUsuario("estudiante", name, email, password, fechaRegistro, null);

                    // Respuesta
                    String response = "Estudiante creado exitosamente: " + estudiante.toString();
                    enviarRespuesta(exchange, response);
                }
            });

            // Procesar formulario de creación de profesor
            server.createContext("/process/teacher", exchange -> {
                if ("POST".equals(exchange.getRequestMethod())) {
                    String requestBody = new String(exchange.getRequestBody().readAllBytes());
                    Map<String, String> inputs = parseFormInputs(requestBody);

                    String email = inputs.get("email");
                    String password = inputs.get("password");
                    String name = inputs.get("name");
                    String subject = inputs.get("subject");
                    String fechaRegistro = Date.from(Instant.now()).toString();

                    // Crear profesor con UserFactory
                    Usuario profesor = userFactory.crearUsuario("profesor", name, email, password, fechaRegistro, subject);

                    // Respuesta
                    String response = "Profesor creado exitosamente: " + profesor.toString();
                    enviarRespuesta(exchange, response);
                }
            });

            server.start();
            System.out.println("Servidor iniciado en http://localhost:8000");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método para enviar respuesta al cliente
    private static void enviarRespuesta(HttpExchange exchange, String response) throws IOException {
        exchange.sendResponseHeaders(200, response.getBytes().length);
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    // Método auxiliar para procesar inputs de formularios
    private static Map<String, String> parseFormInputs(String requestBody) {
        Map<String, String> inputs = new HashMap<>();
        String[] pairs = requestBody.split("&");
        for (String pair : pairs) {
            String[] keyValue = pair.split("=");
            if (keyValue.length == 2) {
                String key = URLDecoder.decode(keyValue[0], StandardCharsets.UTF_8);
                String value = URLDecoder.decode(keyValue[1], StandardCharsets.UTF_8);
                inputs.put(key, value);
            }
        }
        return inputs;
    }

    // HTML: Pantalla de inicio de sesión
    private static String generarInicioSesion() {
        return """
            <!DOCTYPE html>
            <html lang="en">
            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Iniciar Sesión</title>
                <style>
                    body {
                        font-family: Arial, sans-serif;
                        display: flex;
                        justify-content: center;
                        align-items: center;
                        height: 100vh;
                        margin: 0;
                        background-color: #f5f5f5;
                    }

                    .container {
                        background: #fff;
                        padding: 20px;
                        border-radius: 8px;
                        box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
                        width: 300px;
                        text-align: center;
                    }

                    h1 {
                        font-size: 24px;
                        margin-bottom: 20px;
                    }

                    button {
                        width: 100%;
                        padding: 10px;
                        background-color: #000;
                        color: #fff;
                        border: none;
                        border-radius: 4px;
                        font-size: 16px;
                        cursor: pointer;
                    }

                    button:hover {
                        background-color: #333;
                    }
                </style>
            </head>
            <body>
                <div class="container">
                    <h1>Bienvenido</h1>
                    <a href="/register">
                        <button>Registrarse</button>
                    </a>
                </div>
            </body>
            </html>
        """;
    }

    // HTML: Pantalla de registro de usuario
    private static String generarRegistroUsuario() {
        return """
            <!DOCTYPE html>
            <html lang="en">
            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Registro</title>
                <style>
                    body {
                        font-family: Arial, sans-serif;
                        display: flex;
                        justify-content: center;
                        align-items: center;
                        height: 100vh;
                        margin: 0;
                        background-color: #f5f5f5;
                    }

                    .container {
                        background: #fff;
                        padding: 20px;
                        border-radius: 8px;
                        box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
                        width: 300px;
                        text-align: center;
                    }

                    h1 {
                        font-size: 24px;
                        margin-bottom: 20px;
                    }

                    button {
                        width: 100%;
                        padding: 10px;
                        background-color: #000;
                        color: #fff;
                        border: none;
                        border-radius: 4px;
                        font-size: 16px;
                        cursor: pointer;
                        margin-bottom: 10px;
                    }

                    button:hover {
                        background-color: #333;
                    }
                </style>
            </head>
            <body>
                <div class="container">
                    <h1>Seleccionar Tipo de Usuario</h1>
                    <a href="/register/student">
                        <button>Registrar Estudiante</button>
                    </a>
                    <a href="/register/teacher">
                        <button>Registrar Profesor</button>
                    </a>
                </div>
            </body>
            </html>
        """;
    }

    // HTML: Formulario para crear estudiante
    private static String generarCrearEstudiante() {
        return """
            <!DOCTYPE html>
            <html lang="en">
            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Crear Estudiante</title>
                <style>
                    body {
                        font-family: Arial, sans-serif;
                        display: flex;
                        justify-content: center;
                        align-items: center;
                        height: 100vh;
                        margin: 0;
                        background-color: #f5f5f5;
                    }

                    .container {
                        background: #fff;
                        padding: 20px;
                        border-radius: 8px;
                        box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
                        width: 300px;
                        text-align: center;
                    }

                    h1 {
                        font-size: 24px;
                        margin-bottom: 20px;
                    }

                    input {
                        width: calc(100% - 20px);
                        padding: 10px;
                        margin-bottom: 15px;
                        border: 1px solid #ccc;
                        border-radius: 4px;
                    }

                    button {
                        width: 100%;
                        padding: 10px;
                        background-color: #000;
                        color: #fff;
                        border: none;
                        border-radius: 4px;
                        font-size: 16px;
                        cursor: pointer;
                    }

                    button:hover {
                        background-color: #333;
                    }
                </style>
            </head>
            <body>
                <div class="container">
                    <h1>Crear Estudiante</h1>
                    <form action="/process/student" method="post">
                        <input type="email" name="email" placeholder="Email" required>
                        <input type="password" name="password" placeholder="Contraseña" required>
                        <input type="text" name="name" placeholder="Nombre" required>
                        <button type="submit">Registrar Estudiante</button>
                    </form>
                </div>
            </body>
            </html>
        """;
    }

    // HTML: Formulario para crear profesor
    private static String generarCrearProfesor() {
        return """
            <!DOCTYPE html>
            <html lang="en">
            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Crear Profesor</title>
                <style>
                    body {
                        font-family: Arial, sans-serif;
                        display: flex;
                        justify-content: center;
                        align-items: center;
                        height: 100vh;
                        margin: 0;
                        background-color: #f5f5f5;
                    }

                    .container {
                        background: #fff;
                        padding: 20px;
                        border-radius: 8px;
                        box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
                        width: 300px;
                        text-align: center;
                    }

                    h1 {
                        font-size: 24px;
                        margin-bottom: 20px;
                    }

                    input {
                        width: calc(100% - 20px);
                        padding: 10px;
                        margin-bottom: 15px;
                        border: 1px solid #ccc;
                        border-radius: 4px;
                    }

                    button {
                        width: 100%;
                        padding: 10px;
                        background-color: #000;
                        color: #fff;
                        border: none;
                        border-radius: 4px;
                        font-size: 16px;
                        cursor: pointer;
                    }

                    button:hover {
                        background-color: #333;
                    }
                </style>
            </head>
            <body>
                <div class="container">
                    <h1>Crear Profesor</h1>
                    <form action="/process/teacher" method="post">
                        <input type="email" name="email" placeholder="Email" required>
                        <input type="password" name="password" placeholder="Contraseña" required>
                        <input type="text" name="name" placeholder="Nombre" required>
                        <input type="text" name="subject" placeholder="Materia" required>
                        <button type="submit">Registrar Profesor</button>
                    </form>
                </div>
            </body>
            </html>
        """;
    }
}

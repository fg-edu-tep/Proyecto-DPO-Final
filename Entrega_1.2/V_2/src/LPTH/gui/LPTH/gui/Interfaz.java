package LPTH.gui;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
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
            userFactory = new UserFactory().loadUsuarios(); // Cargar usuarios existentes

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

                    try {
                        // Crear estudiante con UserFactory
                        Usuario estudiante = userFactory.crearUsuario("estudiante", name, email, password, "");

                        // Convertir estudiante a JSON en el formato esperado
                        String estudianteJson = String.format("""
                            {
                                "idUsuario": %d,
                                "nombre": "%s",
                                "email": "%s",
                                "contrasenia": "%s",
                                "fechaRegistro": "%s"
                            }
                            """, estudiante.getIdUsuario(), estudiante.getNombre(), estudiante.getEmail(), estudiante.getContrasenia(), estudiante.getFechaRegistro());

                        // Persistir usuarios
                        userFactory.saveUsuarios();

                        // Respuesta
                        String response = "Estudiante creado exitosamente: " + estudianteJson;
                        enviarRespuesta(exchange, response);
                    } catch (Exception e) {
                        enviarRespuesta(exchange, "Error al crear estudiante: " + e.getMessage());
                    }
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

                    try {
                        // Crear profesor con UserFactory
                        Usuario profesor = userFactory.crearUsuario("profesor", name, email, password, subject);

                        // Convertir profesor a JSON en el formato esperado
                        String profesorJson = String.format("""
                            {
                                "idUsuario": %d,
                                "nombre": "%s",
                                "email": "%s",
                                "contrasenia": "%s",
                                "fechaRegistro": "%s",
                                "materia": "%s"
                            }
                            """, profesor.getIdUsuario(), profesor.getNombre(), profesor.getEmail(), profesor.getContrasenia(), profesor.getFechaRegistro(), subject);

                        // Persistir usuarios
                        userFactory.saveUsuarios();

                        // Respuesta
                        String response = "Profesor creado exitosamente: " + profesorJson;
                        enviarRespuesta(exchange, response);
                    } catch (Exception e) {
                        enviarRespuesta(exchange, "Error al crear profesor: " + e.getMessage());
                    }
                }
            });

            // Procesar inicio de sesión
            server.createContext("/process/login", exchange -> {
                if ("POST".equals(exchange.getRequestMethod())) {
                    String requestBody = new String(exchange.getRequestBody().readAllBytes());
                    Map<String, String> inputs = parseFormInputs(requestBody);

                    String email = inputs.get("email");
                    String password = inputs.get("password");

                    try {
                        // Autenticar usuario con UserFactory
                        Usuario usuario = userFactory.autenticarUsuario(email, password);
                        if (usuario != null) {
                            String response = "Bienvenido, " + usuario.toString();
                            enviarRespuesta(exchange, response);
                        } else {
                            enviarRespuesta(exchange, "Credenciales incorrectas.");
                        }
                    } catch (Exception e) {
                        enviarRespuesta(exchange, "Usuario no encontrado.");
                    }
                }
            });

            server.start();
            System.out.println("Servidor iniciado en http://localhost:8000");
        } catch (IOException | ExceptionNoPersistencia e) {
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

                    label {
                        display: block;
                        text-align: left;
                        margin-bottom: 5px;
                    }

                    input {
                        width: 100%;
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

                    .register-link {
                        margin-top: 10px;
                        font-size: 14px;
                    }

                    .register-link a {
                        color: #000;
                        text-decoration: none;
                        font-weight: bold;
                    }

                    .register-link a:hover {
                        text-decoration: underline;
                    }
                </style>
            </head>
            <body>
                <div class="container">
                    <h1>Iniciar sesión</h1>
                    <form action="/process" method="post">
                        <label for="email">Email*</label>
                        <input type="email" id="email" name="email" required>
                        <label for="password">Contraseña*</label>
                        <input type="password" id="password" name="password" required>
                        <button type="submit">Iniciar sesión</button>
                    </form>
                    <div class="register-link">
                        ¿Es tu primera vez? <a href="/register">Registrarse</a>
                    </div>
                </div>
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
                        margin-bottom: 15px;
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

                    a {
                        text-decoration: none;
                        color: #fff;
                    }
                </style>
            </head>
            <body>
                <div class="container">
                    <h1>Crear Usuario</h1>
                    <a href="/register/teacher">
                        <button>Cuenta Profesor</button>
                    </a>
                    <a href="/register/student">
                        <button>Cuenta Estudiante</button>
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
                    <form action="/process" method="post">
                        <input type="email" id="email" name="email" placeholder="Ingrese su email" required>
                        <input type="password" id="password" name="password" placeholder="Ingrese su Contraseña" required>
                        <input type="text" id="name" name="name" placeholder="Ingrese su Nombre" required>
                        <button type="submit">Crear Cuenta</button>
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
                    <form action="/process" method="post">
                        <input type="email" id="email" name="email" placeholder="Ingrese su email" required>
                        <input type="password" id="password" name="password" placeholder="Ingrese su Contraseña" required>
                        <input type="text" id="name" name="name" placeholder="Ingrese su Nombre" required>
                        <input type="text" id="subject" name="subject" placeholder="Ingrese su Materia" required>
                        <button type="submit">Crear Cuenta</button>
                    </form>
                </div>
            </body>
            </html>
        """;
    }
}

package LPTH.gui;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.IIOException;

import LPTH.exceptions.ExceptionNoPersistencia;
import LPTH.modelo.Sistema;
import LPTH.modelo.UserFactory;
import LPTH.persistencia.PersistirSistema;
import LPTH.usuarios.Estudiante;
import LPTH.usuarios.Profesor;
import LPTH.usuarios.Usuario;

public class Interfaz {
	
    // Crear el sistema:
    private static Sistema sistemaCentral = new Sistema();
    
    private static UserFactory userFactory; // Instancia de UserFactory para gestionar usuarios

    public static void addContexts(HttpServer server) {
        try {
            // Inicializar UserFactory
            userFactory = new UserFactory().loadUsuarios(); // Cargar usuarios existentes

            // Rutas del servidor (contextos)
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

                        // Guardar los estudiantes actualizados
                        userFactory.saveUsuarios();

                        // Redirigir al inicio de sesión
                        redirigir(exchange, "/");
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

                        // Guardar los profesores actualizados
                        userFactory.saveUsuarios();

                        // Redirigir al inicio de sesión
                        redirigir(exchange, "/");
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
                    	sistemaCentral = sistemaCentral.loadSistema();
                    	//System.out.println(sistemaCentral.getLearningPaths().getFirst().getTitulo());
                    	
					} catch (ExceptionNoPersistencia e) {
						System.out.println("Error Fatal al cargar el sistema");
						e.printStackTrace();
					}


                    try {
                        // Autenticar usuario con UserFactory
                        Usuario usuario = userFactory.autenticarUsuario(email, password);
                        if (usuario != null) {
                            if (usuario.getTipo().equalsIgnoreCase("estudiante")) {
                                // Estudiante estudiante = (Estudiante)usuario; No importa
                                MenuEstudiante.setSistema(sistemaCentral);
                            	redirigir(exchange, "/menu/student");
                            	
                            } else if (usuario.getTipo().equalsIgnoreCase("profesor")) {
                                Profesor profesor = (Profesor) usuario;
                            	MenuProfesor.setProfesorActual(profesor);
                            	MenuProfesor.setSistema(sistemaCentral);
                                redirigir(exchange, "/menu/teacher");
                            }
                        } else {
                            enviarRespuesta(exchange, "Credenciales incorrectas. <a href=\"/\">Intentar nuevamente</a>");
                        }
                    } catch (Exception e) {
                        enviarRespuesta(exchange, "Usuario no encontrado. <a href=\"/\">Intentar nuevamente</a>");
                    }
                }
            });

        } catch (ExceptionNoPersistencia | IOException e) {
            e.printStackTrace();
        }
    }

    // Métodos auxiliares (enviarRespuesta, redirigir, parseFormInputs, generarInicioSesion, etc.)
    // (Todos estos métodos permanecen igual que en la versión anterior)

    // Método para enviar respuesta al cliente
    private static void enviarRespuesta(HttpExchange exchange, String response) throws IOException {
        exchange.sendResponseHeaders(200, response.getBytes().length);
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    // Método para redirigir al cliente a otra ruta
    private static void redirigir(HttpExchange exchange, String ruta) throws IOException {
        exchange.getResponseHeaders().add("Location", ruta);
        exchange.sendResponseHeaders(302, -1); // Código 302 para redirección
        exchange.close();
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
        // (Mantiene el mismo código de HTML que en la versión anterior)
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

                    a {
                        display: block;
                        margin-top: 15px;
                        color: #333;
                        text-decoration: none;
                    }

                    a:hover {
                        text-decoration: underline;
                    }
                </style>
            </head>
            <body>
                <div class="container">
                    <h1>Iniciar Sesión</h1>
                    <form action="/process/login" method="post">
                        <input type="email" name="email" placeholder="Email" required>
                        <input type="password" name="password" placeholder="Contraseña" required>
                        <button type="submit">Iniciar Sesión</button>
                    </form>
                    <a href="/register">No tengo cuenta, registrarme</a>
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

                    a {
                        display: block;
                        margin-top: 15px;
                        color: #333;
                        text-decoration: none;
                    }

                    a:hover {
                        text-decoration: underline;
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
                    <a href="/">Regresar al Inicio de Sesión</a>
                </div>
            </body>
            </html>
        """;
    }

    // HTML: Pantalla de menú de estudiante
    private static String generarMenuEstudiante() {
        return """
            <!DOCTYPE html>
            <html lang="en">
            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Menú Estudiante</title>
            </head>
            <body>
                <h1>Bienvenido Estudiante</h1>
                <p>Este es el menú de estudiantes.</p>
            </body>
            </html>
        """;
    }

    // HTML: Pantalla de menú de profesor
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
                <h1>Bienvenido Profesor</h1>
                <p>Este es el menú de profesores.</p>
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

package LPTH.gui;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpExchange;

import LPTH.modelo.LearningPath;
import LPTH.modelo.Sistema;
import LPTH.persistencia.PersistirSistema;
import LPTH.usuarios.Profesor;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class MenuProfesor {

    private static PersistirSistema persistirSistema = new PersistirSistema();
    private static Sistema sistema; // Sistema para almacenar los Learning Paths
    private static Profesor profesorActual; // Profesor actualmente autenticado

    static {
        try {
            // Intentar cargar el sistema al inicio
            sistema = persistirSistema.cargarSistema();
            if (sistema == null) {
                sistema = new Sistema(); // Crear uno nuevo si no se encuentra
            }
        } catch (IOException e) {
            System.out.println("No se pudo cargar el sistema: " + e.getMessage());
            sistema = new Sistema(); // Crear uno nuevo en caso de error
        }
    }

    /**
     * Establece el profesor autenticado que inició sesión.
     */
    public static void setProfesorActual(Profesor profesor) {
        profesorActual = profesor;
    }

    public static void addContexts(HttpServer server) {
        // Contexto para el menú del profesor
        server.createContext("/menu/teacher", exchange -> {
            String html = generarMenuProfesor();
            enviarRespuesta(exchange, html);
        });

        // Contexto para la pantalla de creación de Learning Path
        server.createContext("/menu/teacher/crear_learning_path", exchange -> {
            String html = generarCrearLearningPath();
            enviarRespuesta(exchange, html);
        });

        // Procesar formulario de creación de Learning Path
        server.createContext("/menu/teacher/process/crear_learning_path", exchange -> {
            if ("POST".equals(exchange.getRequestMethod())) {
                String requestBody = new String(exchange.getRequestBody().readAllBytes());
                Map<String, String> inputs = parseFormInputs(requestBody);

                String titulo = inputs.get("titulo");
                String descripcion = inputs.get("descripcion");
                String nivelDeDificultad = inputs.get("nivel");
                int duracion = Integer.parseInt(inputs.get("duracion"));

                try {
                    if (profesorActual == null) {
                        throw new IllegalStateException("No hay un profesor autenticado.");
                    }

                    // Crear el Learning Path utilizando el método del profesor autenticado
                    LearningPath learningPath = profesorActual.crearlearningPath(
                            titulo, descripcion, nivelDeDificultad, duracion);

                    // Persistir el sistema actualizado
                    sistema.saveSistema();
                    System.out.println("Learning Path guardado exitosamente: " + learningPath.getTitulo());
                } catch (Exception e) {
                    System.out.println("Error al guardar el Learning Path: " + e.getMessage());
                }

                // Redirigir al menú del profesor
                redirigir(exchange, "/menu/teacher");
            }
        });
    }

    // Métodos auxiliares (enviarRespuesta, redirigir, parseFormInputs, generarMenuProfesor, generarCrearLearningPath)

    private static void enviarRespuesta(HttpExchange exchange, String response) throws IOException {
        exchange.sendResponseHeaders(200, response.getBytes().length);
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    private static void redirigir(HttpExchange exchange, String ruta) throws IOException {
        exchange.getResponseHeaders().add("Location", ruta);
        exchange.sendResponseHeaders(302, -1);
        exchange.close();
    }

    private static Map<String, String> parseFormInputs(String requestBody) {
        Map<String, String> inputs = new HashMap<>();
        String[] pairs = requestBody.split("&");
        for (String pair : pairs) {
            String[] keyValue = pair.split("=");
            if (keyValue.length == 2) {
                String key = keyValue[0];
                String value = keyValue[1];
                inputs.put(key, value);
            }
        }
        return inputs;
    }

    public static String generarMenuProfesor() {
        return """
            <!DOCTYPE html>
            <html lang="en">
            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Menú Profesor</title>
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
                    <h1>Menú Profesor</h1>
                    <p>¿Qué desea hacer?</p>
                    <a href="/menu/teacher/crear_learning_path">
                        <button>Crear un Learning Path</button>
                    </a>
                    <a href="/">Salir</a>
                </div>
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
                        width: 400px;
                        text-align: center;
                    }

                    h1 {
                        font-size: 24px;
                        margin-bottom: 20px;
                    }

                    input, textarea {
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
                    <h1>Crear Learning Path</h1>
                    <form action="/menu/teacher/process/crear_learning_path" method="post">
                        <input type="text" name="titulo" placeholder="Ingrese el título" required>
                        <input type="text" name="nivel" placeholder="Ingrese el nivel de dificultad" required>
                        <input type="text" name="duracion" placeholder="Ingrese la duración en horas" required>
                        <textarea name="descripcion" rows="5" placeholder="Ingrese la descripción" required></textarea>
                        <button type="submit">Crear Learning Path</button>
                    </form>
                    <a href="/menu/teacher">Regresar al Menú Profesor</a>
                    <a href="/">Salir</a>
                </div>
            </body>
            </html>
        """;
    }
}
